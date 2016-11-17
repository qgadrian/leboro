package com.leboro.model.api.live.game.statistic.team;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.leboro.model.api.live.game.statistic.BaseStatistic;

public class TeamStatistic extends BaseStatistic {

    public TeamStatistic(int points, int fouls, int assists, int steals, int turnovers, int defensiveRebounds,
            int offensiveRebounds, int freeThrowsAttempted, int freeThrowsMade, int threePointersAttempted,
            int threePointersMade, int fieldGoalsAttempted, int fieldGoalsMade, int blocks) {
        super(points, fouls, assists, steals, turnovers, defensiveRebounds, offensiveRebounds, freeThrowsAttempted,
                freeThrowsMade, threePointersAttempted, threePointersMade, fieldGoalsAttempted, fieldGoalsMade, blocks);
    }

    @JsonCreator
    public static TeamStatistic build(
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
            @JsonProperty("fouls") int fouls,
            @JsonProperty("pts") int points,
            @JsonProperty("bs") int blocks
    ) {
        return new TeamStatistic(points, fouls, assists, steals, turnovers, defensiveRebounds, offensiveRebounds,
                freeThrowsAttempted, freeThrowsMade, threePointersAttempted, threePointersMade, fieldGoalsAttempted,
                fieldGoalsMade, blocks);
    }
}
