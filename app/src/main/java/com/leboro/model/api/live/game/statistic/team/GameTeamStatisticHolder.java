package com.leboro.model.api.live.game.statistic.team;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.leboro.model.api.live.LiveBase;

public class GameTeamStatisticHolder extends LiveBase {

    private final List<TeamStatistic> gameStatistics;

    public GameTeamStatisticHolder(
            List<TeamStatistic> gameStatistics) {
        this.gameStatistics = gameStatistics;
    }

    public List<TeamStatistic> getGameStatistics() {
        return gameStatistics;
    }

    @JsonCreator
    public static GameTeamStatisticHolder build(
            @JsonProperty("TEAM") List<TeamStatistic> teamStatistics) {
        return new GameTeamStatisticHolder(teamStatistics);
    }

}
