package com.leboro.model.game.live;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.leboro.model.game.GameResult;
import com.leboro.model.team.Team;

public class LiveOverview extends GameResult {

    private final int homeTeamId;

    private final int awayTeamId;

    private final int quarter;

    public LiveOverview(String homeTeamName, int homeTeamId, int homeTeamScore, String homeTeamLogoUrl,
            String awayTeamName, int awayTeamId, int awayTeamScore, String awayTeamLogoUrl, int quarter,
            DateTime startDate) {
        super(startDate, new Team(homeTeamName, homeTeamLogoUrl),
                new Team(awayTeamName,
                        awayTeamLogoUrl), homeTeamScore, awayTeamScore);
        this.homeTeamId = homeTeamId;
        this.awayTeamId = awayTeamId;
        this.quarter = quarter;
    }

    public int getHomeTeamId() {
        return homeTeamId;
    }

    public int getAwayTeamId() {
        return awayTeamId;
    }

    public int getQuarter() {
        return quarter;
    }

    @JsonCreator
    public static LiveOverview build(@JsonProperty("TeamA") String homeTeam,
            @JsonProperty("TeamAID") int homeTeamId,
            @JsonProperty("ScoreA") int homeTeamScore,
            @JsonProperty("LogoA") String homeTeamLogoUrl,
            @JsonProperty("TeamB") String awayTeamName,
            @JsonProperty("TeamBID") int awayTeamId,
            @JsonProperty("ScoreB") int awayTeamScore,
            @JsonProperty("LogoB") String awayTeamLogoUrl,
            @JsonProperty("Quarter") int quarter,
            @JsonProperty("StartTime") @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss:SS") DateTime startDate) {
        return new LiveOverview(homeTeam, homeTeamId, homeTeamScore, homeTeamLogoUrl, awayTeamName, awayTeamId,
                awayTeamScore, awayTeamLogoUrl, quarter, startDate);
    }
}
