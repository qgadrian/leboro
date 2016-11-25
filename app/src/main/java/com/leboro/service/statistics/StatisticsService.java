package com.leboro.service.statistics;

import com.leboro.model.api.live.overview.LiveData;
import com.leboro.model.classification.Position;
import com.leboro.model.game.GameDayInfo;
import com.leboro.view.listeners.CacheDataLoadedListener;
import com.leboro.view.listeners.DataLoadedListener;

public interface StatisticsService {

    void getClassification(CacheDataLoadedListener dataLoadedListener);

    void getDefaultGameDayInfo(CacheDataLoadedListener dataLoadedListener);

    void getGameDayGames(int gameDayId, int kind, int season, CacheDataLoadedListener dataLoadedListener);

    void getLiveData(DataLoadedListener<LiveData> dataDataLoadedListener);

}
