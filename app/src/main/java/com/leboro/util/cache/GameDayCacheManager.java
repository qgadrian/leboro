package com.leboro.util.cache;

import java.util.List;

import com.leboro.model.game.GameDay;
import com.leboro.model.game.GameDayInfo;
import com.leboro.model.game.GameResult;
import com.leboro.util.exception.InstanceNotFoundException;

public class GameDayCacheManager {

    private static GameDayInfo gameDayInfo;

    public static boolean hasCacheData() {
        return gameDayInfo != null;
    }

    // todo: bad practice
    public static GameDayInfo getGameDayInfo() {
        return gameDayInfo;
    }

    // Avoid overriding game day info by forcing manually clear the data
    public static synchronized void setGameDayInfo(GameDayInfo newGameDayInfo) {
        if (gameDayInfo == null) {
            gameDayInfo = newGameDayInfo;
        } else {
            throw new IllegalStateException("Already assigned game day info, you have to clear the data first.");
        }
    }

    public static synchronized void clearGameDayInfo() {
        gameDayInfo = null;
    }

    public static synchronized void updateGameDay(int gameDayId, List<GameResult> gameResults) {
        for (GameDay gameDay : gameDayInfo.getGameDays()) {
            if (gameDay.getId() == gameDayId) {
                gameDay.setGames(gameResults);
            }
        }
    }

    public static synchronized GameDay getGameDay(int id) throws InstanceNotFoundException {
        for (GameDay gameDay : gameDayInfo.getGameDays()) {
            if (gameDay.getId() == id) {
                return gameDay;
            }
        }

        throw new InstanceNotFoundException(id, "No game day found for id[" + id + "]");
    }
}
