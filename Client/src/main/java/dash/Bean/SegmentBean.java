package dash.Bean;

import com.coremedia.iso.IsoFile;

import java.io.IOException;

/**
 * Created by Quincy on 15/10/10.
 */
public class SegmentBean {
    private long duration;
    private long timeScale;
    private double lengthInSecond;

    public long getDuration() {
        return duration;
    }

    public double getLengthInSecond() {
        return lengthInSecond;
    }

    public long getTimeScale() {
        return timeScale;
    }

    public SegmentBean(String path) throws IOException {
        IsoFile isoFile = new IsoFile(path);
        duration = isoFile.getMovieBox().getMovieHeaderBox().getDuration();
        timeScale = isoFile.getMovieBox().getMovieHeaderBox().getTimescale();
        lengthInSecond = duration * 1.0 / timeScale;
    }
}
