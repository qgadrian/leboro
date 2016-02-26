package com.leboro.view.fragment.games.live.game.statistic.player;

import java.util.List;

import org.apache.commons.collections4.ListUtils;

import com.leboro.MainActivity;
import com.leboro.R;
import com.leboro.model.game.live.statistic.player.PlayerStatistic;
import com.leboro.view.adapters.games.live.game.player.PlayerStatisticAdapter;
import com.leboro.view.fragment.games.live.game.LiveGameViewFragment;
import com.leboro.view.fragment.games.live.game.statistic.StatisticPageFragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PlayerStatisticFragment extends StatisticPageFragment {

    private View mView;

    private RecyclerView playerStatisticListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.live_game_player_statistic, container, false);

        initializeView();

        return mView;
    }

    private void initializeView() {
        RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.playerStatisticList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(buildPlayerStatisticAdapter());

    }

    @Override
    public int getTitleStringResource() {
        return R.string.live_game_tab_player_stats;
    }

    @Override
    public void notifySetDataChanged() {
        if (playerStatisticListView != null) {
            playerStatisticListView.swapAdapter(buildPlayerStatisticAdapter(), true);
        } else {
            Log.d(MainActivity.DEBUG_APP_NAME, "Trying to update data on null adapter");
        }
    }

    private static PlayerStatisticAdapter buildPlayerStatisticAdapter() {
        List<PlayerStatistic> homeTeamPlayerStatistics = LiveGameViewFragment.data.getGameStatistics()
                .getHomeTeamPlayerStatistics();
        List<PlayerStatistic> awayTeamPlayerStatistics = LiveGameViewFragment.data.getGameStatistics()
                .getAwayTeamPlayerStatistics();
        List<PlayerStatistic> playerStatistics = ListUtils.sum(homeTeamPlayerStatistics, awayTeamPlayerStatistics);

        return new PlayerStatisticAdapter(playerStatistics,
                LiveGameViewFragment.data.getGameResult().getHomeTeam(),
                LiveGameViewFragment.data.getGameResult().getAwayTeam(),
                homeTeamPlayerStatistics.size());
    }
}
