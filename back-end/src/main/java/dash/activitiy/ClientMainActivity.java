package dash.activitiy;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import dash.listener.CaptureButtonListener;
import dash.listener.StorageButtonListener;


public class ClientMainActivity extends Activity {

    private void initViews() {
        Button captureButton = (Button) findViewById(R.id.captureButton);
        Button serverVideoButton = (Button) findViewById(R.id.storageButton);

        captureButton.setOnClickListener(new CaptureButtonListener(this));
        serverVideoButton.setOnClickListener(new StorageButtonListener(this));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_main);
        initViews();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_client_main, menu);
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
}


