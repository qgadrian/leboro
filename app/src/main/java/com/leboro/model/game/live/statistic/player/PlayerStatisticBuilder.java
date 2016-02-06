package com.leboro.model.game.live.statistic.player;

public class PlayerStatisticBuilder {

    private int points;

    private int fouls;

    private int assists;

    private int steals;

    private int turnovers;

    private int defensiveRebounds;

    private int offensiveRebounds;

    private int freeThrowsAttempted;

    private int freeThrowsMade;

    private int threePointsAttempted;

    private int threePointsMade;

    private int fieldGoalsAttempted;

    private int fieldGoalsMade;

    private String playerName;

    private int playerNumber;

    private String playerImageUrl;

    public PlayerStatisticBuilder setPoints(int points) {
        this.points = points;
        return this;
    }

    public PlayerStatisticBuilder setFouls(int fouls) {
        this.fouls = fouls;
        return this;
    }

    public PlayerStatisticBuilder setAssists(int assists) {
        this.assists = assists;
        return this;
    }

    public PlayerStatisticBuilder setSteals(int steals) {
        this.steals = steals;
        return this;
    }

    public PlayerStatisticBuilder setTurnovers(int turnovers) {
        this.turnovers = turnovers;
        return this;
    }

    public PlayerStatisticBuilder setDefensiveRebounds(int defensiveRebounds) {
        this.defensiveRebounds = defensiveRebounds;
        return this;
    }

    public PlayerStatisticBuilder setOffensiveRebounds(int offensiveRebounds) {
        this.offensiveRebounds = offensiveRebounds;
        return this;
    }

    public PlayerStatisticBuilder setFreeThrowsAttempted(int freeThrowsAttempted) {
        this.freeThrowsAttempted = freeThrowsAttempted;
        return this;
    }

    public PlayerStatisticBuilder setFreeThrowsMade(int freeThrowsMade) {
        this.freeThrowsMade = freeThrowsMade;
        return this;
    }

    public PlayerStatisticBuilder setThreePointsAttempted(int threePointsAttempted) {
        this.threePointsAttempted = threePointsAttempted;
        return this;
    }

    public PlayerStatisticBuilder setThreePointsMade(int threePointsMade) {
        this.threePointsMade = threePointsMade;
        return this;
    }

    public PlayerStatisticBuilder setFieldGoalsAttempted(int fieldGoalsAttempted) {
        this.fieldGoalsAttempted = fieldGoalsAttempted;
        return this;
    }

    public PlayerStatisticBuilder setFieldGoalsMade(int fieldGoalsMade) {
        this.fieldGoalsMade = fieldGoalsMade;
        return this;
    }

    public PlayerStatisticBuilder setPlayerName(String playerName) {
        this.playerName = playerName;
        return this;
    }

    public PlayerStatisticBuilder setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
        return this;
    }

    public PlayerStatisticBuilder setPlayerImageUrl(String playerImageUrl) {
        this.playerImageUrl = playerImageUrl;
        return this;
    }

    public PlayerStatistic createPlayerStatistic() {
        return new PlayerStatistic(points, fouls, assists, steals, turnovers, defensiveRebounds, offensiveRebounds,
                freeThrowsAttempted, freeThrowsMade, threePointsAttempted, threePointsMade, fieldGoalsAttempted,
                fieldGoalsMade, playerName, playerNumber, playerImageUrl);
    }
}