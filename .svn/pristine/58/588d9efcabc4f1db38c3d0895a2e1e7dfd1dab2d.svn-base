package dash.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import dash.activitiy.R;
import dash.listener.RemoteWatchListener;
import dash.network.ExchangeData;

import java.util.ArrayList;
import java.util.List;

public class VideoAdapter extends BaseAdapter {
    private List<ExchangeData.RemoteSessionInfo> remoteSessions;
    private LayoutInflater inflater;
    private Context context;

    public VideoAdapter(Context context) {
        this.context = context;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        remoteSessions = new ArrayList<>();

    }

    @Override
    public int getCount() {
        return remoteSessions.size();
    }

    @Override
    public Object getItem(int position) {
        return remoteSessions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            view = inflater.inflate(R.layout.remote_video_list_row, null);
        }
        TextView textView = (TextView) view.findViewById(R.id.remoteSessionDesc);
        Button button = (Button) view.findViewById(R.id.watchRemotSession);
        ExchangeData.RemoteSessionInfo remoteSessionInfo = remoteSessions.get(position);
        textView.setText("session-"+remoteSessionInfo.getSessionId()+"  "+remoteSessionInfo.getTitle());
        button.setText("Watch it!");
        button.setOnClickListener(new RemoteWatchListener(remoteSessionInfo.getMediaPath(), context));
        return view;
    }

    public Context getContext() {
        return context;
    }


    public void update(List<ExchangeData.RemoteSessionInfo> remoteMovies) {
        this.remoteSessions = remoteMovies;
    }
}

