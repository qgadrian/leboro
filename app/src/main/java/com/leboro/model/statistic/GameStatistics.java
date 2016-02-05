package com.leboro.model.statistic;

import java.util.List;

import com.leboro.model.statistic.player.PlayerStatistic;
import com.leboro.model.statistic.team.TeamStatistic;

public class GameStatistics {

    private List<PlayerStatistic> homeTeamPlayerStatistics;

    private List<PlayerStatistic> awayTeamPlayerStatistics;

    private List<TeamStatistic> homeTeamStatistics;

    private List<TeamStatistic> awayTeamStatistics;
}
