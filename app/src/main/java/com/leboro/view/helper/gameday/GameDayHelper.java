package com.leboro.view.helper.gameday;

import com.leboro.model.game.GameDay;
import com.leboro.model.game.GameDayInfo;

public class GameDayHelper {

    public static int getGameDayCurrentPositionIndex(GameDayInfo gameDayInfo) {
        for (GameDay gameDay : gameDayInfo.getGameDays()) {
            if (gameDayInfo.getCurrentGameDayId().equals(gameDay.getId())) {
                return gameDayInfo.getGameDays().indexOf(gameDay);
            }
        }

        throw new RuntimeException("Unable to find current game day information");
    }
}
