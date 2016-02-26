package com.leboro.view.fragment.games.live.game.statistic.playbyplay;

import com.leboro.MainActivity;
import com.leboro.R;
import com.leboro.view.adapters.games.live.game.playbyplay.PlayByPlayAdapter;
import com.leboro.view.adapters.games.live.game.playbyplay.PlayByPlayViewHolder;
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

public class PlayByPlayFragment extends StatisticPageFragment {

    private View mView;

    private PlayByPlayAdapter playByPlayAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.live_game_play_by_play, container, false);

        initializeView();

        return mView;
    }

    private void initializeView() {
        playByPlayAdapter = new PlayByPlayAdapter(
                LiveGameViewFragment.data.getPlayByPlay().getLines(), R.layout.live_game_play_by_play_row,
                PlayByPlayViewHolder.class);

        RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.playByPlayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(playByPlayAdapter);
    }

    @Override
    public int getTitleStringResource() {
        return R.string.live_game_tab_play_by_play;
    }

    @Override
    public void notifySetDataChanged() {
        if (playByPlayAdapter != null) {
            playByPlayAdapter.updateDataAndNotify(LiveGameViewFragment.data.getPlayByPlay().getLines());
        } else {
            Log.d(MainActivity.DEBUG_APP_NAME, "Trying to update data on null adapter");
        }
    }
}
