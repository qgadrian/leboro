package com.leboro.model.statistic.player;

import com.leboro.model.statistic.BaseStatistic;

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
}
