package com.leboro.service.standing;

import com.leboro.view.listeners.CacheDataLoadedListener;

public interface StandingService {

    void getPlayerStandings(int standingType, CacheDataLoadedListener dataLoadedListener);

    int getPlayerStandingResourceId(int standingType);

}
