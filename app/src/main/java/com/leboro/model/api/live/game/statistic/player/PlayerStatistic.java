package com.leboro.model.api.live.game.statistic.player;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.leboro.model.api.live.game.statistic.BaseStatistic;

public class PlayerStatistic extends BaseStatistic {

    private String playerName;

    private int playerNumber;

    private String playerImageUrl;

    public PlayerStatistic(int points, int fouls, int assists, int steals, int turnovers, int defensiveRebounds,
            int offensiveRebounds, int freeThrowsAttempted, int freeThrowsMade, int threePointsAttempted,
            int threePointsMade, int fieldGoalsAttempted, int fieldGoalsMade, String playerName, int playerNumber,
            String playerImageUrl) {
        super(points, fouls, assists, steals, turnovers, defensiveRebounds, offensiveRebounds, freeThrowsAttempted,
                freeThrowsMade, threePointsAttempted, threePointsMade, fieldGoalsAttempted, fieldGoalsMade);
        this.playerName = playerName;
        this.playerNumber = playerNumber;
        this.playerImageUrl = playerImageUrl;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public String getPlayerImageUrl() {
        return playerImageUrl;
    }

    public void setPlayerImageUrl(String playerImageUrl) {
        this.playerImageUrl = playerImageUrl;
    }

    @JsonCreator
    public static PlayerStatistic build(
            @JsonProperty("fgm") int fieldGoalsMade,
            @JsonProperty("fga") int fieldGoalsAttempted,
            @JsonProperty("p3m") int threePointersMade,
            @JsonProperty("p3a") int threePointersAttempted,
            @JsonProperty("p1m") int freeThrowsMade,
            @JsonProperty("p1a") int freeThrowsAttempted,
            @JsonProperty("ro") int offensiveRebounds,
            @JsonProperty("rd") int defensiveRebounds,
            @JsonProperty("to") int turnovers,
            @JsonProperty("st") int steals,
            @JsonProperty("assist") int assists,
            @JsonProperty("pf") int fouls,
            @JsonProperty("pts") int points,
            @JsonProperty("name") String playerName,
            @JsonProperty("no") int playerNumber,
            @JsonProperty("logo") String playerImageUrl
    ) {
        return new PlayerStatistic(points, fouls, assists, steals, turnovers, defensiveRebounds, offensiveRebounds,
                freeThrowsAttempted, freeThrowsMade, threePointersAttempted, threePointersMade, fieldGoalsAttempted,
                fieldGoalsMade, playerName, playerNumber, playerImageUrl);
    }
}
