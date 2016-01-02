package com.leboro.service.statistics;

import java.util.List;

import com.leboro.model.classification.Position;
import com.leboro.model.game.GameDayInfo;
import com.leboro.model.game.live.LiveData;

public interface StatisticsService {

    List<Position> getClassification();

    GameDayInfo getGameDayInfo();

    void refreshGameInfo(int gameDayId, int kind, int season);

    LiveData getLiveData();
}
