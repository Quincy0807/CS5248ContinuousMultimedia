package dash.listener;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Toast;
import dash.activitiy.ClientCaptureActivity;

/**
 * Created by Quincy on 15/9/29.
 */
public class CaptureButtonListener implements View.OnClickListener {
    Context context;

    public CaptureButtonListener(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Intent intent = new Intent(context, ClientCaptureActivity.class);
            context.startActivity(intent);
        } else {
            Toast.makeText(context, " Doesn't support CAMERA", Toast.LENGTH_LONG).show();
        }
    }
}
