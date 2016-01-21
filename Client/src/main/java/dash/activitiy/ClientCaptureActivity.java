package dash.activitiy;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import dash.component.CaptureRecorder;
import dash.component.MuxerWrapper;
import dash.view.CaptureSurfaceView;
import io.kickflip.sdk.av.SessionConfig;

import java.io.IOException;

public class ClientCaptureActivity extends Activity {
    private Button captureAction;
    private boolean isStart = false;
    private boolean isRelease = true;
    private CaptureSurfaceView captureSurfaceView;


    private CaptureRecorder recorder;
    private boolean isFirstRecording;


    public void setIsStart(boolean isStart) {
        this.isStart = isStart;
    }

    public boolean isStart() {
        return isStart;
    }

    private void initMediaDevices() {
        if (recorder == null) {
            try {
                isFirstRecording = true;
                isRelease = false;
                captureSurfaceView = (CaptureSurfaceView) findViewById(R.id.cameraSurfacePreview);
                SessionConfig sessionConfig = createSessionConfig();
                recorder = new CaptureRecorder(sessionConfig,captureSurfaceView );
                recorder.setPreviewDisplay((CaptureSurfaceView) findViewById(R.id.cameraSurfacePreview));
            } catch (IOException e) {
                // Could not create recording at given file path
                Toast.makeText(this, "Sorry, Recorder can be launched", Toast.LENGTH_LONG).show();
            }
        }

        captureAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recorder.isRecording()) {
                    captureAction.setBackgroundResource(R.drawable.red_dot);
                    recorder.stopRecording();
                } else {
                    if (!isFirstRecording) {
                        resetAVRecorder();
                    } else {
                        isFirstRecording = false;
                    }
                    recorder.startRecording();
                    captureAction.setBackgroundResource(R.drawable.red_dot_stop);
                }
            }
        });
    }

    private void releaseMediaDevices() {
        if (isRelease) {
            return;
        }
        if (recorder != null) {
            recorder.release();
            recorder = null;
        }
        isRelease = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_capture);
        captureAction = (Button) findViewById(R.id.captureAction);
        initMediaDevices();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Looper.loop();
            }
        }).start();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_client_capture, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMediaDevices();
    }

    private SessionConfig createSessionConfig() {
        String out = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath();
        return new SessionConfig.Builder(MuxerWrapper.create(out, this)).withVideoBitrate(3000000).withPrivateVisibility(false).withLocation(true).withVideoResolution(1280, 720).build();
    }

    private void resetAVRecorder() {
        try {
            recorder.reset(createSessionConfig());
        } catch (IOException e) {
            // Could not create recording at given file path
            Toast.makeText(this, "Can't reset the recorder", Toast.LENGTH_LONG).show();
        }
    }

}



