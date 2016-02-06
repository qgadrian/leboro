package com.leboro.model.game;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.leboro.model.team.Team;

import android.os.Parcel;
import android.os.Parcelable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GameResult implements Comparable<GameResult>, Parcelable {

    private DateTime startDate;

    private Team homeTeam;

    private Team awayTeam;

    private Integer homeScore;

    private Integer awayScore;

    public GameResult(DateTime startDate, Team homeTeam, Team awayTeam, Integer homeScore, Integer awayScore) {
        this.startDate = startDate;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    protected GameResult(Parcel in) {
        homeTeam = in.readParcelable(Team.class.getClassLoader());
        awayTeam = in.readParcelable(Team.class.getClassLoader());
    }

    public static final Creator<GameResult> CREATOR = new Creator<GameResult>() {
        @Override
        public GameResult createFromParcel(Parcel in) {
            return new GameResult(in);
        }

        @Override
        public GameResult[] newArray(int size) {
            return new GameResult[size];
        }
    };

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public Integer getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(Integer homeScore) {
        this.homeScore = homeScore;
    }

    public Integer getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(Integer awayScore) {
        this.awayScore = awayScore;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GameResult gameResult = (GameResult) o;

        return new EqualsBuilder()
                .append(startDate, gameResult.startDate)
                .append(homeTeam, gameResult.homeTeam)
                .append(awayTeam, gameResult.awayTeam)
                .append(homeScore, gameResult.homeScore)
                .append(awayScore, gameResult.awayScore)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(startDate)
                .append(homeTeam)
                .append(awayTeam)
                .append(homeScore)
                .append(awayScore)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("startDate", startDate)
                .append("homeTeam", homeTeam)
                .append("awayTeam", awayTeam)
                .append("homeScore", homeScore)
                .append("awayScore", awayScore)
                .toString();
    }

    @Override
    public int compareTo(GameResult another) {
        return this.startDate.compareTo(another.getStartDate());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(homeTeam, flags);
        dest.writeParcelable(awayTeam, flags);
    }
}
