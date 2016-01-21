package dash.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import dash.view.LocalVideoViewerFragment;
import dash.view.RemoteVideoViewerFragment;

/**
 * Created by Quincy on 15/9/29.
 */
public class PagerAdapter extends FragmentPagerAdapter {
    @Override
    public int getItemPosition(Object object) {
        return FragmentPagerAdapter.POSITION_NONE;
    }

    private Fragment[] fragments = new Fragment[3];
    private String[] titles = new String[]{"Live", "VoD","Local"};
    private VideoAdapter VoDAdapter;
    private VideoAdapter liveAdapter;
    private LocalVideoAdapter localVideoAdapter;

    public LocalVideoAdapter getLocalVideoAdapter() {
        return localVideoAdapter;
    }

    public PagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        VoDAdapter = new VideoAdapter(context);
        liveAdapter = new VideoAdapter(context);
        localVideoAdapter = new LocalVideoAdapter(context);
        fragments[0] = new RemoteVideoViewerFragment(RemoteVideoViewerFragment.LIVE_SESSION_FRAGMENT,liveAdapter);
        fragments[1] = new RemoteVideoViewerFragment(RemoteVideoViewerFragment.VOD_SESSION_FRAGMENT,VoDAdapter);
        fragments[2] = new LocalVideoViewerFragment(localVideoAdapter);

    }

    @Override
    public Fragment getItem(int i) {
        return fragments[i];
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    public VideoAdapter getVoDAdapter() {
        return VoDAdapter;
    }

    public VideoAdapter getLiveAdapter() {
        return liveAdapter;
    }

    @Override
    public int getCount() {
        return fragments.length;
    }
}