package com.leboro.model.game.live;

import com.leboro.model.game.GameResult;
import com.leboro.model.game.live.playbyplay.PlayByPlay;
import com.leboro.model.game.live.statistic.GameStatistics;
import com.leboro.model.game.live.status.CurrentGameStatus;

public class LiveGame {

    private final long gameId;

    private final GameResult gameResult;

    private final PlayByPlay playByPlay;

    private final GameStatistics gameStatistics;

    private final CurrentGameStatus currentGameStatus;

    public LiveGame(long gameId, GameResult gameResult, PlayByPlay playByPlay, GameStatistics gameStatistics,
            CurrentGameStatus currentGameStatus) {
        this.gameId = gameId;
        this.playByPlay = playByPlay;
        this.gameResult = gameResult;
        this.gameStatistics = gameStatistics;
        this.currentGameStatus = currentGameStatus;
    }

    public GameResult getGameResult() {
        return gameResult;
    }

    public PlayByPlay getPlayByPlay() {
        return playByPlay;
    }

    public GameStatistics getGameStatistics() {
        return gameStatistics;
    }

    public long getGameId() {
        return gameId;
    }

    public CurrentGameStatus getCurrentGameStatus() {
        return currentGameStatus;
    }
}
