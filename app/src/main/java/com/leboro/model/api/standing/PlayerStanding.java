package com.leboro.model.api.standing;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import android.support.annotation.NonNull;

public class PlayerStanding implements Comparable<PlayerStanding> {

    private String imageUrl;

    private String playerName;

    private String teamName;

    private double valueAccumulated;

    private int numberOfGames;

    private double valueAveraged;

    public PlayerStanding(String imageUrl, String playerName, String teamName, double valueAccumulated,
            int numberOfGames, double valueAveraged) {
        this.imageUrl = imageUrl;
        this.playerName = playerName;
        this.teamName = teamName;
        this.valueAccumulated = valueAccumulated;
        this.numberOfGames = numberOfGames;
        this.valueAveraged = valueAveraged;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public double getValueAccumulated() {
        return valueAccumulated;
    }

    public void setValueAccumulated(double valueAccumulated) {
        this.valueAccumulated = valueAccumulated;
    }

    public int getNumberOfGames() {
        return numberOfGames;
    }

    public void setNumberOfGames(int numberOfGames) {
        this.numberOfGames = numberOfGames;
    }

    public double getValueAveraged() {
        return valueAveraged;
    }

    public void setValueAveraged(double valueAveraged) {
        this.valueAveraged = valueAveraged;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PlayerStanding that = (PlayerStanding) o;

        return new EqualsBuilder()
                .append(valueAccumulated, that.valueAccumulated)
                .append(numberOfGames, that.numberOfGames)
                .append(valueAveraged, that.valueAveraged)
                .append(imageUrl, that.imageUrl)
                .append(playerName, that.playerName)
                .append(teamName, that.teamName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(imageUrl)
                .append(playerName)
                .append(teamName)
                .append(valueAccumulated)
                .append(numberOfGames)
                .append(valueAveraged)
                .toHashCode();
    }

    @Override
    public int compareTo(@NonNull PlayerStanding another) {
        return CompareToBuilder.reflectionCompare(another.valueAveraged, valueAveraged);
    }
}
