package dash.asyntask;

import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.TrafficStats;
import android.os.AsyncTask;
import android.util.Log;
import dash.Bean.BandwidthAdaptionInfoBean;
import dash.activitiy.VideoPlayerActivity;
import dash.component.XMLParser;
import dash.network.ExchangeData;
import dash.network.ExchangeDataState;
import dash.network.NetworkConnection;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

/**
 * Created by xuzhiwei on 7/11/15.
 */
public class ExtractMPD extends AsyncTask<Void, String, Void> {
    private String mpdURL;
    private BlockingQueue<String> blockingQueue;
    private BlockingQueue<String> firstUrl;
    private Map<String,BandwidthAdaptionInfoBean> map;
    private String url;
    private double CurrentTraffic = 205.0;
    public ExtractMPD(String mpdURL, BlockingQueue<String> blockingQueue, BlockingQueue<String> firstUrl) {
        super();
        this.mpdURL = mpdURL;
        this.blockingQueue = blockingQueue;
        this.firstUrl = firstUrl;
    }

    @Override
    protected void onProgressUpdate(String... values) {
//        try {
//            for (String value : values) {
//                Log.d("test",value);
//                blockingQueue.put(value);
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        for (BandwidthAdaptionInfoBean temp : map.values()) {
//            Log.d("xml", temp.toString());
//        }
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            map = new XMLParser(mpdURL).parser();
            //check network rate r
            //if r>map.get('high').getBandwidth()
            //url=map.get('high').url
            int index = 1;
            while(true){
                if(CurrentTraffic>=768){
                    url="http://"+map.get("high").getMp4URL() + String.valueOf(index);
                }else if(CurrentTraffic>=200){
                    url="http://"+map.get("medium").getMp4URL() + String.valueOf(index);
                }else {
                    url="http://"+map.get("low").getMp4URL() + String.valueOf(index);
                }

                ExchangeData exchangeData= NetworkConnection.getMP4(url);
                if (exchangeData.getExchangeDataState() == ExchangeDataState.PLAYABLE.mappingInt()) {
                    index++;
//                    publishProgress(exchangeData.getMp4URL());
                    if(index == 2){
                        firstUrl.put(exchangeData.getMp4URL());
                    }else{
                        blockingQueue.put(exchangeData.getMp4URL());
                    }

                } else {
                    if (exchangeData.getExchangeDataState() == ExchangeDataState.PLAYABLE_WITH_END.mappingInt()) {
//                        publishProgress(exchangeData.getMp4URL(),"end");
                        blockingQueue.put(exchangeData.getMp4URL());
                        blockingQueue.put("end");
                        break;
                    } else {
                        if (exchangeData.getExchangeDataState() == ExchangeDataState.WAITING.mappingInt()) {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else {

                        }

                    }
                }
            }

        } catch (IOException | XmlPullParserException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
