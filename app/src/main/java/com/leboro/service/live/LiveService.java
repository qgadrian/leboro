package com.leboro.service.live;

import com.leboro.model.game.live.LiveGame;
import com.leboro.util.exception.NoLiveDataException;
import com.leboro.view.listeners.DataLoadedListener;

public interface LiveService {

    void getLiveGameData(long gameId, DataLoadedListener<LiveGame> dataDataLoadedListener) throws NoLiveDataException;

}
