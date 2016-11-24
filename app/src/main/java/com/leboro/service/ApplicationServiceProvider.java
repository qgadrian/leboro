package com.leboro.service;

import com.leboro.service.game.GameService;
import com.leboro.service.game.impl.GameServiceImpl;
import com.leboro.service.live.LiveService;
import com.leboro.service.live.impl.LiveServiceImpl;
import com.leboro.service.news.NewsService;
import com.leboro.service.news.impl.NewsServiceImpl;
import com.leboro.service.standing.StandingService;
import com.leboro.service.standing.impl.StandingServiceImpl;
import com.leboro.service.statistics.StatisticsService;
import com.leboro.service.statistics.impl.StatisticsServiceImpl;
import com.leboro.service.teaminfo.TeamInfoService;
import com.leboro.service.teaminfo.impl.TeamInfoServiceImpl;
import com.leboro.service.volley.NetworkImageLoaderService;
import com.leboro.service.volley.impl.VolleyServiceImpl;
import com.leboro.util.parser.news.feb.FebNewsParser;
import com.leboro.util.parser.news.zonadebasquet.ZonaDeBasquetNewsParser;

public class ApplicationServiceProvider {

    private static StatisticsService statisticsService;

    private static NetworkImageLoaderService networkImageLoaderService;

    private static NewsService newsService;

    private static LiveService liveService;

    private static GameService gameService;

    private static StandingService standingService;

    private static TeamInfoService teamInfoService;

    // News providers parsers

    private static FebNewsParser febNewsParser;

    private static ZonaDeBasquetNewsParser zonaDeBasquetNewsParser;

    public static NetworkImageLoaderService getNetworkImageLoaderService() {
        if (networkImageLoaderService == null) {
            networkImageLoaderService = new VolleyServiceImpl();
        }

        return networkImageLoaderService;
    }

    public static StatisticsService getStatisticsService() {
        if (statisticsService == null) {
            statisticsService = new StatisticsServiceImpl();
        }

        return statisticsService;
    }

    public static NewsService getNewsService() {
        if (newsService == null) {
            newsService = new NewsServiceImpl();
        }

        return newsService;
    }

    public static LiveService getLiveService() {
        if (liveService == null) {
            liveService = new LiveServiceImpl();
        }

        return liveService;
    }

    public static GameService getGameService() {
        if (gameService == null) {
            gameService = new GameServiceImpl();
        }

        return gameService;
    }

    public static StandingService getStandingService() {
        if (standingService == null) {
            standingService = new StandingServiceImpl();
        }

        return standingService;
    }

    public static TeamInfoService getTeamInfoService() {
        if (teamInfoService == null) {
            teamInfoService = new TeamInfoServiceImpl();
        }

        return teamInfoService;
    }

//    News providers info

    public static FebNewsParser getFebNewsParser() {
        if (febNewsParser == null) {
            febNewsParser = new FebNewsParser();
        }

        return febNewsParser;
    }

    public static ZonaDeBasquetNewsParser getZonaDeBasquetNewsParser() {
        if (zonaDeBasquetNewsParser == null) {
            zonaDeBasquetNewsParser = new ZonaDeBasquetNewsParser();
        }

        return zonaDeBasquetNewsParser;
    }
}
