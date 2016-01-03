package com.leboro.view.fragment.games.live;

import java.util.Collections;
import java.util.List;

import com.leboro.MainActivity;
import com.leboro.R;
import com.leboro.model.game.live.LiveData;
import com.leboro.model.game.live.LiveOverview;
import com.leboro.service.ApplicationServiceProvider;
import com.leboro.util.Constants;
import com.leboro.view.adapters.games.live.LiveGameDayOverviewAdapter;
import com.leboro.view.listeners.DataLoadedListener;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class LiveGameDayOverviewFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
                                                                     DataLoadedListener<LiveData> {

    private View mView;

    private LiveGameDayOverviewAdapter liveGameDayOverviewAdapter;

    private SwipeRefreshLayout liveGameDaySwipeLayout;

    // Auto sync
    protected Handler mHandler = new Handler();

    private final static int SYNC_INTERVAL = Integer
            .valueOf(MainActivity.properties.getProperty(Constants.DEFAULT_REFRESH_TIME_PROP));

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.live_game_day_fragment, container, false);
        initializeViews();
        return mView;
    }

    private void initializeViews() {
        ListView gamesList = (ListView) mView.findViewById(R.id.gameDayListView);
        liveGameDaySwipeLayout = (SwipeRefreshLayout) mView.findViewById(R.id.liveGameDaySwipeLayout);
        liveGameDaySwipeLayout.setOnRefreshListener(this);

        liveGameDayOverviewAdapter = new LiveGameDayOverviewAdapter(mView.getContext(), R.layout.game_day_row,
                Collections.<LiveOverview>emptyList());
        gamesList.setAdapter(liveGameDayOverviewAdapter);

        new Thread(mSyncTask).start();
    }

    private void updateLiveData() {
        ApplicationServiceProvider.getStatisticsService().getLiveData(this);
    }

    @Override
    public void onRefresh() {
        updateLiveData();
        liveGameDaySwipeLayout.setRefreshing(false);
    }

    protected Runnable mSyncTask = new Runnable() {
        @Override
        public void run() {
            updateLiveData();
            mHandler.postDelayed(mSyncTask, SYNC_INTERVAL);
        }
    };

    @Override
    public void onDetach() {
        super.onDetach();
        mHandler.removeCallbacks(mSyncTask);
    }

    @Override
    public void onDataLoaded() {

    }

    @Override
    public void onDataLoaded(final LiveData liveData) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                View loadingView = mView.findViewById(R.id.gameDayFragmentLoadingFrame);
                if (loadingView.getVisibility() != View.GONE) {
                    loadingView.setVisibility(View.GONE);
                    liveGameDaySwipeLayout.setVisibility(View.VISIBLE);
                }

                List<LiveOverview> gameDayOverviews = liveData.getOverview().getCompetitions().get(0).getOverviews();
                liveGameDayOverviewAdapter.updateDataAndNotifify(gameDayOverviews);
            }
        });
    }
}
