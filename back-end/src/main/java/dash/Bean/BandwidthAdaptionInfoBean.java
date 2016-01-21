package dash.Bean;

/**
 * Created by Quincy on 15/10/15.
 */
public class BandwidthAdaptionInfoBean {
    private String desc;
    private int bandwidth;
    private String mp4URL;

    public BandwidthAdaptionInfoBean(String desc) {
        this.desc = desc;
    }

    public void setBandwidth(String bandwidth) {
        this.bandwidth = Integer.parseInt(bandwidth);
    }

    public void setMp4URL(String mp4URL) {
        this.mp4URL = mp4URL;
    }

    public int getBandwidth() {
        return bandwidth;
    }

    public String getDesc() {
        return desc;
    }

    public String getMp4URL() {
        return mp4URL.substring(0,mp4URL.indexOf("$"));
    }

    @Override
    public String toString() {
        return "description: " + desc + " bandwidth: " + bandwidth + " URL: " + mp4URL;
    }
}
