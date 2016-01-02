package com.leboro.service.statistics.impl;

import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.commons.collections4.CollectionUtils;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.leboro.MainActivity;
import com.leboro.model.classification.Position;
import com.leboro.model.game.GameDay;
import com.leboro.model.game.GameDayInfo;
import com.leboro.model.game.live.LiveData;
import com.leboro.service.statistics.StatisticsService;
import com.leboro.service.task.HttpPostAsyncTask;
import com.leboro.util.Constants;
import com.leboro.util.Parser;
import com.leboro.util.cache.GameDayCacheManager;
import com.leboro.util.http.HttpUtils;
import com.leboro.util.json.JSONUtils;
import com.leboro.util.sharedpreferences.SharedPreferencesHelper;

import android.util.Log;
import cz.msebera.android.httpclient.client.methods.HttpGet;

public class StatisticsServiceImpl implements StatisticsService {

    @Override
    public List<Position> getClassification() {

        String classificationHTML = requestClassificationData();

        Document data = Parser.parseHTMLData(classificationHTML);

        Elements elements = data.getElementsByTag("tbody");
        if (CollectionUtils.isEmpty(elements)) {
            Log.d(MainActivity.DEBUG_APP, "Unable to find data tp parse data for classification");
        } else {
            if (elements.size() > 1) {
                Log.d(MainActivity.DEBUG_APP, "Unexpected number of parsed data for classification from request");
            }
            return Parser.getPositionsInfo(elements.get(0).children());
        }

        return Collections.emptyList();
    }

    @Override
    public GameDayInfo getGameDayInfo() {
        if (!GameDayCacheManager.hasCacheData()) {
            String gameDaysHTML = requestGameDayData();
            Document gameDayInfoData = Parser.parseHTMLAndSaveTokenData(gameDaysHTML);
            GameDayInfo gameDayInfo = Parser.getGameInfoFromData(gameDayInfoData);
            GameDayCacheManager.setGameDayInfo(gameDayInfo);
        }

        return GameDayCacheManager.getGameDayInfo();
    }

    @Override
    public void refreshGameInfo(int gameDayId, int kind, int season) {
        String gameDaysHTML = requestGameDayData(gameDayId, kind, season);
        Document gameDayInfoData = Parser.parseHTMLAndSaveTokenData(gameDaysHTML);
        GameDay gameDay = Parser.getGameDay(gameDayInfoData);
        GameDayCacheManager.updateGameDay(gameDay.getId(), gameDay.getGames());
    }

    @Override
    public LiveData getLiveData() {
        String url = MainActivity.properties.getProperty(Constants.URL_LIVE_GAMES_OVERVIEW_PROP);
        HttpGet request = new HttpGet(url);
        String response = HttpUtils.doAsyncGet(request);
        return JSONUtils.readValue(response, LiveData.class);
    }

    private String requestClassificationData() {
        Properties properties = MainActivity.properties;

        String url = properties.getProperty(Constants.CLASSIFICATION_URL_PROP);
        String acceptHeader = properties.getProperty(Constants.URL_HEADER_ACCEPT_PROP);
        String referrerHeader = properties.getProperty(Constants.URL_HEADER_REFERRER_PROP);
        HttpGet request = new HttpGet(url);
        request.setHeader("Accept", acceptHeader);
        request.setHeader("Referrer", referrerHeader);

        return HttpUtils.doAsyncGet(request);
    }

    private String requestGameDayData() {
        Properties properties = MainActivity.properties;

        String url = properties.getProperty(Constants.GAMES_URL_PROP);
        String acceptHeader = properties.getProperty(Constants.URL_HEADER_ACCEPT_PROP);
        String referrerHeader = properties.getProperty(Constants.URL_HEADER_REFERRER_PROP);
        HttpGet request = new HttpGet(url);
        request.setHeader("Accept", acceptHeader);
        request.setHeader("Referrer", referrerHeader);

        return HttpUtils.doAsyncGet(request);
    }

    private String requestGameDayData(int gameDayId, int kind, int season) {
        Properties properties = MainActivity.properties;

        String url = properties.getProperty(Constants.GAMES_URL_PROP);
        String acceptHeader = properties.getProperty(Constants.URL_HEADER_ACCEPT_PROP);
        String referrerHeader = properties.getProperty(Constants.URL_HEADER_REFERRER_PROP);

        HttpPostAsyncTask httpPostAsyncTask = new HttpPostAsyncTask();
        try {
            String serverEventValidationToken = SharedPreferencesHelper.getDefaultSharedPreferences(MainActivity
                    .context).getString(Constants.EVENT_VALIDATION_TOKEN_SHARED_PROP, "");
            String serverViewState = SharedPreferencesHelper.getDefaultSharedPreferences(MainActivity
                    .context).getString(Constants.VIEW_STATE_TOKEN_SHARED_PROP, "");
            HttpPostAsyncTask.ResultTaskArgument resultTaskArgument = new HttpPostAsyncTask.ResultTaskArgument
                    (gameDayId, kind, season, referrerHeader, acceptHeader, url, serverViewState,
                            serverEventValidationToken);
            return httpPostAsyncTask.execute(resultTaskArgument).get();
        } catch (InterruptedException | ExecutionException e) {
            Log.d(MainActivity.DEBUG_APP, "Error obtaining classification data", e);
        }

        return null;
    }
}
