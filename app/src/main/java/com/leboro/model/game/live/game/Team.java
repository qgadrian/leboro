package com.leboro.model.game.live.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Team {

    private int score;

    private String name;

    private String logoUrl;

    public Team(int score, String name, String logoUrl) {
        this.score = score;
        this.name = name;
        this.logoUrl = logoUrl;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    @JsonCreator
    public static Team build(@JsonProperty("pts") int score,
            @JsonProperty("name") String name,
            @JsonProperty("logo") String logo) {
        return new Team(score, name, logo);
    }
}
