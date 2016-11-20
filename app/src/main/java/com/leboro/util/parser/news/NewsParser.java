package com.leboro.util.parser.news;

import java.util.List;

import com.leboro.model.news.News;
import com.leboro.util.parser.BaseParser;
import com.leboro.view.listeners.DataLoadedListener;

public abstract class NewsParser extends BaseParser {

    public abstract List<News> getNews(String newsHTML);

    public abstract String getArticleText(String newsArticleHTML);

    public abstract void fillNewsWithArticleText(News news, DataLoadedListener<News> dataLoadedListener);

}
