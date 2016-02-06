package com.leboro.model.api.live.game.header;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.leboro.model.api.live.LiveBase;

public class Quarter extends LiveBase {

    private int number;

    private int homeTeamScore;

    private int awayTeamScore;

    public Quarter(int number, int homeTeamScore, int awayTeamScore) {
        this.number = number;
        this.homeTeamScore = homeTeamScore;
        this.awayTeamScore = awayTeamScore;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getHomeTeamScore() {
        return homeTeamScore;
    }

    public void setHomeTeamScore(int homeTeamScore) {
        this.homeTeamScore = homeTeamScore;
    }

    public int getAwayTeamScore() {
        return awayTeamScore;
    }

    public void setAwayTeamScore(int awayTeamScore) {
        this.awayTeamScore = awayTeamScore;
    }

    @JsonCreator
    public static Quarter build(@JsonProperty("n") int number,
            @JsonProperty("scoreA") int homeTeamScore,
            @JsonProperty("scoreB") int awayTeamScore) {
        return new Quarter(number, homeTeamScore, awayTeamScore);
    }

}
