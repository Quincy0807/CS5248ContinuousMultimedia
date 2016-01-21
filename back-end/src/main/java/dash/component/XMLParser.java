package dash.component;

import android.util.Xml;
import dash.Bean.BandwidthAdaptionInfoBean;
import dash.network.NetworkConnection;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Quincy on 15/10/15.
 */
public class XMLParser {
    private String mpdURL;
    private final int START_XML = 1;
    private final int END_XML = 2;
    private final int START_HIGH_PRESENTATION = 3;
    private final int START_MEDIUM_PRESENTATION = 5;
    private final int START_LOW_PRESENTATION = 7;

    public XMLParser(String mpdPath) {
        this.mpdURL = mpdPath;
    }

    public HashMap<String, BandwidthAdaptionInfoBean> parser() throws IOException, XmlPullParserException {
        HashMap<String, BandwidthAdaptionInfoBean> result = new HashMap<>();
        XmlPullParser xmlPullParser = Xml.newPullParser();
        xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        xmlPullParser.setInput(NetworkConnection.getMPD(mpdURL), "utf-8");
        xmlPullParser.nextTag();
        xmlPullParser.require(XmlPullParser.START_TAG,null,"MPD");
        int currentState = START_XML;
        BandwidthAdaptionInfoBean bandwidthAdaptionInfoBean;
        while (currentState != END_XML) {
            String nodeName = xmlPullParser.getName();
            if (nodeName == null) {
                xmlPullParser.next();
                continue;
            }
            switch (currentState) {
                case START_XML:
                    if (nodeName.equals("Representation")&& xmlPullParser.getEventType()==XmlPullParser.START_TAG) {
                        switch (xmlPullParser.getAttributeValue(null, "id")) {
                            case "high":
                                bandwidthAdaptionInfoBean = new BandwidthAdaptionInfoBean("high");
                                bandwidthAdaptionInfoBean.setBandwidth(xmlPullParser.getAttributeValue(null, "bandwidth"));
                                result.put("high", bandwidthAdaptionInfoBean);
                                currentState = START_HIGH_PRESENTATION;
                                break;
                            case "medium":
                                bandwidthAdaptionInfoBean = new BandwidthAdaptionInfoBean("medium");
                                bandwidthAdaptionInfoBean.setBandwidth(xmlPullParser.getAttributeValue(null, "bandwidth"));
                                result.put("medium", bandwidthAdaptionInfoBean);
                                currentState = START_MEDIUM_PRESENTATION;
                                break;
                            case "low":
                                bandwidthAdaptionInfoBean = new BandwidthAdaptionInfoBean("low");
                                bandwidthAdaptionInfoBean.setBandwidth(xmlPullParser.getAttributeValue(null, "bandwidth"));
                                result.put("low", bandwidthAdaptionInfoBean);
                                currentState = START_LOW_PRESENTATION;
                                break;
                        }
                    } else {
                        xmlPullParser.next();
                    }
                    break;
                case START_HIGH_PRESENTATION:
                    if (nodeName.equals("SegmentTemplate")) {
                        result.get("high").setMp4URL(xmlPullParser.getAttributeValue(null, "media"));
                        currentState = START_XML;
                    } else {
                        xmlPullParser.next();
                    }
                    break;
                case START_MEDIUM_PRESENTATION:
                    if (nodeName.equals("SegmentTemplate")) {
                        result.get("medium").setMp4URL(xmlPullParser.getAttributeValue(null, "media"));
                        currentState = START_XML;
                    } else {
                        xmlPullParser.next();
                    }
                    break;
                case START_LOW_PRESENTATION:
                    if (nodeName.equals("SegmentTemplate")) {
                        result.get("low").setMp4URL(xmlPullParser.getAttributeValue(null, "media"));
                        currentState = END_XML;
                    } else {
                        xmlPullParser.next();
                    }
                    break;
            }


        }
        return result;
    }



}

