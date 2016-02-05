package com.leboro.view.fragment.games;

import java.util.List;

import com.leboro.R;
import com.leboro.model.game.GameDayInfo;
import com.leboro.service.ApplicationServiceProvider;
import com.leboro.view.adapters.games.GamesPagerAdapter;
import com.leboro.view.fragment.LoadableFragment;
import com.leboro.view.helper.gameday.GameDayHelper;
import com.leboro.view.listeners.DataLoadedListener;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class GamesFragment extends LoadableFragment implements DataLoadedListener<GameDayInfo> {

    private ViewPager viewPager;

    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.games_fragment, container, false);
        viewPager = (ViewPager) mView.findViewById(R.id.games_pager_container);

        final DataLoadedListener dataLoadedListener = this;
        AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                ApplicationServiceProvider.getStatisticsService().getDefaultGameDayInfo(dataLoadedListener);
            }
        });

        return mView;
    }

    @Override
    public void onDataLoadedIntoCache() {

    }

    @Override
    public void onDataLoaded(final GameDayInfo gameDayInfo) {
        if (isVisible()) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    removeLoadingLayout(mView);
                    GamesPagerAdapter gamesPagerAdapter = new GamesPagerAdapter(getChildFragmentManager());
                    viewPager.setAdapter(gamesPagerAdapter); // to set current item it's necessary to set adapter first
                    viewPager.setCurrentItem(GameDayHelper.getGameDayCurrentPositionIndex(gameDayInfo));
                }
            });
        }
    }

    public void onDataLoaded(List<GameDayInfo> data) {

    }
}
