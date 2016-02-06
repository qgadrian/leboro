package com.leboro.view.fragment.games.live.game;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.leboro.MainActivity;
import com.leboro.R;
import com.leboro.model.game.GameResult;
import com.leboro.model.game.live.LiveGame;
import com.leboro.model.team.Team;
import com.leboro.service.ApplicationServiceProvider;
import com.leboro.util.Constants;
import com.leboro.util.calendar.CalendarUtils;
import com.leboro.util.exception.NoLiveDataException;
import com.leboro.util.game.GameUtil;
import com.leboro.view.adapters.games.live.game.LiveGameViewAdapter;
import com.leboro.view.fragment.LoadableFragment;
import com.leboro.view.fragment.games.live.game.statistic.StatisticPageFragment;
import com.leboro.view.fragment.games.live.game.statistic.playbyplay.PlayByPlayFragment;
import com.leboro.view.fragment.games.live.game.statistic.player.PlayerStatisticFragment;
import com.leboro.view.helper.gameday.GameDayHelper;
import com.leboro.view.listeners.DataLoadedListener;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class LiveGameViewFragment extends LoadableFragment implements SwipeRefreshLayout.OnRefreshListener,
                                                                      DataLoadedListener<LiveGame> {

    private View mView;

    public static LiveGame data; // TODO remove me?

    private SwipeRefreshLayout liveGameDaySwipeLayout;

    private long gameId;

    private LiveGameViewAdapter liveGameViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.live_game_view_fragment, container, false);

        gameId = getArguments().getLong(Constants.BUNDLE_ARG_GAME_ID);

        initializeViews();
        updateLiveData();

        return mView;
    }

    private void initializeViews() {
        ViewPager viewPager = (ViewPager) mView.findViewById(R.id.gamesPagerContainer);

        liveGameViewAdapter = new LiveGameViewAdapter(getChildFragmentManager());
        liveGameViewAdapter.addViewPageStatisticFragments(PlayByPlayFragment.class, PlayerStatisticFragment.class);
        viewPager.setAdapter(liveGameViewAdapter);

        liveGameDaySwipeLayout = (SwipeRefreshLayout) mView.findViewById(R.id.liveGameSwipeLayout); // TODO unify swipes
        liveGameDaySwipeLayout.setOnRefreshListener(this);
    }

    private void refreshView() {
        if (data == null) {
            Toast.makeText(getContext(), R.string.toast_no_live_data_found, Toast.LENGTH_SHORT).show();
            removeLoadingLayoutAndShowResource(mView, R.id.liveGameNoInfoMessage);
            TextView liveGameNoInfo = (TextView) mView.findViewById(R.id.liveGameNoInfoMessage);
            liveGameNoInfo.setText(getString(R.string.live_game_no_data_message));
        } else {
            removeLoadingLayoutAndShowResource(mView, R.id.gamesPagerContainer);

            ImageLoader imageLoader = ApplicationServiceProvider.getNetworkImageLoaderService().getImageLoader();

            NetworkImageView homeTeamLogo = (NetworkImageView) mView.findViewById(R.id.gameDayRowHomeLogo);
            NetworkImageView awayTeamLogo = (NetworkImageView) mView.findViewById(R.id.gameDayRowAwayLogo);
            TextView homeTeamScore = (TextView) mView.findViewById(R.id.gameDayHomeScore);
            TextView awayTeamScore = (TextView) mView.findViewById(R.id.gameDayAwayScore);
            TextView gameDate = (TextView) mView.findViewById(R.id.gameDayRowStatus);

            Team homeTeam = data.getGameResult().getHomeTeam();
            Team awayTeam = data.getGameResult().getAwayTeam();
            GameResult gameResult = data.getGameResult();

            homeTeamLogo.setImageUrl(homeTeam.getLogoUrl(), imageLoader);
            awayTeamLogo.setImageUrl(awayTeam.getLogoUrl(), imageLoader);

            if (GameDayHelper.isStarted(gameResult)) {
                homeTeamScore.setText(String.valueOf(gameResult.getHomeScore()));
                awayTeamScore.setText(String.valueOf(gameResult.getAwayScore()));
            } else {
                homeTeamScore.setText("");
                awayTeamScore.setText("");
            }

            if (!GameDayHelper.isStarted(gameResult)) {

                gameDate.setText(CalendarUtils.toString(gameResult.getStartDate()));

            } else if (!gameResult.getHomeScore().equals(gameResult.getAwayScore())
                    && data.getCurrentGameStatus().getQuarter() >= 4
                    && (data.getCurrentGameStatus().getTimeLeft().equals("00:00")
                    || data.getCurrentGameStatus().getTimeLeft().equalsIgnoreCase("FINAL"))) {

                gameDate.setText(MainActivity.context.getString(R.string.live_game_overview_current_status_finished));

            } else {
                String statusInfoString = MainActivity.context.getString(R.string.live_game_overview_current_status)
                        .replace("{quarter}", GameUtil.getGamePeriodString(data.getCurrentGameStatus().getQuarter()))
                        .replace("{timeLeft}", data.getCurrentGameStatus().getTimeLeft());
                gameDate.setText(statusInfoString);
            }
        }
    }

    private void updateLiveData() {
        final DataLoadedListener dataLoadedListener = this;
        AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    ApplicationServiceProvider.getLiveService().getLiveGameData(gameId, dataLoadedListener);
                } catch (NoLiveDataException e) {
                    dataLoadedListener.onDataLoaded(null);
                }
            }
        });
    }

    @Override
    public void onDataLoaded(final LiveGame data) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d(MainActivity.DEBUG_APP, "Updated live game data for game [" + gameId + "]");
                liveGameDaySwipeLayout.setRefreshing(false);
                LiveGameViewFragment.data = data;
                for (StatisticPageFragment statisticPageFragment : liveGameViewAdapter.getInstancedFragments()) {
                    statisticPageFragment.notifySetDataChanged();
                }
                refreshView();
            }
        });
    }

    @Override
    public void onRefresh() {
        updateLiveData();
        liveGameDaySwipeLayout.setRefreshing(true);
    }
}