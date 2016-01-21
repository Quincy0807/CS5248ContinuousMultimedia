package dash.view;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import dash.activitiy.R;
import dash.adapter.LocalVideoAdapter;

/**
 * Created by Quincy on 15/11/16.
 */
public class LocalVideoViewerFragment extends Fragment {
    private LocalVideoAdapter localVideoAdapter;
    public LocalVideoViewerFragment(LocalVideoAdapter localVideoAdapter) {
        this.localVideoAdapter = localVideoAdapter;
    }

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rooter = inflater.inflate(R.layout.local_movies_layout, container, false);
        final ListView localMovies = (ListView) rooter.findViewById(R.id.localListView);
        localMovies.setAdapter(localVideoAdapter);
        return rooter;

    }
}
