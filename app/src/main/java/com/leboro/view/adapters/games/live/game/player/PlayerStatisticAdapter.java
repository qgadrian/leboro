package com.leboro.view.adapters.games.live.game.player;

import java.util.List;

import com.leboro.R;
import com.leboro.model.game.live.statistic.player.PlayerStatistic;
import com.leboro.model.team.Team;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static android.support.v7.widget.RecyclerView.ViewHolder;

public class PlayerStatisticAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected final static int HEADER_TITLE = 1;

    protected final static int ELEMENT_ROW = 2;

    private final static int homeTeamTitlePosition = 0;

    private int awayTeamTitlePosition = 0;

    private List<PlayerStatistic> elements;

    private Team homeTeam;

    private Team awayTeam;

    public PlayerStatisticAdapter(List<PlayerStatistic> elements, Team homeTeam, Team awayTeam, int
            awayTeamTitlePosition) {
        this.awayTeamTitlePosition = awayTeamTitlePosition + 1;
        this.elements = elements;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == homeTeamTitlePosition || position == awayTeamTitlePosition) {
            return HEADER_TITLE;
        }

        return ELEMENT_ROW;
    }

    @Override
    public int getItemCount() {
        // home and away team headers
        return elements.size() + 2;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case HEADER_TITLE:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.live_game_player_statistic_team_header, parent, false);
                return new PlayerStatisticTitleViewHolder(view);
            default:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.live_game_player_statistic_row, parent, false);
                return new PlayerStatisticViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder instanceof PlayerStatisticTitleViewHolder) {
            if (position == awayTeamTitlePosition) {
                ((PlayerStatisticTitleViewHolder) holder).setHolderData(awayTeam);
            } else {
                ((PlayerStatisticTitleViewHolder) holder).setHolderData(homeTeam);
            }
        } else if (holder instanceof PlayerStatisticViewHolder) {
            PlayerStatistic element = null;
            if (position < awayTeamTitlePosition) {
                element = elements.get(position - 1);
            }
            if (position > awayTeamTitlePosition) {
                element = elements.get(position - 2);
            }

            ((PlayerStatisticViewHolder) holder).setHolderData(element);
        }

    }
}