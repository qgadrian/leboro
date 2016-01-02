package com.leboro.model.game;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import android.os.Parcel;
import android.os.Parcelable;

public class GameDayInfo implements Parcelable {

    private int season;

    private int kind;

    private Integer currentGameDayId;

    private List<GameDay> gameDays;

    public GameDayInfo(List<GameDay> gameDays, Integer currentGameDayId, int kind,
            int season) {
        this.gameDays = gameDays;
        this.currentGameDayId = currentGameDayId;
        this.kind = kind;
        this.season = season;
    }

    protected GameDayInfo(Parcel in) {
        season = in.readInt();
        kind = in.readInt();
    }

    public static final Creator<GameDayInfo> CREATOR = new Creator<GameDayInfo>() {
        @Override
        public GameDayInfo createFromParcel(Parcel in) {
            return new GameDayInfo(in);
        }

        @Override
        public GameDayInfo[] newArray(int size) {
            return new GameDayInfo[size];
        }
    };

    public int getSeason() {
        return season;
    }

    public int getKind() {
        return kind;
    }

    public List<GameDay> getGameDays() {
        return gameDays;
    }

    public Integer getCurrentGameDayId() {
        return currentGameDayId;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public void setCurrentGameDayId(Integer currentGameDayId) {
        this.currentGameDayId = currentGameDayId;
    }

    public void setGameDays(List<GameDay> gameDays) {
        this.gameDays = gameDays;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GameDayInfo that = (GameDayInfo) o;

        return new EqualsBuilder()
                .append(season, that.season)
                .append(kind, that.kind)
                .append(currentGameDayId, that.currentGameDayId)
                .append(gameDays, that.gameDays)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(season)
                .append(kind)
                .append(currentGameDayId)
                .append(gameDays)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "GameDayInfo{" +
                "season=" + season +
                ", kind=" + kind +
                ", currentGameDayId=" + currentGameDayId +
                ", gameDays=" + gameDays +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(season);
        dest.writeInt(kind);
        dest.writeTypedList(gameDays);
    }
}
