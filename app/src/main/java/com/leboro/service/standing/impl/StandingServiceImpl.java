package com.leboro.service.standing.impl;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.commons.collections4.CollectionUtils;
import org.jsoup.nodes.Document;

import com.leboro.MainActivity;
import com.leboro.R;
import com.leboro.model.api.standing.PlayerStanding;
import com.leboro.service.standing.StandingService;
import com.leboro.service.task.standing.StandingHttpPostAsyncTask;
import com.leboro.util.Constants;
import com.leboro.util.cache.ApplicationCacheManager;
import com.leboro.util.html.HTMLHelper;
import com.leboro.util.parser.standing.StandingParser;
import com.leboro.view.listeners.CacheDataLoadedListener;

import android.util.Log;

public class StandingServiceImpl implements StandingService {

    @Override
    public void getPlayerStandings(final int standingType, final CacheDataLoadedListener dataLoadedListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (CollectionUtils.isEmpty(ApplicationCacheManager.getPlayerStandings(standingType))) {
                    String standingsHTML = requestStandingData(standingType);

                    try {
                        parseHTMLAndSaveStandingsToCache(standingsHTML, standingType);
                    } catch (Exception e) {
                        HTMLHelper.clearStandingViewStateToken();
                        HTMLHelper.clearStandingEventValidationToken();

                        Log.e(MainActivity.DEBUG_APP_NAME, "Oh jezz, problems with the api again...");

                        parseHTMLAndSaveStandingsToCache(standingsHTML, standingType);
                    }
                }

                dataLoadedListener.onDataLoadedIntoCache();
            }
        }).start();
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
        Properties properties = MainActivity.properties;

        String url = properties.getProperty(Constants.STANDINGS_URL_PROP);
        String acceptHeader = properties.getProperty(Constants.URL_HEADER_ACCEPT_PROP);
        String referrerHeader = properties.getProperty(Constants.URL_HEADER_REFERRER_PROP);

        StandingHttpPostAsyncTask httpPostAsyncTask = new StandingHttpPostAsyncTask();
        try {
            String serverEventValidationToken = HTMLHelper.getStandingEventValidationToken();
            String serverViewState = HTMLHelper.getStandingViewStateToken();

            StandingHttpPostAsyncTask.ResultTaskArgument resultTaskArgument = new StandingHttpPostAsyncTask.ResultTaskArgument
                    (url, acceptHeader, referrerHeader, standingType, serverEventValidationToken, serverViewState);

            return httpPostAsyncTask.execute(resultTaskArgument).get();
        } catch (InterruptedException | ExecutionException e) {
            Log.d(MainActivity.DEBUG_APP_NAME, "Error obtaining classification data", e);
        }

        return null;
    }

}
