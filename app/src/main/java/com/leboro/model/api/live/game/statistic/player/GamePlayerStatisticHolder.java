package com.leboro.model.api.live.game.statistic.player;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.leboro.model.api.live.LiveBase;

public class GamePlayerStatisticHolder extends LiveBase {

    private final List<GamePlayerStatistics> gameStatistics;

    public GamePlayerStatisticHolder(
            List<GamePlayerStatistics> gameStatistics) {
        this.gameStatistics = gameStatistics;
    }

    public List<GamePlayerStatistics> getGameStatistics() {
        return gameStatistics;
    }

    @JsonCreator
    public static GamePlayerStatisticHolder build(
            @JsonProperty("TEAM") List<GamePlayerStatistics> gameStatistics) {
        return new GamePlayerStatisticHolder(gameStatistics);
    }

    public static class GamePlayerStatistics extends LiveBase {

        private final List<PlayerStatistic> teamPlayersStatistics;

        public GamePlayerStatistics(
                List<PlayerStatistic> teamPlayersStatistics) {
            this.teamPlayersStatistics = teamPlayersStatistics;
        }

        public List<PlayerStatistic> getTeamPlayersStatistics() {
            return teamPlayersStatistics;
        }

        @JsonCreator
        public static GamePlayerStatistics build(
                @JsonProperty("PLAYER") List<PlayerStatistic> teamPlayersStatistics) {
            return new GamePlayerStatistics(teamPlayersStatistics);
        }
    }

}


