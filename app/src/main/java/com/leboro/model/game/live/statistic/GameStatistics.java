package com.leboro.model.game.live.statistic;

import java.util.List;

import com.leboro.model.game.live.statistic.player.PlayerStatistic;
import com.leboro.model.game.live.statistic.team.TeamStatistic;

public class GameStatistics {

    private final List<PlayerStatistic> homeTeamPlayerStatistics;

    private final List<PlayerStatistic> awayTeamPlayerStatistics;

    private final TeamStatistic homeTeamStatistics;

    private final TeamStatistic awayTeamStatistics;

    public GameStatistics(TeamStatistic homeTeamStatistics,
            TeamStatistic awayTeamStatistics,
            List<PlayerStatistic> homeTeamPlayerStatistics,
            List<PlayerStatistic> awayTeamPlayerStatistics) {
        this.awayTeamStatistics = awayTeamStatistics;
        this.homeTeamStatistics = homeTeamStatistics;
        this.awayTeamPlayerStatistics = awayTeamPlayerStatistics;
        this.homeTeamPlayerStatistics = homeTeamPlayerStatistics;
    }

    public List<PlayerStatistic> getHomeTeamPlayerStatistics() {
        return homeTeamPlayerStatistics;
    }

    public List<PlayerStatistic> getAwayTeamPlayerStatistics() {
        return awayTeamPlayerStatistics;
    }

    public TeamStatistic getHomeTeamStatistics() {
        return homeTeamStatistics;
    }

    public TeamStatistic getAwayTeamStatistics() {
        return awayTeamStatistics;
    }
}
