package com.leboro.util.cache;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.leboro.model.api.standing.PlayerStanding;
import com.leboro.model.classification.Position;
import com.leboro.model.game.GameDay;
import com.leboro.model.game.GameDayInfo;
import com.leboro.model.game.GameInfo;
import com.leboro.model.news.News;
import com.leboro.util.exception.InstanceNotFoundException;

public class ApplicationCacheManager {

    private static GameDayInfo gameDayInfo;

    private static List<Position> positions;

    private static List<News> news;

    private static Map<Integer, List<PlayerStanding>> playerStandingsByStandingType;

    public static boolean hasGameDayCacheData() {
        return gameDayInfo != null;
    }

    public static boolean hasNewsData() {
        return news != null;
    }

    public static boolean hasClassificationCacheData() {
        return positions != null;
    }

    // Avoid overriding game day info by forcing manually clear the data
    public static synchronized void setClassification(List<Position> newPositions) {
        if (positions == null) {
            positions = newPositions;
        } else {
            throw new IllegalStateException("Already assigned classification info, you have to clear the data first.");
        }
    }

    public static synchronized void setNews(List<News> newNews) {
        if (news == null) {
            news = newNews;
        } else {
            throw new IllegalStateException("Already assigned news info, you have to clear the data first.");
        }
    }

    public static synchronized void clearClassificationInfo() {
        positions = null;
    }

    public static synchronized void clearNewsInfo() {
        news = null;
    }

    // todo: bad practice
    public static List<Position> getClassification() {
        return positions;
    }

    public static List<News> getNews() {
        return news;
    }

    public static GameDayInfo getGameDayInfo() throws InstanceNotFoundException {
        if (gameDayInfo == null) {
            throw new InstanceNotFoundException(0, "No game day info available.");
        } else {
            return gameDayInfo;
        }
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

    public static synchronized void updateGameDay(int gameDayId, List<GameInfo> gameInfos) {
        for (GameDay gameDay : gameDayInfo.getGameDays()) {
            if (gameDay.getId() == gameDayId) {
                gameDay.setGames(gameInfos);
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

    public static synchronized void clearPlayerStandings() {
        playerStandingsByStandingType.clear();
    }

    public static synchronized void updatePlayerStandings(int standingType, List<PlayerStanding> playerStandings) {
        if (playerStandingsByStandingType == null) {
            playerStandingsByStandingType = Maps.newHashMap();
        }
        playerStandingsByStandingType.put(standingType, playerStandings);
    }

    public static synchronized List<PlayerStanding> getPlayerStandings(int standingType) {
        if (playerStandingsByStandingType == null) {
            playerStandingsByStandingType = Maps.newHashMap();
        }
        List<PlayerStanding> playerStandings = playerStandingsByStandingType.get(standingType);
        return playerStandings == null ? Lists.<PlayerStanding>newArrayList() : playerStandings;
    }

}
