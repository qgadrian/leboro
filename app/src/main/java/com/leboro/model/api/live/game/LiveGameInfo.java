package com.leboro.model.api.live.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.leboro.model.api.live.LiveBase;
import com.leboro.model.api.live.game.header.Header;
import com.leboro.model.api.live.game.playbyplay.PlayByPlay;
import com.leboro.model.api.live.game.statistic.player.GamePlayerStatisticHolder;
import com.leboro.model.api.live.game.statistic.team.GameTeamStatisticHolder;

public class LiveGameInfo extends LiveBase {

    private long gameId;

    private final Header header;

    private final PlayByPlay playByPlay;

    private final GamePlayerStatisticHolder gamePlayerStatisticHolder;

    private final GameTeamStatisticHolder gameTeamStatisticHolder;

    public LiveGameInfo(Header header, PlayByPlay playByPlay, GamePlayerStatisticHolder gamePlayerStatisticHolder,
            GameTeamStatisticHolder gameTeamStatisticHolder) {
        this.header = header;
        this.playByPlay = playByPlay;
        this.gamePlayerStatisticHolder = gamePlayerStatisticHolder;
        this.gameTeamStatisticHolder = gameTeamStatisticHolder;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public Header getHeader() {
        return header;
    }

    public PlayByPlay getPlayByPlay() {
        return playByPlay;
    }

    public GameTeamStatisticHolder getGameTeamStatisticHolder() {
        return gameTeamStatisticHolder;
    }

    public GamePlayerStatisticHolder getGamePlayerStatisticHolder() {
        return gamePlayerStatisticHolder;
    }

    @JsonCreator
    public static LiveGameInfo build(
            @JsonProperty("HEADER") Header header,
            @JsonProperty("PLAYBYPLAY") PlayByPlay playByPlay,
            @JsonProperty("SCOREBOARD") GamePlayerStatisticHolder gamePlayerStatisticHolder,
            @JsonProperty("TEAMSTATS") GameTeamStatisticHolder gameTeamStatisticHolder
    ) {
        return new LiveGameInfo(header, playByPlay, gamePlayerStatisticHolder, gameTeamStatisticHolder);
    }
}
