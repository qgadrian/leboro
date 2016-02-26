package com.leboro.model.game.live.statistic.team;

import com.leboro.model.game.live.statistic.BaseStatistic;

public class TeamStatistic extends BaseStatistic {

    public TeamStatistic(int points, int fouls, int assists, int steals, int turnovers, int defensiveRebounds,
            int offensiveRebounds, int freeThrowsAttempted, int freeThrowsMade, int threePointersAttempted,
            int threePointersMade, int fieldGoalsAttempted, int fieldGoalsMade, int blocks) {
        super(points, fouls, assists, steals, turnovers, defensiveRebounds, offensiveRebounds, freeThrowsAttempted,
                freeThrowsMade, threePointersAttempted, threePointersMade, fieldGoalsAttempted, fieldGoalsMade, blocks);
    }
}
