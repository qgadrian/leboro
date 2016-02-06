package com.leboro.model.game.live.statistic;

public class BaseStatisticBuilder {

    private int points;

    private int fouls;

    private int assists;

    private int steals;

    private int turnovers;

    private int defensiveRebounds;

    private int offensiveRebounds;

    private int freeThrowsAttempted;

    private int freeThrowsMade;

    private int threePointersAttempted;

    private int threePointersMade;

    private int fieldGoalsAttempted;

    private int fieldGoalsMade;

    public BaseStatisticBuilder setPoints(int points) {
        this.points = points;
        return this;
    }

    public BaseStatisticBuilder setFouls(int fouls) {
        this.fouls = fouls;
        return this;
    }

    public BaseStatisticBuilder setAssists(int assists) {
        this.assists = assists;
        return this;
    }

    public BaseStatisticBuilder setSteals(int steals) {
        this.steals = steals;
        return this;
    }

    public BaseStatisticBuilder setTurnovers(int turnovers) {
        this.turnovers = turnovers;
        return this;
    }

    public BaseStatisticBuilder setDefensiveRebounds(int defensiveRebounds) {
        this.defensiveRebounds = defensiveRebounds;
        return this;
    }

    public BaseStatisticBuilder setOffensiveRebounds(int offensiveRebounds) {
        this.offensiveRebounds = offensiveRebounds;
        return this;
    }

    public BaseStatisticBuilder setFreeThrowsAttempted(int freeThrowsAttempted) {
        this.freeThrowsAttempted = freeThrowsAttempted;
        return this;
    }

    public BaseStatisticBuilder setFreeThrowsMade(int freeThrowsMade) {
        this.freeThrowsMade = freeThrowsMade;
        return this;
    }

    public BaseStatisticBuilder setThreePointersAttempted(int threePointersAttempted) {
        this.threePointersAttempted = threePointersAttempted;
        return this;
    }

    public BaseStatisticBuilder setThreePointersMade(int threePointersMade) {
        this.threePointersMade = threePointersMade;
        return this;
    }

    public BaseStatisticBuilder setFieldGoalsAttempted(int fieldGoalsAttempted) {
        this.fieldGoalsAttempted = fieldGoalsAttempted;
        return this;
    }

    public BaseStatisticBuilder setFieldGoalsMade(int fieldGoalsMade) {
        this.fieldGoalsMade = fieldGoalsMade;
        return this;
    }

}