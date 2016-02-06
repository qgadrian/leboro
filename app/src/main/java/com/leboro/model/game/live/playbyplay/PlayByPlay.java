package com.leboro.model.game.live.playbyplay;

import java.util.List;

import com.leboro.model.api.live.LiveBase;
import com.leboro.model.team.Team;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class PlayByPlay extends LiveBase implements Parcelable {

    private final List<PlayLine> lines;

    public PlayByPlay(List<PlayLine> lines) {
        this.lines = lines;
    }

    protected PlayByPlay(Parcel in) {
        lines = in.createTypedArrayList(PlayLine.CREATOR);
    }

    public static final Creator<PlayByPlay> CREATOR = new Creator<PlayByPlay>() {
        @Override
        public PlayByPlay createFromParcel(Parcel in) {
            return new PlayByPlay(in);
        }

        @Override
        public PlayByPlay[] newArray(int size) {
            return new PlayByPlay[size];
        }
    };

    public List<PlayLine> getLines() {
        return lines;
    }

    public static class PlayLine implements Comparable<PlayLine>, Parcelable {

        private final int order;

        private final String text;

        private final Team team;

        private final String time;

        private final int homeScore;

        private final int awayScore;

        private final int quarter;

        public PlayLine(int order, String text, Team team, String time, int homeScore, int awayScore, int quarter) {
            this.order = order;
            this.text = text;
            this.team = team;
            this.time = time;
            this.homeScore = homeScore;
            this.awayScore = awayScore;
            this.quarter = quarter;
        }

        protected PlayLine(Parcel in) {
            order = in.readInt();
            text = in.readString();
            team = in.readParcelable(Team.class.getClassLoader());
            time = in.readString();
            homeScore = in.readInt();
            awayScore = in.readInt();
            quarter = in.readInt();
        }

        public static final Creator<PlayLine> CREATOR = new Creator<PlayLine>() {
            @Override
            public PlayLine createFromParcel(Parcel in) {
                return new PlayLine(in);
            }

            @Override
            public PlayLine[] newArray(int size) {
                return new PlayLine[size];
            }
        };

        public int getOrder() {
            return order;
        }

        public String getText() {
            return text;
        }

        public Team getTeam() {
            return team;
        }

        public String getTime() {
            return time;
        }

        public int getHomeScore() {
            return homeScore;
        }

        public int getAwayScore() {
            return awayScore;
        }

        public int getQuarter() {
            return quarter;
        }

        @Override
        public int compareTo(@NonNull PlayLine another) {
            return ((Integer) another.order).compareTo(order);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(order);
            dest.writeString(text);
            dest.writeParcelable(team, flags);
            dest.writeString(time);
            dest.writeInt(homeScore);
            dest.writeInt(awayScore);
            dest.writeInt(quarter);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(lines);
    }
}
