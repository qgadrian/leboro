package com.leboro.service.game.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.joda.time.DateTime;

import com.leboro.MainActivity;
import com.leboro.model.game.GameDay;
import com.leboro.model.game.GameInfo;
import com.leboro.service.game.GameService;
import com.leboro.util.cache.ApplicationCacheManager;
import com.leboro.util.exception.InstanceNotFoundException;

import android.util.Log;

public class GameServiceImpl implements GameService {

    @Override
    public DateTime getGameInfoFromCacheByGameId(long gameId) throws InstanceNotFoundException {
        List<GameDay> gameDays = ApplicationCacheManager.getGameDayInfo().getGameDays();
        for (GameDay gameDay : gameDays) {
            if (!CollectionUtils.isEmpty(gameDay.getGames())) {
                for (GameInfo gameInfo : gameDay.getGames()) {
                    if (gameInfo.getGameId() == gameId) {
                        return gameInfo.getGameResult().getStartDate();
                    }
                }
            }
        }

        throw new InstanceNotFoundException((int) gameId);
    }
}
