package dash.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import dash.Bean.SegmentBean;
import dash.Bean.VideoBean;
import dash.activitiy.R;
import dash.network.ExchangeData;
import dash.network.ExchangeDataState;
import dash.network.NetworkConnection;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Quincy on 15/9/29.
 */
public class LocalVideoAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private Map<String, List<VideoBean>> videos;
    private List<String> keyList;
//    private Handler handler;
    private Lock lock = new ReentrantLock();

    public Lock getLock() {
        return lock;
    }

    @SuppressWarnings("all")
    private void findCurrentVideos() {
        File targetDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File[] videoPaths = targetDir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                if (filename.startsWith("test")) {
                    return false;
                }
                if (filename.substring(filename.lastIndexOf(".") + 1).equals("mp4")) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        for (File videoPath : videoPaths) {
            String sessionId = videoPath.getName().split("-")[0];
            if (videos.containsKey(sessionId)) {
                videos.get(sessionId).add(new VideoBean(videoPath));
            } else {
                List<VideoBean> list = new ArrayList<>();
                list.add(new VideoBean(videoPath));
                videos.put(sessionId, list);
            }

        }
        for (Map.Entry<String, List<VideoBean>> entry : videos.entrySet()) {
            Collections.sort(entry.getValue(), new Comparator<VideoBean>() {
                @Override
                public int compare(VideoBean lhs, VideoBean rhs) {
                    int left = Integer.parseInt(lhs.getVideoPath().substring(lhs.getVideoPath().lastIndexOf("-") + 1, lhs.getVideoPath().lastIndexOf(".")));
                    int right = Integer.parseInt(rhs.getVideoPath().substring(rhs.getVideoPath().lastIndexOf("-") + 1, rhs.getVideoPath().lastIndexOf(".")));
                    return left - right;
                }
            });

        }
        keyList = new ArrayList<>(videos.keySet());
    }

    public LocalVideoAdapter(Context context) {
        this.context = context;
//        list = new ArrayList<>();
        videos = new HashMap<>();
        findCurrentVideos();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        handler = new AdapterHandler(this);
    }


    @Override
    public int getCount() {
        return videos.size();
    }

    @Override
    public Object getItem(int position) {
        return keyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            view = inflater.inflate(R.layout.local_video_list_row, null);
        }
        TextView textView = (TextView) view.findViewById(R.id.VideoRowTextView);
        final String name = (String) getItem(position);
        final Button uploadButton = (Button) view.findViewById(R.id.uploadButton);
        textView.setText((CharSequence) getItem(position));
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadButton.setText("uploading");
                List<VideoBean> lists = videos.get(name);
                int sessionId = Integer.parseInt(name);
                int segmentId;
                int isEnd = 0;
                for (int i = 0; i < lists.size(); i++) {
                    lock.lock();
                    if (i == lists.size() - 1) {
                        isEnd = 1;
                    }
                    String temp = lists.get(i).getVideoPath();
                    temp = temp.substring(temp.lastIndexOf("-") + 1, temp.lastIndexOf("."));
                    segmentId = Integer.parseInt(temp);
                    try {
                        new ContinueUploadAsyntask(isEnd, segmentId, sessionId, lists.get(i).getVideoPath(), context, LocalVideoAdapter.this, videos, i, lists.size()).execute();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return view;
    }

}

class ContinueUploadAsyntask extends AsyncTask<Void, Integer, String> {

    private int isEnd;
    private int sessionId;
    private int segmentId;
    private String segmentPath;
    private SegmentBean segmentBean;
    private Context context;
    private LocalVideoAdapter localVideoAdapter;
    private Map<String, List<VideoBean>> videos;
//    private ProgressBar progressBar;
    private int i;
    private int total;

