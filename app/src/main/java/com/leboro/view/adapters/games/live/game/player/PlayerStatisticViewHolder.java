package com.leboro.view.adapters.games.live.game.player;

import org.apache.commons.lang3.StringUtils;

import com.android.volley.toolbox.NetworkImageView;
import com.leboro.R;
import com.leboro.model.game.live.statistic.player.PlayerStatistic;
import com.leboro.service.ApplicationServiceProvider;
import com.leboro.view.adapters.BaseViewHolder;

import android.view.View;
import android.widget.TextView;

public class PlayerStatisticViewHolder extends BaseViewHolder<PlayerStatistic> {

    public TextView playerName, points, assists, rebounds;

    public TextView fga, fgm, tpa, tpm, fta, ftm, rebOff, rebDef, turnOvers, fouls, steals, blocks;

    public NetworkImageView playerLogo;

    private View mView;

    public PlayerStatisticViewHolder(View view) {
        super(view);
        this.mView = view;
        playerName = (TextView) view.findViewById(R.id.playerName);
        points = (TextView) view.findViewById(R.id.playerStatisticHighlightPoints);
        assists = (TextView) view.findViewById(R.id.playerStatisticHighlightAssists);
        rebounds = (TextView) view.findViewById(R.id.playerStatisticHighlightRebounds);
        playerLogo = (NetworkImageView) view.findViewById(R.id.playerLogo);

        fga = (TextView) view.findViewById(R.id.playerStatisticFieldGoalAttempted);
        fgm = (TextView) view.findViewById(R.id.playerStatisticFieldGoalMade);
        tpa = (TextView) view.findViewById(R.id.playerStatistic3PointAttempted);
        tpm = (TextView) view.findViewById(R.id.playerStatistic3PointMade);
        fta = (TextView) view.findViewById(R.id.playerStatisticFreeThrowAttempted);
        ftm = (TextView) view.findViewById(R.id.playerStatisticFreeThrowMade);
        rebOff = (TextView) view.findViewById(R.id.playerStatisticOffensiveRebounds);
        rebDef = (TextView) view.findViewById(R.id.playerStatisticDefensiveRebounds);
        turnOvers = (TextView) view.findViewById(R.id.playerStatisticTurnOvers);
        fouls = (TextView) view.findViewById(R.id.playerStatisticPersonalFouls);
        steals = (TextView) view.findViewById(R.id.playerStatisticSteals);
        blocks = (TextView) view.findViewById(R.id.playerStatisticBlocks);
    }

    @Override
    public void setHolderData(PlayerStatistic element) {
        playerName.setText(element.getPlayerName());
        points.setText(mView.getContext().getResources().getQuantityString(R.plurals.statistic_highlight_points,
                element.getPoints(), element.getPoints()));
        assists.setText(mView.getContext().getResources().getQuantityString(R.plurals.statistic_highlight_assists,
                element.getAssists(), element.getAssists()));
        rebounds.setText(mView.getContext().getResources().getQuantityString(R.plurals.statistic_highlight_rebounds,
                element.getTotalRebounds(), element.getTotalRebounds()));

        fga.setText(String.valueOf(element.getFieldGoalsAttempted()));
        fgm.setText(String.valueOf(element.getFieldGoalsMade()));
        tpa.setText(String.valueOf(element.getThreePointersAttempted()));
        tpm.setText(String.valueOf(element.getThreePointersMade()));
        fta.setText(String.valueOf(element.getFreeThrowsAttempted()));
        ftm.setText(String.valueOf(element.getFreeThrowsMade()));
        rebOff.setText(String.valueOf(element.getOffensiveRebounds()));
        rebDef.setText(String.valueOf(element.getDefensiveRebounds()));
        turnOvers.setText(String.valueOf(element.getTurnovers()));
        fouls.setText(String.valueOf(element.getFouls()));
        steals.setText(String.valueOf(element.getSteals()));
        blocks.setText(String.valueOf(0)); //TODO

        if (!StringUtils.isBlank(element.getPlayerImageUrl())) {
            playerLogo.setImageUrl(element.getPlayerImageUrl(), ApplicationServiceProvider.getNetworkImageLoaderService
                    ().getImageLoader());
        }
    }

}
