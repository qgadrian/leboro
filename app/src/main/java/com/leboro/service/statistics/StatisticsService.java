package com.leboro.service.statistics;

import com.leboro.model.classification.Position;
import com.leboro.model.game.GameDayInfo;
import com.leboro.model.game.live.overview.LiveData;
import com.leboro.view.listeners.DataLoadedListener;

public interface StatisticsService {

    void getClassification(DataLoadedListener<Position> dataLoadedListener);

    void getDefaultGameDayInfo(DataLoadedListener<GameDayInfo> dataLoadedListener);

    void refreshGameInfo(int gameDayId, int kind, int season, DataLoadedListener dataLoadedListener);

    void getLiveData(DataLoadedListener<LiveData> dataDataLoadedListener);

}
