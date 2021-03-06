package com.leboro.view.fragment.games.gameday;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leboro.MainActivity;
import com.leboro.R;
import com.leboro.service.ApplicationServiceProvider;
import com.leboro.util.cache.ApplicationCacheManager;
import com.leboro.util.exception.InstanceNotFoundException;
import com.leboro.view.adapters.games.GamesPagerAdapter;
import com.leboro.view.fragment.LoadableFragment;
import com.leboro.view.helper.gameday.GameDayHelper;
import com.leboro.view.listeners.CacheDataLoadedListener;

public class GameDaysFragment extends LoadableFragment implements CacheDataLoadedListener {

    private ViewPager viewPager;

    private View mView;

    private static Integer lastPosition = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.games_fragment, container, false);
        viewPager = (ViewPager) mView.findViewById(R.id.gamesPagerContainer);

        initializeListeners();

        final CacheDataLoadedListener dataLoadedListener = this;
        new Thread(new Runnable() {
            @Override
            public void run() {
                ApplicationServiceProvider.getStatisticsService().getDefaultGameDayInfo(dataLoadedListener);
            }
        }).start();

        return mView;
    }

    @Override
    public void onDataLoadedIntoCache() {
        Log.d(MainActivity.DEBUG_APP_NAME, "Data loaded into cache [" + GameDaysFragment.class.getSimpleName() + "]");

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    removeLoadingLayout(mView);
                    GamesPagerAdapter gamesPagerAdapter = new GamesPagerAdapter(getChildFragmentManager());
                    viewPager.setAdapter(gamesPagerAdapter); // to set current item it's necessary to set adapter first
                    if (lastPosition != null) {
                        Log.d(MainActivity.DEBUG_APP_NAME, "Detected last position on page [" + lastPosition + "]");
                        viewPager.setCurrentItem(lastPosition);
                    } else {
                        try {
                            viewPager.setCurrentItem(
                                    GameDayHelper
                                            .getGameDayCurrentPositionIndex(ApplicationCacheManager.getGameDayInfo()));
                        } catch (InstanceNotFoundException e) {
                            Log.e(MainActivity.DEBUG_APP_NAME, "Could not get game info for game day", e);
                        }
                    }
                }
            });
    }


    @Override
    protected void updateActionAndNavigationBar() {
        MainActivity.navigationView.setCheckedItem(R.id.nav_games);
    }

    private void initializeListeners() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                lastPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
