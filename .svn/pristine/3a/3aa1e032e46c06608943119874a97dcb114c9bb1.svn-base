package dash.activitiy;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import dash.adapter.PagerAdapter;
import dash.asyntask.RemoteMoviesRetrieveTask;


public class ServerVideoViewerActivity extends FragmentActivity {
    private ViewPager viewPager;
    private boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_video_viewer);
        viewPager = (ViewPager) findViewById(R.id.storagePager);
        final PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(pagerAdapter);
        ProgressDialog progressDialog = ProgressDialog.show(ServerVideoViewerActivity.this, "Retrieving data from server", "Please Wait..", true);
        new RemoteMoviesRetrieveTask(progressDialog).addVodAdapter(pagerAdapter.getVoDAdapter()).addLiveAdapter(pagerAdapter.getLiveAdapter()).execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_storage_viewer, menu);
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




