package com.leboro.model.api.live.overview;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.leboro.model.api.live.LiveBase;

public class LiveData extends LiveBase {

    private final OverView overView;

    public LiveData(OverView overView) {
        this.overView = overView;
    }

    public OverView getOverview() {
        return overView;
    }

    @JsonCreator
    public static LiveData build(@JsonProperty("OVERVIEW") OverView data) {
        return new LiveData(data);
    }

    public static class OverView extends LiveBase {

        private final List<Competition> competitions;

        public OverView(List<Competition> competitions) {
            this.competitions = competitions;
        }

        public List<Competition> getCompetitions() {
            return competitions;
        }

        @JsonCreator
        public static OverView build(@JsonProperty("COMPETITIONS") List<Competition> competitions) {
            return new OverView(competitions);
        }
    }

    public static class Competition extends LiveBase {

        private final int id;

        private final List<LiveGameOverview> overviews;

        public Competition(int id, List<LiveGameOverview> overviews) {
            this.id = id;
            this.overviews = overviews;
        }

        public int getId() {
            return id;
        }

        public List<LiveGameOverview> getOverviews() {
            return overviews;
        }

        @JsonCreator
        public static Competition build(@JsonProperty("CompID") int id,
                @JsonProperty("GAMES") List<LiveGameOverview> overviews) {
            return new Competition(id, overviews);
        }
    }

}