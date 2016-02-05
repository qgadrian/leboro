package com.leboro.service.news.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.google.common.collect.Lists;
import com.leboro.MainActivity;
import com.leboro.model.news.News;
import com.leboro.service.news.NewsService;
import com.leboro.util.Constants;
import com.leboro.util.Parser;
import com.leboro.util.cache.ApplicationCacheManager;
import com.leboro.view.helper.http.HttpHelper;
import com.leboro.view.listeners.DataLoadedListener;

import android.util.Log;

public class NewsServiceImpl implements NewsService {

    @Override
    public void getNews(DataLoadedListener<News> dataLoadedListener) {
        String newsHTML = HttpHelper.getHtmlFromSimpleHttpRequestUsingProperties(Constants.NEWS_URL_PROP);

        // TODO getting document here for parse after this into another method...
        Document data = Parser.parseHTMLData(newsHTML);

        List<News> news = Lists.newArrayList();

        Elements elements = data.getElementsByClass("content-mod");
        if (CollectionUtils.isEmpty(elements)) {
            Log.d(MainActivity.DEBUG_APP, "Unable to find data to parse data for classification");
        } else {
            news = Parser.getNews(elements.get(0).children());
        }

        ApplicationCacheManager.setNews(news);

        dataLoadedListener.onDataLoadedIntoCache();
    }

    @Override
    public void fillNewsWithArticleText(News news, DataLoadedListener<News> dataLoadedListener) {
        String newsArticleHTML = HttpHelper.getHtmlFromSimpleHttpRequest(news.getArticleUrl());
        String articleText = Parser.getArticleText(newsArticleHTML);
        news.setArticleText(articleText);
        dataLoadedListener.onDataLoaded(news);
    }

}
