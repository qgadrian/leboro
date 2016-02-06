package com.leboro.model.api.live.game.playbyplay;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.leboro.model.api.live.LiveBase;

public class PlayByPlay extends LiveBase {

    private final List<Line> lines;

    public PlayByPlay(List<Line> lines) {
        this.lines = lines;
    }

    public List<Line> getLines() {
        return lines;
    }

    @JsonCreator
    public static PlayByPlay build(@JsonProperty("LINES") List<Line> lines) {
        return new PlayByPlay(lines);
    }

    public static class Line {

        private final int order;

        private final String text;

        private final int team;

        private final String time;

        private final Boolean deleted;

        private final int homeScore;

        private final int awayScore;

        private final int quarter;

        public Line(int order, String text, int team, String time, Boolean deleted, int homeScore, int awayScore,
                int quarter) {
            this.order = order;
            this.text = text;
            this.team = team;
            this.time = time;
            this.deleted = deleted;
            this.homeScore = homeScore;
            this.awayScore = awayScore;
            this.quarter = quarter;
        }

        public int getOrder() {
            return order;
        }

        public String getText() {
            return text;
        }

        public int getTeam() {
            return team;
        }

        public String getTime() {
            return time;
        }

        public Boolean getDeleted() {
            return deleted;
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

        @JsonCreator
        public static Line build(
                @JsonProperty("num") int order,
                @JsonProperty("text") String text,
                @JsonProperty("team") int team,
                @JsonProperty("time") String time,
                @JsonProperty("deleted") String deleted,
                @JsonProperty("scoreA") int homeScore,
                @JsonProperty("scoreB") int awayScore,
                @JsonProperty("quarter") int quarter) {
            return new Line(order, text, team, time, "1".equals(deleted), homeScore, awayScore, quarter);
        }
    }
}
