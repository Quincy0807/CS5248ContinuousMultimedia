package dash.listener;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import dash.activitiy.ServerVideoViewerActivity;

/**
 * Created by Quincy on 15/9/29.
 */
public class StorageButtonListener implements View.OnClickListener {
    private Context context;

    public StorageButtonListener(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, ServerVideoViewerActivity.class);
        context.startActivity(intent);
    }
}
