package com.leboro.view.fragment.games.live;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.leboro.MainActivity;
import com.leboro.R;
import com.leboro.model.game.live.LiveData;
import com.leboro.model.game.live.LiveOverview;
import com.leboro.service.ApplicationServiceProvider;
import com.leboro.util.Constants;
import com.leboro.view.adapters.games.live.LiveGameDayOverviewAdapter;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class LiveGameDayOverviewFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

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
        hideListHeader();

        return mView;
    }

    private void hideListHeader() {
        TextView headerTitle = (TextView) mView.findViewById(R.id.gameDayListHeaderTitle);
        headerTitle.setVisibility(View.GONE);
    }

    private void initializeViews() {
        ListView gamesList = (ListView) mView.findViewById(R.id.gameDayListView);
        liveGameDaySwipeLayout = (SwipeRefreshLayout) mView.findViewById(R.id.liveGameDaySwipeLayout);

        liveGameDaySwipeLayout.setOnRefreshListener(this);

        LiveData liveData = ApplicationServiceProvider.getStatisticsService().getLiveData();
        List<LiveOverview> gameDayOverviews = liveData.getOverview().getCompetitions().get(0).getOverviews();

        liveGameDayOverviewAdapter = new LiveGameDayOverviewAdapter(mView.getContext(), R.layout.game_day_row,
                gameDayOverviews);

        if (!CollectionUtils.isEmpty(gameDayOverviews)) {
            gamesList.setAdapter(liveGameDayOverviewAdapter);
        }

        mHandler.postDelayed(mSyncTask, SYNC_INTERVAL);
    }

    private void updateLiveData() {
        LiveData liveData = ApplicationServiceProvider.getStatisticsService().getLiveData();
        List<LiveOverview> gameDayOverviews = liveData.getOverview().getCompetitions().get(0).getOverviews();
        liveGameDayOverviewAdapter.updateData(gameDayOverviews);
        liveGameDayOverviewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        updateLiveData();
        liveGameDaySwipeLayout.setRefreshing(false);
    }

    /**
     * Thread var for auto sync data feature *
     */
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

}
