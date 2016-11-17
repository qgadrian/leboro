package com.leboro.view.fragment.games.live.game.statistic.team;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.leboro.MainActivity;
import com.leboro.R;
import com.leboro.model.game.live.statistic.team.TeamStatistic;
import com.leboro.service.ApplicationServiceProvider;
import com.leboro.view.fragment.games.live.game.LiveGameViewFragment;
import com.leboro.view.fragment.games.live.game.statistic.StatisticPageFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TeamStatisticFragment extends StatisticPageFragment {

    private final static ImageLoader imageLoader = ApplicationServiceProvider.getNetworkImageLoaderService()
            .getImageLoader();

    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.live_game_teams_statistics, container, false);

        buildTeamsStatisticsView();

        return mView;
    }

    @Override
    public int getTitleStringResource() {
        return R.string.live_game_tab_team_stats;
    }

    @Override
    public void notifySetDataChanged() {
        if (mView != null) {
            buildTeamsStatisticsView();
        } else {
            Log.d(MainActivity.DEBUG_APP_NAME, "Trying to update data on null adapter");
        }
    }

    private void buildTeamsStatisticsView() {
        TeamStatistic homeTeamStatistics = LiveGameViewFragment.data.getGameStatistics()
                .getHomeTeamStatistics();
        TeamStatistic awayTeamStatistics = LiveGameViewFragment.data.getGameStatistics()
                .getAwayTeamStatistics();

        setHomeTeamData(homeTeamStatistics);
        setAwayTeamData(awayTeamStatistics);
    }

    private void setHomeTeamData(TeamStatistic teamStatistic) {
        setTeamData(teamStatistic, true);
    }

    private void setAwayTeamData(TeamStatistic teamStatistic) {
        setTeamData(teamStatistic, false);
    }

    private void setTeamData(TeamStatistic team, boolean isHomeTeam) {

        NetworkImageView teamLogo;

        TextView fieldGoals, threePoints, freeThrows;

        TextView fieldGoalsPercentage, threePointsPercentage, freeThrowsPercentage;

        TextView defensiveRebounds, offensiveRebounds, totalRebounds;

        TextView assists, steals, turnovers, blocks, fouls;

        if (isHomeTeam) {
            teamLogo = (NetworkImageView) mView.findViewById(R.id.homeTeamLogo);
            fieldGoals = (TextView) mView.findViewById(R.id.homeTeamStatisticFieldGoals);
            threePoints = (TextView) mView.findViewById(R.id.homeTeamStatisticThreePoint);
            freeThrows = (TextView) mView.findViewById(R.id.homeTeamStatisticFreeThrow);
            fieldGoalsPercentage = (TextView) mView.findViewById(R.id.homeTeamStatisticFieldGoalsPercentage);
            threePointsPercentage = (TextView) mView.findViewById(R.id.homeTeamStatisticThreePointPercentage);
            freeThrowsPercentage = (TextView) mView.findViewById(R.id.homeTeamStatisticFreeThrowPercentage);
            defensiveRebounds = (TextView) mView.findViewById(R.id.homeTeamStatisticDefensiveRebounds);
            offensiveRebounds = (TextView) mView.findViewById(R.id.homeTeamStatisticOffensiveRebounds);
            totalRebounds = (TextView) mView.findViewById(R.id.homeTeamStatisticTotalRebounds);
            assists = (TextView) mView.findViewById(R.id.homeTeamStatisticAssists);
            steals = (TextView) mView.findViewById(R.id.homeTeamStatisticSteals);
            turnovers = (TextView) mView.findViewById(R.id.homeTeamStatisticTurnovers);
            blocks = (TextView) mView.findViewById(R.id.homeTeamStatisticBlocks);
            fouls = (TextView) mView.findViewById(R.id.homeTeamStatisticFouls);
        } else {
            teamLogo = (NetworkImageView) mView.findViewById(R.id.awayTeamLogo);
            fieldGoals = (TextView) mView.findViewById(R.id.awayTeamStatisticFieldGoals);
            threePoints = (TextView) mView.findViewById(R.id.awayTeamStatisticThreePoint);
            freeThrows = (TextView) mView.findViewById(R.id.awayTeamStatisticFreeThrow);
            fieldGoalsPercentage = (TextView) mView.findViewById(R.id.awayTeamStatisticFieldGoalsPercentage);
            threePointsPercentage = (TextView) mView.findViewById(R.id.awayTeamStatisticThreePointPercentage);
            freeThrowsPercentage = (TextView) mView.findViewById(R.id.awayTeamStatisticFreeThrowPercentage);
            defensiveRebounds = (TextView) mView.findViewById(R.id.awayTeamStatisticDefensiveRebounds);
            offensiveRebounds = (TextView) mView.findViewById(R.id.awayTeamStatisticOffensiveRebounds);
            totalRebounds = (TextView) mView.findViewById(R.id.awayTeamStatisticTotalRebounds);
            assists = (TextView) mView.findViewById(R.id.awayTeamStatisticAssists);
            steals = (TextView) mView.findViewById(R.id.awayTeamStatisticSteals);
            turnovers = (TextView) mView.findViewById(R.id.awayTeamStatisticTurnovers);
            blocks = (TextView) mView.findViewById(R.id.awayTeamStatisticBlocks);
            fouls = (TextView) mView.findViewById(R.id.awayTeamStatisticFouls);
        }

        if (isHomeTeam) {
            teamLogo.setImageUrl(LiveGameViewFragment.data.getGameResult().getHomeTeam().getLogoUrl(), imageLoader);
        } else {
            teamLogo.setImageUrl(LiveGameViewFragment.data.getGameResult().getAwayTeam().getLogoUrl(), imageLoader);
        }

        fieldGoals
                .setText(mView.getResources().getString(R.string.statistic_header_value, team.getFieldGoalsMade(), team
                        .getFieldGoalsAttempted()));
        threePoints.setText(mView.getResources().getString(R.string.statistic_header_value, team.getThreePointersMade(),
                team.getThreePointersAttempted()));
        freeThrows.setText(mView.getResources().getString(R.string.statistic_header_value, team.getFreeThrowsMade(),
                team.getFreeThrowsAttempted()));

        fieldGoalsPercentage.setText(team.getFieldGoalPercentageString());
        threePointsPercentage.setText(team.getThreePointersPercentageString());
        freeThrowsPercentage.setText(team.getFreeThrowPercentageString());

        defensiveRebounds.setText(String.valueOf(team.getDefensiveRebounds()));
        offensiveRebounds.setText(String.valueOf(team.getOffensiveRebounds()));
        totalRebounds.setText(String.valueOf(team.getTotalRebounds()));

        assists.setText(String.valueOf(team.getAssists()));
        steals.setText(String.valueOf(team.getSteals()));
        turnovers.setText(String.valueOf(team.getTurnovers()));
        blocks.setText(String.valueOf(team.getBlocks()));
        fouls.setText(String.valueOf(team.getFouls()));
    }

}
