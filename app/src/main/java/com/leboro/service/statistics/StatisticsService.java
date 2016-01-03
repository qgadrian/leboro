package com.leboro.service.statistics;

import java.util.List;

import com.leboro.model.classification.Position;
import com.leboro.model.game.GameDayInfo;
import com.leboro.model.game.live.LiveData;
import com.leboro.view.listeners.DataLoadedListener;

public interface StatisticsService {

    List<Position> getClassification();

    void getDefaultGameDayInfo(DataLoadedListener<GameDayInfo> dataLoadedListener);

    void refreshGameInfo(int gameDayId, int kind, int season, DataLoadedListener dataLoadedListener);

    void getLiveData(DataLoadedListener<LiveData> dataDataLoadedListener);

}
