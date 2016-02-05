package com.leboro.service;

import com.leboro.service.news.NewsService;
import com.leboro.service.news.impl.NewsServiceImpl;
import com.leboro.service.statistics.StatisticsService;
import com.leboro.service.statistics.impl.StatisticsServiceImpl;
import com.leboro.service.volley.NetworkImageLoaderService;
import com.leboro.service.volley.impl.VolleyServiceImpl;

public class ApplicationServiceProvider {

    private static StatisticsService statisticsService;

    private static NetworkImageLoaderService networkImageLoaderService;

    private static NewsService newsService;

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
}
