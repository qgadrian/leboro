package com.leboro.model.game.live.statistic.team;

public class TeamStatisticBuilder {

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

    public TeamStatisticBuilder setPoints(int points) {
        this.points = points;
        return this;
    }

    public TeamStatisticBuilder setFouls(int fouls) {
        this.fouls = fouls;
        return this;
    }

    public TeamStatisticBuilder setAssists(int assists) {
        this.assists = assists;
        return this;
    }

    public TeamStatisticBuilder setSteals(int steals) {
        this.steals = steals;
        return this;
    }

    public TeamStatisticBuilder setTurnovers(int turnovers) {
        this.turnovers = turnovers;
        return this;
    }

    public TeamStatisticBuilder setDefensiveRebounds(int defensiveRebounds) {
        this.defensiveRebounds = defensiveRebounds;
        return this;
    }

    public TeamStatisticBuilder setOffensiveRebounds(int offensiveRebounds) {
        this.offensiveRebounds = offensiveRebounds;
        return this;
    }

    public TeamStatisticBuilder setFreeThrowsAttempted(int freeThrowsAttempted) {
        this.freeThrowsAttempted = freeThrowsAttempted;
        return this;
    }

    public TeamStatisticBuilder setFreeThrowsMade(int freeThrowsMade) {
        this.freeThrowsMade = freeThrowsMade;
        return this;
    }

    public TeamStatisticBuilder setThreePointersAttempted(int threePointersAttempted) {
        this.threePointersAttempted = threePointersAttempted;
        return this;
    }

    public TeamStatisticBuilder setThreePointersMade(int threePointersMade) {
        this.threePointersMade = threePointersMade;
        return this;
    }

    public TeamStatisticBuilder setFieldGoalsAttempted(int fieldGoalsAttempted) {
        this.fieldGoalsAttempted = fieldGoalsAttempted;
        return this;
    }

    public TeamStatisticBuilder setFieldGoalsMade(int fieldGoalsMade) {
        this.fieldGoalsMade = fieldGoalsMade;
        return this;
    }

    public TeamStatistic createTeamStatistic() {
        return new TeamStatistic(points, fouls, assists, steals, turnovers, defensiveRebounds, offensiveRebounds,
                freeThrowsAttempted, freeThrowsMade, threePointersAttempted, threePointersMade, fieldGoalsAttempted,
                fieldGoalsMade);
    }
}