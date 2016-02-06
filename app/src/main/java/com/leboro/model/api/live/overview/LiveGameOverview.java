package com.leboro.model.api.live.overview;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.leboro.model.game.GameResult;
import com.leboro.model.team.Team;

public class LiveGameOverview extends GameResult {

    private final long gameId;

    private final int homeTeamId;

    private final int awayTeamId;

    private final int currentQuarter;

    private final String timeLeft;

    public LiveGameOverview(long gameId, String homeTeamName, int homeTeamId, int homeTeamScore, String homeTeamLogoUrl,
            String awayTeamName, int awayTeamId, int awayTeamScore, String awayTeamLogoUrl, int currentQuarter,
            DateTime startDate, String timeLeft) {
        super(startDate, new Team(homeTeamName, homeTeamLogoUrl),
                new Team(awayTeamName,
                        awayTeamLogoUrl), homeTeamScore, awayTeamScore);
        this.gameId = gameId;
        this.homeTeamId = homeTeamId;
        this.awayTeamId = awayTeamId;
        this.currentQuarter = currentQuarter;
        this.timeLeft = timeLeft;
    }

    public int getHomeTeamId() {
        return homeTeamId;
    }

    public int getAwayTeamId() {
        return awayTeamId;
    }

    public int getCurrentQuarter() {
        return currentQuarter;
    }

    public String getTimeLeft() {
        return timeLeft;
    }

    public long getGameId() {
        return gameId;
    }

    @JsonCreator
    public static LiveGameOverview build(
            @JsonProperty("ID") long gameId,
            @JsonProperty("TeamA") String homeTeam,
            @JsonProperty("TeamAID") int homeTeamId,
            @JsonProperty("ScoreA") int homeTeamScore,
            @JsonProperty("LogoA") String homeTeamLogoUrl,
            @JsonProperty("TeamB") String awayTeamName,
            @JsonProperty("TeamBID") int awayTeamId,
            @JsonProperty("ScoreB") int awayTeamScore,
            @JsonProperty("LogoB") String awayTeamLogoUrl,
            @JsonProperty("Quarter") int quarter,
            @JsonProperty("Time") String timeLeft,
            @JsonProperty("StartTime") @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") DateTime startDate) {
        return new LiveGameOverview(gameId, homeTeam, homeTeamId, homeTeamScore, homeTeamLogoUrl, awayTeamName,
                awayTeamId,
                awayTeamScore, awayTeamLogoUrl, quarter, startDate, timeLeft);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("gameId", gameId)
                .append("homeTeamId", homeTeamId)
                .append("awayTeamId", awayTeamId)
                .append("currentQuarter", currentQuarter)
                .append("timeLeft", timeLeft)
                .toString();
    }
}
