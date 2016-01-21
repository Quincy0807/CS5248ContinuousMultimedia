package dash.asyntask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;
import dash.adapter.VideoAdapter;
import dash.network.ExchangeData;
import dash.network.ExchangeDataState;
import dash.network.NetworkConnection;

import java.io.IOException;

/**
 * Created by Quincy on 15/9/28.
 */

public class RemoteMoviesRetrieveTask extends AsyncTask<Void,String,Void> {
    private static VideoAdapter vodAdapter;
    private static VideoAdapter liveAdapter;
    private Context context;
    private ProgressDialog progressDialog;
    private SwipeRefreshLayout swipeRefreshLayout;

    public RemoteMoviesRetrieveTask(ProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
        this.swipeRefreshLayout = null;
    }

    public RemoteMoviesRetrieveTask(SwipeRefreshLayout swipeRefreshLayout) {
        this.progressDialog = null;
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    public RemoteMoviesRetrieveTask addVodAdapter(VideoAdapter videoAdapter) {
        vodAdapter = videoAdapter;
        this.context = videoAdapter.getContext();
        return this;
    }
    public RemoteMoviesRetrieveTask addLiveAdapter(VideoAdapter videoAdapter) {
        liveAdapter = videoAdapter;
        this.context = videoAdapter.getContext();
        return this;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            ExchangeData exchangeData = new NetworkConnection()
                    .makePost(NetworkConnection.MOVIES_RETRIEVE_URL)
                    .addExchangeData(new ExchangeData()
                            .addExchangeDataState(ExchangeDataState.REQUEST_PLAYLIST_ANDROID))
                    .jsonResponse();
            if (vodAdapter != null) {
                vodAdapter.update(exchangeData.getVodSessions());
            }
            if (liveAdapter != null) {
                liveAdapter.update(exchangeData.getLiveSessions());
            }
            publishProgress("OK");
        } catch (IOException e) {
            publishProgress("error", e.getMessage());
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        if (values[0].equals("OK")) {
            if (liveAdapter != null) {
                liveAdapter.notifyDataSetChanged();
            }
            if (vodAdapter != null) {
                vodAdapter.notifyDataSetChanged();
            }
        } else {
            Toast.makeText(context, "error occurred when retrieving movies from server. " + values[1], Toast.LENGTH_LONG).show();
        }
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
