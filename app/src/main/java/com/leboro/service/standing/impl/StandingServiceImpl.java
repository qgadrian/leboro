package com.leboro.service.standing.impl;

import android.util.Log;

import com.leboro.MainActivity;
import com.leboro.R;
import com.leboro.model.api.standing.PlayerStanding;
import com.leboro.service.standing.StandingService;
import com.leboro.service.task.http.standing.StandingHttpPostAsyncTask;
import com.leboro.util.Constants;
import com.leboro.util.cache.ApplicationCacheManager;
import com.leboro.util.html.HTMLHelper;
import com.leboro.util.http.HttpRequestResponse;
import com.leboro.util.parser.standing.StandingParser;
import com.leboro.util.properties.PropertiesHelper;
import com.leboro.view.listeners.CacheDataLoadedListener;

import org.apache.commons.collections4.CollectionUtils;
import org.jsoup.nodes.Document;

import java.util.List;
import java.util.concurrent.ExecutionException;

import cz.msebera.android.httpclient.HttpStatus;

public class StandingServiceImpl implements StandingService {

    @Override
    public void getPlayerStandings(final int standingType, final CacheDataLoadedListener dataLoadedListener) {
        if (CollectionUtils.isEmpty(ApplicationCacheManager.getPlayerStandings(standingType))) {
            String standingsHTML;

            try {
                standingsHTML = requestStandingData(standingType);
                parseHTMLAndSaveStandingsToCache(standingsHTML, standingType);
            } catch (Exception e) {
                Log.e(MainActivity.DEBUG_APP_NAME, "Oh jezz, problems with the api again...");

                HTMLHelper.clearStandingViewStateToken();
                HTMLHelper.clearStandingEventValidationToken();

                standingsHTML = requestStandingData(standingType);

                parseHTMLAndSaveStandingsToCache(standingsHTML, standingType);
            }
        }

        dataLoadedListener.onDataLoadedIntoCache();
    }

    private void parseHTMLAndSaveStandingsToCache(String standingsHTML, int standingType) {
        Document htmlInfoData = StandingParser
                .parseHTMLAndSaveStandingTokenData(standingsHTML);
        List<PlayerStanding> playerStandings = StandingParser.getPlayerStandings(htmlInfoData);
        ApplicationCacheManager.updatePlayerStandings(standingType, playerStandings);
    }

    @Override
    public int getPlayerStandingNameStringResourceId(int position) {
        HTMLHelper.StandingType standingType = HTMLHelper.StandingType.getFromPosition(position);

        switch (standingType) {
            case POINTS:
                return R.string.standing_points_bar_title;
            case GAME_VALUE:
                return R.string.standing_game_value_bar_title;
            case TOTAL_REBOUNDS:
                return R.string.standing_rebounds_bar_title;
            case DEFENSIVE_REBOUNDS:
                return R.string.standing_offensive_rebounds_bar_title;
            case OFFENSIVE_REBOUNDS:
                return R.string.standing_defensive_rebounds_bar_title;
            case ASSISTS:
                return R.string.standing_assists_bar_title;
            case STEALS:
                return R.string.standing_steals_bar_title;
            case TURNOVERS:
                return R.string.standing_turnovers_bar_title;
            case BLOCKS:
                return R.string.standing_blocks_bar_title;
            //                case 9:
            //                    return MINUTES_PER_GAME;
            //                case 10:
            //                    return FIELD_GOALS_PERCENTAGE;
            //                case 11:
            //                    return THREE_POINTER_PERCENTAGE;
            //                case 12:
            //                    return FREE_THROW_PERCENTAGE;
            default:
                Log.d(MainActivity.DEBUG_APP_NAME,
                        "Unrecognized standing position [" + position + "]. Returning points string");
                return R.string.standing_points_bar_title;
        }
    }

    private String requestStandingData(int standingType) {
        String url = PropertiesHelper.getProperty(Constants.STANDINGS_URL_PROP);
        String acceptHeader = PropertiesHelper.getProperty(Constants.URL_HEADER_ACCEPT_PROP);
        String referrerHeader = PropertiesHelper.getProperty(Constants.URL_HEADER_REFERRER_PROP);

        StandingHttpPostAsyncTask httpPostAsyncTask = new StandingHttpPostAsyncTask();
        try {
            String serverEventValidationToken = HTMLHelper.getStandingEventValidationToken();
            String serverViewState = HTMLHelper.getStandingViewStateToken();

            StandingHttpPostAsyncTask.ResultTaskArgument resultTaskArgument = new StandingHttpPostAsyncTask.ResultTaskArgument
                    (url, acceptHeader, referrerHeader, standingType, serverEventValidationToken, serverViewState);

            HttpRequestResponse response = httpPostAsyncTask.execute(resultTaskArgument).get();

            if (response.getResponseStatusCode() != HttpStatus.SC_OK) {
                Log.d(MainActivity.DEBUG_APP_NAME, "Received and status error from standings request");
                throw new RuntimeException("Error obtaining classification data");
            }

            return response.getResponseBody();

        } catch (InterruptedException | ExecutionException e) {
            Log.d(MainActivity.DEBUG_APP_NAME, "Error obtaining classification data", e);
        }

        return null;
    }

}
