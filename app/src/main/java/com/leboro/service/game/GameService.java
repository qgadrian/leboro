package com.leboro.service.game;

import org.joda.time.DateTime;

import com.leboro.util.exception.InstanceNotFoundException;

public interface GameService {

    DateTime getGameInfoFromCacheByGameId(long gameId) throws InstanceNotFoundException;

}
