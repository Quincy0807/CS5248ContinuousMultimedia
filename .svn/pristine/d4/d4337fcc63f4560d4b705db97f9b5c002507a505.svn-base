package dash.listener;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import dash.activitiy.VideoPlayerActivity;
import dash.asyntask.ExtractMPD;
import dash.component.BlockingQueueWrapper;

/**
 * Created by Quincy on 15/10/12.
 */
public class RemoteWatchListener implements View.OnClickListener {
    private String mpdPath;
    private Context context;

    public RemoteWatchListener(String mediaPath, Context context) {
        this.mpdPath = mediaPath;
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        BlockingQueueWrapper.extractMPD = new ExtractMPD(mpdPath, BlockingQueueWrapper.blockingQueue, BlockingQueueWrapper.firstQueue);
        BlockingQueueWrapper.extractMPD.execute();
        //new ExtractMPD(mpdPath, BlockingQueueWrapper.blockingQueue, BlockingQueueWrapper.firstQueue).execute();

        Intent intent = new Intent(context, VideoPlayerActivity.class);
        intent.putExtra("mpdPath", mpdPath);
        context.startActivity(intent);
    }
}

