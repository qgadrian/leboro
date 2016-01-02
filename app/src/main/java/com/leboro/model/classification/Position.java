package com.leboro.model.classification;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.google.common.base.Objects;
import com.leboro.model.team.Team;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class Position implements Comparable<Position>, Parcelable {

    private int position;

    private Team team;

    private int gamesWon;

    private int gamesLost;

    private int totalScored;

    private int opponentTotalScored;

    private int classificationPoints;

    private int strike;

    public Position() {
    }

    public Position(int position, Team team, int gamesWon, int gamesLost, int totalScored, int
            opponentTotalScored, int classificationPoints, int strike) {
        this.position = position;
        this.team = team;
        this.gamesWon = gamesWon;
        this.gamesLost = gamesLost;
        this.totalScored = totalScored;
        this.opponentTotalScored = opponentTotalScored;
        this.classificationPoints = classificationPoints;
        this.strike = strike;
    }

    protected Position(Parcel in) {
        position = in.readInt();
        gamesWon = in.readInt();
        gamesLost = in.readInt();
        totalScored = in.readInt();
        opponentTotalScored = in.readInt();
        classificationPoints = in.readInt();
        strike = in.readInt();
    }

    public int getPosition() {
        return position;
    }

    public Team getTeam() {
        return team;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public int getGamesLost() {
        return gamesLost;
    }

    public int getTotalScored() {
        return totalScored;
    }

    public int getOpponentTotalScored() {
        return opponentTotalScored;
    }

    public int getClassificationPoints() {
        return classificationPoints;
    }

    public int getStrike() {
        return strike;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    public void setGamesLost(int gamesLost) {
        this.gamesLost = gamesLost;
    }

    public void setTotalScored(int totalScored) {
        this.totalScored = totalScored;
    }

    public void setOpponentTotalScored(int opponentTotalScored) {
        this.opponentTotalScored = opponentTotalScored;
    }

    public void setClassificationPoints(int classificationPoints) {
        this.classificationPoints = classificationPoints;
    }

    public void setStrike(int strike) {
        this.strike = strike;
    }

    public int totalGamesPlayed() {
        return gamesWon + gamesLost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Position position1 = (Position) o;

        return new EqualsBuilder()
                .append(position, position1.position)
                .append(gamesWon, position1.gamesWon)
                .append(gamesLost, position1.gamesLost)
                .append(totalScored, position1.totalScored)
                .append(opponentTotalScored, position1.opponentTotalScored)
                .append(classificationPoints, position1.classificationPoints)
                .append(strike, position1.strike)
                .append(team, position1.team)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(position)
                .append(team)
                .append(gamesWon)
                .append(gamesLost)
                .append(totalScored)
                .append(opponentTotalScored)
                .append(classificationPoints)
                .append(strike)
                .toHashCode();
    }

    @Override
    public int compareTo(@NonNull Position another) {
        return Integer.valueOf(this.position).compareTo(another.getPosition());
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("position", position)
                .add("team", team)
                .add("gamesWon", gamesWon)
                .add("gamesLost", gamesLost)
                .add("totalScored", totalScored)
                .add("opponentTotalScored", opponentTotalScored)
                .add("classificationPoints", classificationPoints)
                .add("strike", strike)
                .toString();
    }

    public static final Creator<Position> CREATOR = new Creator<Position>() {
        @Override
        public Position createFromParcel(Parcel in) {
            return new Position(in);
        }

        @Override
        public Position[] newArray(int size) {
            return new Position[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(position);
        dest.writeInt(gamesWon);
        dest.writeInt(gamesLost);
        dest.writeInt(totalScored);
        dest.writeInt(opponentTotalScored);
        dest.writeInt(classificationPoints);
        dest.writeInt(strike);
    }
}
