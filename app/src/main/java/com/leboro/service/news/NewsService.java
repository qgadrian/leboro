package com.leboro.service.news;

import java.util.List;

import com.leboro.model.news.News;
import com.leboro.view.listeners.CacheDataLoadedListener;

public interface NewsService {

    void getAllProviderNews(CacheDataLoadedListener dataLoadedListener);

    List<News> getFEBNews();

    List<News> getYoutubeVideos(News.NewsKind... newsYoutubeProviders);

    List<News> getZonaDeBasquetNews();

}
