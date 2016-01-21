package dash.Bean;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;

import java.io.File;

public class VideoBean {
    private String videoPath;
    private Bitmap videoThumbnail;

    public VideoBean(File videoFile) {
        this.videoPath = videoFile.getAbsolutePath();
        videoThumbnail = ThumbnailUtils.createVideoThumbnail(videoFile.getAbsolutePath(),
                MediaStore.Images.Thumbnails.MICRO_KIND);
    }

    public void setVideoThumbnail(Bitmap videoThumbnail) {
        this.videoThumbnail = videoThumbnail;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoPath = videoTitle;
    }

    public Bitmap getVideoThumbnail() {
        return videoThumbnail;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public String getVideoTitle() {
        return videoPath.substring(videoPath.lastIndexOf("/")+1);
    }
}
