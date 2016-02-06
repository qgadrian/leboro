package com.leboro.service.news;

import com.leboro.model.news.News;
import com.leboro.view.listeners.CacheDataLoadedListener;
import com.leboro.view.listeners.DataLoadedListener;

public interface NewsService {

    void getNews(CacheDataLoadedListener dataLoadedListener);

    void fillNewsWithArticleText(News news, DataLoadedListener<News> dataLoadedListener);
}
