package com.leboro.view.fragment.games.live;

import java.util.Collections;
import java.util.List;

import com.leboro.MainActivity;
import com.leboro.R;
import com.leboro.model.api.live.overview.LiveData;
import com.leboro.model.api.live.overview.LiveGameOverview;
import com.leboro.service.ApplicationServiceProvider;
import com.leboro.util.Constants;
import com.leboro.util.properties.PropertiesHelper;
import com.leboro.view.adapters.games.live.LiveGameDayOverviewAdapter;
import com.leboro.view.fragment.LoadableFragment;
import com.leboro.view.fragment.games.live.game.LiveGameViewFragment;
import com.leboro.view.listeners.DataLoadedListener;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class LiveGameDayOverviewFragment extends LoadableFragment implements SwipeRefreshLayout.OnRefreshListener,
                                                                             DataLoadedListener<LiveData> {

    private View mView;

    private LiveGameDayOverviewAdapter liveGameDayOverviewAdapter;

    ListView gamesList;

    private SwipeRefreshLayout liveGameDaySwipeLayout;

    // Auto sync
    protected Handler mHandler = new Handler();

    private final static int SYNC_INTERVAL = Integer
            .valueOf(PropertiesHelper.getProperty(Constants.DEFAULT_REFRESH_TIME_PROP));

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.live_game_day_fragment, container, false);
        initializeViews();
        initializeListeners();
        return mView;
    }

    private void initializeListeners() {
        gamesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LiveGameOverview liveGameOverview = liveGameDayOverviewAdapter.getItem(position);
                long gameId = liveGameOverview.getGameId();
                Bundle bundle = new Bundle(1);
                bundle.putLong(Constants.BUNDLE_ARG_GAME_ID, gameId);
                LiveGameViewFragment liveGameViewFragment = new LiveGameViewFragment();
                liveGameViewFragment.setArguments(bundle);

                String liveGameViewTitle = getString(R.string.navigation_game_view_title, liveGameOverview.getHomeTeam
                        ().getName(), liveGameOverview.getAwayTeam().getName());

                ((MainActivity) getActivity()).fragmentTransition(liveGameViewFragment, liveGameViewTitle);
            }
        });
    }

    private void initializeViews() {
        gamesList = (ListView) mView.findViewById(R.id.gameDayListView);
        liveGameDaySwipeLayout = (SwipeRefreshLayout) mView.findViewById(R.id.liveGameDaySwipeLayout);
        liveGameDaySwipeLayout.setOnRefreshListener(this);

        liveGameDayOverviewAdapter = new LiveGameDayOverviewAdapter(mView.getContext(), R.layout.game_day_row,
                Collections.<LiveGameOverview>emptyList());
        gamesList.setAdapter(liveGameDayOverviewAdapter);

        new Thread(mSyncTask).start();
    }

    private void updateLiveData() {
        ApplicationServiceProvider.getStatisticsService().getLiveData(this);
    }

    @Override
    public void onRefresh() {
        updateLiveData();
        liveGameDaySwipeLayout.setRefreshing(true);
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
    public void onDataLoaded(final LiveData liveData) {
        if (isVisible()) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d(MainActivity.DEBUG_APP_NAME, "Updated live games data");
                    liveGameDaySwipeLayout.setRefreshing(false);
                    removeLoadingLayoutAndShowResource(mView, liveGameDaySwipeLayout);
                    List<LiveGameOverview> gameDayOverviews = liveData.getOverview().getCompetitions().get(0)
                            .getOverviews();
                    liveGameDayOverviewAdapter.updateDataAndNotify(gameDayOverviews);
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.navigation_drawer_live_games));
    }
}
