package dash.listener;

import android.content.Context;
import android.view.View;
import android.widget.*;
import dash.adapter.LocalVideoAdapter;
import dash.asyntask.SegmentProgressBarUpdateAsyncTask;

public class UploadButtonListener implements View.OnClickListener {
    /**
     * after being segmented, output file would be xxx.mp4_num
     * num will is the order of the segment.
     **/
    private String filePath;
    private Context context;
    private ProgressBar progressBar;
    private int pos;
    private LocalVideoAdapter localVideoAdapter;


    public UploadButtonListener(String filePath, Context context, ProgressBar progressBar, int pos, LocalVideoAdapter localVideoAdapter) {
        this.filePath = filePath;
        this.context = context;
        this.progressBar = progressBar;
        this.pos = pos;
        this.localVideoAdapter = localVideoAdapter;
    }

    @Override
    public void onClick(View v) {
//        new SegmentProgressBarUpdateAsyncTask(filePath, context, progressBar, pos, localVideoAdapter).execute();
    }
}

