package dash.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import dash.activitiy.R;
import dash.adapter.VideoAdapter;
import dash.asyntask.RemoteMoviesRetrieveTask;

/**
 * Created by Quincy on 15/9/28.
 */
public class RemoteVideoViewerFragment extends Fragment {
    public static final int VOD_SESSION_FRAGMENT = 1;
    public static final int LIVE_SESSION_FRAGMENT = 2;
    private VideoAdapter adapter;
    private int listViewId;
    private int layoutResource;
    private int fragmentType;

    public RemoteVideoViewerFragment(int fragmentType,VideoAdapter adapter) {
        this.adapter = adapter;
        this.layoutResource = R.layout.server_video_viewer;
        this.listViewId = R.id.remoteListViewer;
        this.fragmentType = fragmentType;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rooter = inflater.inflate(layoutResource, container, false);
        final ListView remoteMovies = (ListView) rooter.findViewById(listViewId);
        remoteMovies.setAdapter(adapter);
        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) rooter.findViewById(R.id.swipeRefresher);
        remoteMovies.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                boolean enable = false;
                if (remoteMovies.getChildCount() > 0) {
                    boolean firstItem = remoteMovies.getFirstVisiblePosition() == 0;
                    boolean topOfFirstItem = remoteMovies.getChildAt(0).getTop() == 0;
                    enable = firstItem && topOfFirstItem;
                }else {
                    enable = true;
                }
                swipeRefreshLayout.setEnabled(enable);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                switch (fragmentType) {
                    case VOD_SESSION_FRAGMENT:
                        new RemoteMoviesRetrieveTask(swipeRefreshLayout).addVodAdapter(adapter).execute();
                    break;
                    case LIVE_SESSION_FRAGMENT:
                        new RemoteMoviesRetrieveTask(swipeRefreshLayout).addLiveAdapter(adapter).execute();
                        break;
                }
            }
        });
        return rooter;
    }
}