    ContinueUploadAsyntask(int isEnd, int segmentId, int sessionId, String segmentPath, Context context, LocalVideoAdapter localVideoAdapter, Map<String, List<VideoBean>> videos, int i, int total) throws IOException {
        this.isEnd = isEnd;
        this.sessionId = sessionId;
        this.segmentId = segmentId;
        this.segmentPath = segmentPath;
        this.segmentBean = new SegmentBean(segmentPath);
        this.context = context;
        this.localVideoAdapter = localVideoAdapter;
        this.videos = videos;
//        this.progressBar = progressBar;
        this.i = i;
        this.total = total;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        int val = values[0];
        if (val == -1) {
            Toast.makeText(context, "Network unreachable", Toast.LENGTH_SHORT).show();
        }else{
//            progressBar.setProgress(val);
            if (isEnd == 1) {
                localVideoAdapter.notifyDataSetChanged();
            }
        }

    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            ExchangeData exchangeData = new NetworkConnection().makePost(NetworkConnection.SEGMENT_UPLOAD_URL)
                    .addFileBody(new File(segmentPath)).addExchangeData(
                            new ExchangeData()
                                    .addExchangeDataState(ExchangeDataState.REQUEST_TO_UPLOAD)
                                    .addDuration(segmentBean.getDuration())
                                    .addSegmentId(segmentId)
                                    .addSessionId(sessionId)
                                    .addTimeScale(segmentBean.getTimeScale())
                                    .addLengthInSecond(segmentBean.getLengthInSecond())
                                    .addIsEnd(isEnd)).jsonResponse();
            if (exchangeData.getExchangeDataState() != ExchangeDataState.RESPONSE_UPLOAD_SUCCESS.mappingInt()) {
                publishProgress(-1);
            } else {
                if (isEnd == 1) {
                    videos.remove(sessionId + "");
                }
                new File(segmentPath).delete();
                publishProgress((i + 1) / total * 100);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
//class ContinueUploadThread extends Thread {
//    private int isEnd;
//    private int sessionId;
//    private int segmentId;
//    private String segmentPath;
//    private SegmentBean segmentBean;
//    private Context context;
//    private LocalVideoAdapter localVideoAdapter;
//    private Map<String, List<VideoBean>> videos;
//    private ProgressBar progressBar;
//    private int i;
//    private int total;
////    private Lock lock;
//    @Override
//    public void run() {
//        try {
//            ExchangeData exchangeData = new NetworkConnection().makePost(NetworkConnection.SEGMENT_UPLOAD_URL)
//                    .addFileBody(new File(segmentPath)).addExchangeData(
//                            new ExchangeData()
//                                    .addExchangeDataState(ExchangeDataState.REQUEST_TO_UPLOAD)
//                                    .addDuration(segmentBean.getDuration())
//                                    .addSegmentId(segmentId)
//                                    .addSessionId(sessionId)
//                                    .addTimeScale(segmentBean.getTimeScale())
//                                    .addLengthInSecond(segmentBean.getLengthInSecond())
//                                    .addIsEnd(isEnd)).jsonResponse();
//            if (exchangeData.getExchangeDataState() != ExchangeDataState.RESPONSE_UPLOAD_SUCCESS.mappingInt()) {
//                Toast.makeText(context, "Network unreachable", Toast.LENGTH_SHORT).show();
//            } else {
//                if (isEnd == 1) {
//                    videos.remove(sessionId + "");
//                }
//                new File(segmentPath).delete();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public ContinueUploadThread(int isEnd, int segmentId, int sessionId, String segmentPath, Context context, LocalVideoAdapter localVideoAdapter, Map<String, List<VideoBean>> videos,  ProgressBar progressBar, int i, int total) throws IOException {
//        this.isEnd = isEnd;
//        this.segmentId = segmentId;
//        this.sessionId = sessionId;
//        this.segmentPath = segmentPath;
//        this.context = context;
//        this.localVideoAdapter = localVideoAdapter;
//        this.videos = videos;
////        this.handler = handler;
//        this.progressBar = progressBar;
//        this.i = i;
//        this.total = total;
////        this.lock = lock;
//        this.segmentBean = new SegmentBean(segmentPath);
//    }
//}


//class AdapterHandler extends Handler {
//    private LocalVideoAdapter localVideoAdapter;
//
//    AdapterHandler(LocalVideoAdapter localVideoAdapter) {
//        this.localVideoAdapter = localVideoAdapter;
//    }
//
//    @Override
//    public void handleMessage(Message msg) {
//        switch (msg.what) {
//            case 1:
//                localVideoAdapter.notifyDataSetChanged();
//                break;
//            case 2:
//                ProgressBar progressBar = (ProgressBar) msg.obj;
//                progressBar.setProgress(msg.arg1);
//                localVideoAdapter.getLock().unlock();
//            case 3:
//                localVideoAdapter.getLock().unlock();
//
//        }
//    }
//}
