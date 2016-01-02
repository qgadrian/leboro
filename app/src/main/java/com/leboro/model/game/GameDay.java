package com.leboro.model.game;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import android.os.Parcel;
import android.os.Parcelable;

public class GameDay implements Parcelable {

    private List<GameResult> games;

    private final int id;

    public GameDay(List<GameResult> games, int id) {
        this.games = games;
        this.id = id;
    }

    protected GameDay(Parcel in) {
        games = in.createTypedArrayList(GameResult.CREATOR);
        id = in.readInt();
    }

    public static final Creator<GameDay> CREATOR = new Creator<GameDay>() {
        @Override
        public GameDay createFromParcel(Parcel in) {
            return new GameDay(in);
        }

        @Override
        public GameDay[] newArray(int size) {
            return new GameDay[size];
        }
    };

    public List<GameResult> getGames() {
        return games;
    }

    public int getId() {
        return id;
    }

    public void setGames(List<GameResult> games) {
        this.games = games;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GameDay gameDay = (GameDay) o;

        return new EqualsBuilder()
                .append(id, gameDay.id)
                .append(games, gameDay.games)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(games)
                .append(id)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "GameDay{" +
                "games=" + games +
                ", id=" + id +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(games);
        dest.writeInt(id);
    }
}
