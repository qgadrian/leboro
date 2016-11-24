package com.leboro.view.fragment.rosters;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leboro.MainActivity;
import com.leboro.R;
import com.leboro.service.ApplicationServiceProvider;
import com.leboro.view.fragment.LoadableFragment;
import com.leboro.view.listeners.CacheDataLoadedListener;

public class RostersFragment extends LoadableFragment implements CacheDataLoadedListener {

    private View mView;

    private final CacheDataLoadedListener dataLoadedListener = this;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.news_fragment, container, false);

        new Thread(new Runnable() {
            @Override
            public void run() {
                ApplicationServiceProvider.getNewsService().getAllProviderNews(dataLoadedListener);
            }
        }).start();

        return mView;
    }

    @Override
    public void onDataLoadedIntoCache() {
        Log.d(MainActivity.DEBUG_APP_NAME, "todo");
    }

    @Override
    protected void updateActionAndNavigationBar() {
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.navigation_drawer_news));
        MainActivity.navigationView.setCheckedItem(R.id.nav_news);
    }
}
