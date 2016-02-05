package com.leboro.service.news;

import com.leboro.model.news.News;
import com.leboro.view.listeners.DataLoadedListener;

public interface NewsService {

    void getNews(DataLoadedListener<News> dataLoadedListener);

    void fillNewsWithArticleText(News news, DataLoadedListener<News> dataLoadedListener);
}
