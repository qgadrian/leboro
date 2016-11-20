package com.leboro.service.statistics.impl;

import java.util.concurrent.ExecutionException;

import org.apache.commons.collections4.CollectionUtils;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.leboro.MainActivity;
import com.leboro.model.api.live.overview.LiveData;
import com.leboro.model.game.GameDay;
import com.leboro.model.game.GameDayInfo;
import com.leboro.service.statistics.StatisticsService;
import com.leboro.service.task.http.gameday.GameDayHttpPostAsyncTask;
import com.leboro.util.Constants;
import com.leboro.util.cache.ApplicationCacheManager;
import com.leboro.util.html.HTMLHelper;
import com.leboro.util.http.HttpUtils;
import com.leboro.util.json.JSONUtils;
import com.leboro.util.parser.BaseParser;
import com.leboro.util.parser.classification.ClassificationParser;
import com.leboro.util.parser.game.GameParser;
import com.leboro.util.properties.PropertiesHelper;
import com.leboro.view.helper.http.HttpHelper;
import com.leboro.view.listeners.CacheDataLoadedListener;
import com.leboro.view.listeners.DataLoadedListener;

import android.util.Log;
import cz.msebera.android.httpclient.client.methods.HttpGet;

public class StatisticsServiceImpl implements StatisticsService {

    @Override
    public void getClassification(CacheDataLoadedListener dataLoadedListener) {
        if (CollectionUtils.isEmpty(ApplicationCacheManager.getClassification())) {

            String classificationHTML = HttpHelper.getHtmlFromSimpleHttpRequestUsingProperties(Constants
                    .CLASSIFICATION_URL_PROP);

            Document data = BaseParser.parseHTMLData(classificationHTML);

            Elements elements = data.getElementsByTag("tbody");
            if (CollectionUtils.isEmpty(elements)) {
                Log.d(MainActivity.DEBUG_APP_NAME, "Unable to find data to parse data for classification");
            } else {
                if (elements.size() > 1) {
                    Log.d(MainActivity.DEBUG_APP_NAME,
                            "Unexpected number of parsed data for classification from request");
                }
                ApplicationCacheManager
                        .setClassification(ClassificationParser.getPositionsInfo(elements.get(0).children()));
            }
        }

        dataLoadedListener.onDataLoadedIntoCache();
    }

    @Override
    public void getDefaultGameDayInfo(final CacheDataLoadedListener dataLoadedListener) {
        if (!ApplicationCacheManager.hasGameDayCacheData()) {
            String gameDaysHTML = HttpHelper.getHtmlFromSimpleHttpRequestUsingProperties(Constants.GAMES_URL_PROP);
            Document gameDayInfoData = BaseParser.parseHTMLAndSaveTokenData(gameDaysHTML);
            GameDayInfo gameDayInfo = GameParser.getGameInfoFromData(gameDayInfoData);
            ApplicationCacheManager.setGameDayInfo(gameDayInfo);
        }

        dataLoadedListener.onDataLoadedIntoCache();
    }

    @Override
    public void refreshGameInfo(final int gameDayId, final int kind, final int season,
            final CacheDataLoadedListener dataLoadedListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String gameDaysHTML = requestGameDayData(gameDayId, kind, season);
                Document gameDayInfoData = BaseParser.parseHTMLAndSaveTokenData(gameDaysHTML);
                GameDay gameDay = GameParser.getGameDay(gameDayInfoData);
                ApplicationCacheManager.updateGameDay(gameDay.getId(), gameDay.getGames());
                dataLoadedListener.onDataLoadedIntoCache();
            }
        }).start();
    }

    @Override
    public void getLiveData(DataLoadedListener<LiveData> dataDataLoadedListener) {
        String url = PropertiesHelper.getProperty(Constants.URL_LIVE_GAMES_OVERVIEW_PROP);
        HttpGet request = new HttpGet(url);
        HttpHelper.addApiRequestHeaders(request);
        String response = HttpUtils.doAsyncGet(request);
        dataDataLoadedListener.onDataLoaded(JSONUtils.readValue(response, LiveData.class));
    }

    private String requestGameDayData(int gameDayId, int kind, int season) {
        String url = PropertiesHelper.getProperty(Constants.GAMES_URL_PROP);
        String acceptHeader = PropertiesHelper.getProperty(Constants.URL_HEADER_ACCEPT_PROP);
        String referrerHeader = PropertiesHelper.getProperty(Constants.URL_HEADER_REFERRER_PROP);

        GameDayHttpPostAsyncTask gameDayHttpPostAsyncTask = new GameDayHttpPostAsyncTask();
        try {
            String serverEventValidationToken = HTMLHelper.getEventValidationToken();
            String serverViewState = HTMLHelper.getViewStateToken();

            GameDayHttpPostAsyncTask.ResultTaskArgument resultTaskArgument = new GameDayHttpPostAsyncTask.ResultTaskArgument
                    (gameDayId, kind, season, referrerHeader, acceptHeader, url, serverViewState,
                            serverEventValidationToken);
            return gameDayHttpPostAsyncTask.execute(resultTaskArgument).get();
        } catch (InterruptedException | ExecutionException e) {
            Log.d(MainActivity.DEBUG_APP_NAME, "Error obtaining classification data", e);
        }

        return null;
    }
}
