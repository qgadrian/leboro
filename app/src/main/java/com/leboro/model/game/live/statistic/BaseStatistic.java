package com.leboro.model.game.live.statistic;

import java.text.DecimalFormat;

public abstract class BaseStatistic {

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

    private final int blocks;

    public BaseStatistic(int points, int fouls, int assists, int steals, int turnovers, int defensiveRebounds,
            int offensiveRebounds, int freeThrowsAttempted, int freeThrowsMade, int threePointersAttempted,
            int threePointersMade, int fieldGoalsAttempted, int fieldGoalsMade, int blocks) {
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
        this.blocks = blocks;
    }

    public int getFieldGoalsMade() {
        return fieldGoalsMade;
    }

    public int getFieldGoalsAttempted() {
        return fieldGoalsAttempted;
    }

    public String getFieldGoalPercentageString() {
        if (fieldGoalsMade == 0) {
            return "0%";
        }
        return new DecimalFormat("0").format((fieldGoalsMade * 100) / fieldGoalsAttempted) + "%";
    }

    public int getThreePointersMade() {
        return threePointersMade;
    }

    public int getThreePointersAttempted() {
        return threePointersAttempted;
    }

    public String getThreePointersPercentageString() {
        if (threePointersAttempted == 0) {
            return "0%";
        }
        return new DecimalFormat("0").format((threePointersMade * 100) / threePointersAttempted) + "%";
    }

    public int getFreeThrowsMade() {
        return freeThrowsMade;
    }

    public int getFreeThrowsAttempted() {
        return freeThrowsAttempted;
    }

    public String getFreeThrowPercentageString() {
        if (freeThrowsMade == 0) {
            return "0%";
        }
        return new DecimalFormat("0").format((freeThrowsMade * 100) / freeThrowsAttempted) + "%";
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

    public int getBlocks() {
        return blocks;
    }
    
}
