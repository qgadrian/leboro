package com.leboro.view.helper.gameday;

import com.leboro.model.game.GameDay;
import com.leboro.model.game.GameDayInfo;
import com.leboro.model.game.GameResult;

public class GameDayHelper {

    public static int getGameDayCurrentPositionIndex(GameDayInfo gameDayInfo) {
        for (GameDay gameDay : gameDayInfo.getGameDays()) {
            if (gameDayInfo.getCurrentGameDayId().equals(gameDay.getId())) {
                return gameDayInfo.getGameDays().indexOf(gameDay);
            }
        }

        throw new RuntimeException("Unable to find current game day information");
    }

    public static boolean isStarted(GameResult gameResult) {
        return gameResult.getStartDate().isBeforeNow() && gameResult.getHomeScore() != 0
                && gameResult.getAwayScore() != 0;
    }

}
