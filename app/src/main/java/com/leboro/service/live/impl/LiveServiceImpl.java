package com.leboro.service.live.impl;

import com.leboro.MainActivity;
import com.leboro.model.api.live.game.LiveGameInfo;
import com.leboro.model.game.live.LiveGame;
import com.leboro.service.live.LiveService;
import com.leboro.util.Constants;
import com.leboro.util.exception.NoLiveDataException;
import com.leboro.util.http.HttpUtils;
import com.leboro.util.json.JSONUtils;
import com.leboro.util.mapper.api.LiveGameMapper;
import com.leboro.view.listeners.DataLoadedListener;

import cz.msebera.android.httpclient.client.methods.HttpGet;

public class LiveServiceImpl implements LiveService {

    @Override
    public void getLiveGameData(long gameId, DataLoadedListener<LiveGame> dataDataLoadedListener)
            throws NoLiveDataException {
        String url = MainActivity.properties.getProperty(Constants.URL_LIVE_GAME_PROP) + gameId;
        HttpGet request = new HttpGet(url);
        String response = HttpUtils.doAsyncGet(request);
        LiveGameInfo liveGameInfo = JSONUtils.readValue(response, LiveGameInfo.class);
        liveGameInfo.setGameId(gameId);
        dataDataLoadedListener.onDataLoaded(LiveGameMapper.toLiveGame(liveGameInfo));
    }
}
