package com.leboro.service.teaminfo;


import com.leboro.util.exception.InstanceNotFoundException;
import com.leboro.view.listeners.CacheDataLoadedListener;

public interface TeamInfoService {

    void getTeamInfos(CacheDataLoadedListener dataLoadedListener);

    void getTeamRosterAndUpdateCache(long teamInfoId, CacheDataLoadedListener dataLoadedListener) throws InstanceNotFoundException;

}
