package com.leboro.model.api.live.game.statistic;

import com.leboro.model.api.live.LiveBase;

public abstract class BaseStatistic extends LiveBase {

    private final int fieldGoalsMade;

    private final int fieldGoalsAttempted;

    private final int threePointersMade;

    private final int threePointersAttempted;

    private final int freeThrowsMade;

    private final int freeThrowsAttempted;

    private final int offensiveRebounds;

    private final int defensiveRebounds;

    private final int turnovers;

    private final int steals;

    private final int assists;

    private final int fouls;

    private final int points;

    public BaseStatistic(int points, int fouls, int assists, int steals, int turnovers, int defensiveRebounds,
            int offensiveRebounds, int freeThrowsAttempted, int freeThrowsMade, int threePointersAttempted,
            int threePointersMade, int fieldGoalsAttempted, int fieldGoalsMade) {
        this.points = points;
        this.fouls = fouls;
        this.assists = assists;
        this.steals = steals;
        this.turnovers = turnovers;
        this.defensiveRebounds = defensiveRebounds;
        this.offensiveRebounds = offensiveRebounds;
        this.freeThrowsAttempted = freeThrowsAttempted;
        this.freeThrowsMade = freeThrowsMade;
        this.threePointersAttempted = threePointersAttempted;
        this.threePointersMade = threePointersMade;
        this.fieldGoalsAttempted = fieldGoalsAttempted;
        this.fieldGoalsMade = fieldGoalsMade;
    }

    public int getFieldGoalsMade() {
        return fieldGoalsMade;
    }

    public int getFieldGoalsAttempted() {
        return fieldGoalsAttempted;
    }

    public double getFieldGoalPercentage() {
        return fieldGoalsAttempted / fieldGoalsMade;
    }

    public int getThreePointersMade() {
        return threePointersMade;
    }

    public int getThreePointersAttempted() {
        return threePointersAttempted;
    }

    public double getThreePointersPercentage() {
        return threePointersAttempted / threePointersMade;
    }

    public int getFreeThrowsMade() {
        return freeThrowsMade;
    }

    public int getFreeThrowsAttempted() {
        return freeThrowsAttempted;
    }

    public int getOffensiveRebounds() {
        return offensiveRebounds;
    }

    public int getDefensiveRebounds() {
        return defensiveRebounds;
    }

    public int getTotalRebounds() {
        return defensiveRebounds + offensiveRebounds;
    }

    public int getTurnovers() {
        return turnovers;
    }

    public int getSteals() {
        return steals;
    }

    public int getAssists() {
        return assists;
    }

    public int getFouls() {
        return fouls;
    }

    public int getPoints() {
        return points;
    }

    //    @JsonCreator
    //    public static BaseStatistic build(
    //            @JsonProperty("fgm") int fieldGoalsMade,
    //            @JsonProperty("fga") int fieldGoalsAttempted,
    //            @JsonProperty("p3m") int threePointersMade,
    //            @JsonProperty("p3a") int threePointersAttempted,
    //            @JsonProperty("p1m") int freeThrowsMade,
    //            @JsonProperty("p1a") int freeThrowsAttempted,
    //            @JsonProperty("ro") int offensiveRebounds,
    //            @JsonProperty("rd") int defensiveRebounds,
    //            @JsonProperty("to") int turnovers,
    //            @JsonProperty("st") int steals,
    //            @JsonProperty("assist") int assists,
    //            @JsonProperty("pf") int fouls,
    //            @JsonProperty("pts") int points
    //    ) {
    //        return new BaseStatistic(points, fouls, assists, steals, turnovers, defensiveRebounds, offensiveRebounds,
    //                freeThrowsAttempted, freeThrowsMade, threePointersAttempted, threePointersMade, fieldGoalsAttempted,
    //                fieldGoalsMade);
    //    }
}
