package com.leboro.model.game;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class GameInfo implements Comparable<GameInfo>, Parcelable {

    private long gameId;

    private GameResult gameResult;

    public GameInfo(long gameId, GameResult gameResult) {
        this.gameId = gameId;
        this.gameResult = gameResult;
    }

    protected GameInfo(Parcel in) {
        gameId = in.readLong();
        gameResult = in.readParcelable(GameResult.class.getClassLoader());
    }

    public static final Creator<GameInfo> CREATOR = new Creator<GameInfo>() {
        @Override
        public GameInfo createFromParcel(Parcel in) {
            return new GameInfo(in);
        }

        @Override
        public GameInfo[] newArray(int size) {
            return new GameInfo[size];
        }
    };

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public GameResult getGameResult() {
        return gameResult;
    }

    public void setGameResult(GameResult gameResult) {
        this.gameResult = gameResult;
    }

    @Override
    public int compareTo(@NonNull GameInfo another) {
        return this.gameResult.compareTo(another.getGameResult());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(gameId);
        dest.writeParcelable(gameResult, flags);
    }
}
