package com.leboro.model.api.live.game.header;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.leboro.model.api.live.LiveBase;

public class Header extends LiveBase {

    private final List<Team> teams;

    private final List<Quarter> quarters;

    private final int quarter;

    private final String currentGameTime;

    private final String gameInfoStartDateAndTime;

    public Header(List<Team> teams, List<Quarter> quarters, int quarter, String currentGameTime,
            String gameInfoStartDateAndTime) {
        this.teams = teams;
        this.quarters = quarters;
        this.quarter = quarter;
        this.currentGameTime = currentGameTime;
        this.gameInfoStartDateAndTime = gameInfoStartDateAndTime;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public List<Quarter> getQuarters() {
        return quarters;
    }

    public int getQuarter() {
        return quarter;
    }

    public String getCurrentGameTime() {
        return currentGameTime;
    }

    public String getGameInfoStartDateAndTime() {
        return gameInfoStartDateAndTime;
    }

    @JsonCreator
    public static Header build(
            @JsonProperty("TEAM") List<Team> teams,
            @JsonProperty("QUARTERS") QuarterHolder quarterHolder,
            @JsonProperty("quarter") int quarter,
            @JsonProperty("time") String currentGameTime,
            @JsonProperty("competition") String gameInfoStartDateAndTime) {
        return new Header(teams, quarterHolder.getQuarters(), quarter, currentGameTime, gameInfoStartDateAndTime);
    }

    private static class QuarterHolder extends LiveBase {

        private List<Quarter> quarters;

        public QuarterHolder(List<Quarter> quarters) {
            this.quarters = quarters;
        }

        public List<Quarter> getQuarters() {
            return quarters;
        }

        public void setQuarters(List<Quarter> quarters) {
            this.quarters = quarters;
        }

        @JsonCreator
        public static QuarterHolder build(
                @JsonProperty("QUARTERS") List<Quarter> quarters) {
            return new QuarterHolder(quarters);
        }
    }
}
