package com.leboro.service.statistics.impl;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.commons.collections4.CollectionUtils;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.leboro.MainActivity;
import com.leboro.model.classification.Position;
import com.leboro.model.game.GameDay;
import com.leboro.model.game.GameDayInfo;
import com.leboro.model.game.live.overview.LiveData;
import com.leboro.service.statistics.StatisticsService;
import com.leboro.service.task.HttpPostAsyncTask;
import com.leboro.util.Constants;
import com.leboro.util.Parser;
import com.leboro.util.cache.ApplicationCacheManager;
import com.leboro.util.http.HttpUtils;
import com.leboro.util.json.JSONUtils;
import com.leboro.util.sharedpreferences.SharedPreferencesHelper;
import com.leboro.view.helper.http.HttpHelper;
import com.leboro.view.listeners.DataLoadedListener;

import android.util.Log;
import cz.msebera.android.httpclient.client.methods.HttpGet;

public class StatisticsServiceImpl implements StatisticsService {

    @Override
    public void getClassification(DataLoadedListener<Position> dataLoadedListener) {
        if (CollectionUtils.isEmpty(ApplicationCacheManager.getClassification())) {

            String classificationHTML = HttpHelper.getHtmlFromSimpleHttpRequestUsingProperties(Constants
                    .CLASSIFICATION_URL_PROP);

            Document data = Parser.parseHTMLData(classificationHTML);

            Elements elements = data.getElementsByTag("tbody");
            if (CollectionUtils.isEmpty(elements)) {
                Log.d(MainActivity.DEBUG_APP, "Unable to find data to parse data for classification");
            } else {
                if (elements.size() > 1) {
                    Log.d(MainActivity.DEBUG_APP, "Unexpected number of parsed data for classification from request");
                }
                ApplicationCacheManager.setClassification(Parser.getPositionsInfo(elements.get(0).children()));
            }
        }

        dataLoadedListener.onDataLoadedIntoCache();
    }

    @Override
    public void getDefaultGameDayInfo(final DataLoadedListener<GameDayInfo> dataLoadedListener) {
        if (!ApplicationCacheManager.hasGameDayCacheData()) {
            String gameDaysHTML = HttpHelper.getHtmlFromSimpleHttpRequestUsingProperties(Constants.GAMES_URL_PROP);
            Document gameDayInfoData = Parser.parseHTMLAndSaveTokenData(gameDaysHTML);
            GameDayInfo gameDayInfo = Parser.getGameInfoFromData(gameDayInfoData);
            ApplicationCacheManager.setGameDayInfo(gameDayInfo);
        }

        dataLoadedListener.onDataLoaded(ApplicationCacheManager.getGameDayInfo());
    }

    @Override
    public void refreshGameInfo(final int gameDayId, final int kind, final int season,
            final DataLoadedListener dataLoadedListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String gameDaysHTML = requestGameDayData(gameDayId, kind, season);
                Document gameDayInfoData = Parser.parseHTMLAndSaveTokenData(gameDaysHTML);
                GameDay gameDay = Parser.getGameDay(gameDayInfoData);
                ApplicationCacheManager.updateGameDay(gameDay.getId(), gameDay.getGames());
                dataLoadedListener.onDataLoadedIntoCache();
            }
        }).start();
    }

    @Override
    public void getLiveData(DataLoadedListener<LiveData> dataDataLoadedListener) {
        String url = MainActivity.properties.getProperty(Constants.URL_LIVE_GAMES_OVERVIEW_PROP);
        HttpGet request = new HttpGet(url);
        String response = HttpUtils.doAsyncGet(request);
        dataDataLoadedListener.onDataLoaded(JSONUtils.readValue(response, LiveData.class));
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
