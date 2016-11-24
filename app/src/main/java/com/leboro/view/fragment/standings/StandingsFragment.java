package com.leboro.view.fragment.standings;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leboro.MainActivity;
import com.leboro.R;
import com.leboro.service.ApplicationServiceProvider;
import com.leboro.util.html.HTMLHelper;
import com.leboro.view.adapters.standing.StandingPagerAdapter;
import com.leboro.view.fragment.LoadableFragment;
import com.leboro.view.listeners.CacheDataLoadedListener;

public class StandingsFragment extends LoadableFragment implements CacheDataLoadedListener {

    private ViewPager viewPager;

    private View mView;

    private static Integer lastPosition = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.standings_fragment, container, false);
        viewPager = (ViewPager) mView.findViewById(R.id.standingsPagerContainer);

        initializeListeners();

        final CacheDataLoadedListener dataLoadedListener = this;
        new Thread(new Runnable() {
            @Override
            public void run() {
                ApplicationServiceProvider.getStandingService().getPlayerStandings(HTMLHelper.StandingType
                        .POINTS.getId(), dataLoadedListener);
            }
        }).start();

        return mView;
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

    @Override
    public void onDataLoadedIntoCache() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                removeLoadingLayout(mView);
                StandingPagerAdapter standingPagerAdapter = new StandingPagerAdapter(getChildFragmentManager());
                viewPager.setAdapter(standingPagerAdapter);

                if (lastPosition != null) {
                    Log.d(MainActivity.DEBUG_APP_NAME, "Detected last position on page [" + lastPosition + "]");
                    viewPager.setCurrentItem(lastPosition);
                } else {
                    try {
                        viewPager.setCurrentItem(0);
                    } catch (Exception e) {
                        Log.e(MainActivity.DEBUG_APP_NAME, "Could not get standing info", e);
                    }
                }
            }
        });
    }

    @Override
    protected void updateActionAndNavigationBar() {
        MainActivity.navigationView.setCheckedItem(R.id.nav_standings);
    }
}
