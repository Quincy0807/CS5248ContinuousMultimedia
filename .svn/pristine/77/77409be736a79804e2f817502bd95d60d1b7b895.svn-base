package dash.view;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import io.kickflip.sdk.view.GLCameraView;

/**
 * Created by Quincy on 15/10/19.
 */
public class CaptureSurfaceView extends GLCameraView {

    public CaptureSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CaptureSurfaceView(Context context) {
        super(context);
    }

    @Override
    public void setCamera(Camera camera) {
//        camera.setDisplayOrientation(90);
        super.setCamera(camera);
    }

    @Override
    public void releaseCamera() {
        getHolder().removeCallback(this);
        super.releaseCamera();
    }
}
