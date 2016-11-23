package com.leboro.service.news.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.PlaylistItemSnippet;
import com.google.common.collect.Lists;
import com.leboro.MainActivity;
import com.leboro.R;
import com.leboro.model.news.News;
import com.leboro.service.ApplicationServiceProvider;
import com.leboro.service.news.NewsService;
import com.leboro.util.Constants;
import com.leboro.util.cache.ApplicationCacheManager;
import com.leboro.util.list.ListUtils;
import com.leboro.util.properties.PropertiesHelper;
import com.leboro.view.helper.http.HttpHelper;
import com.leboro.view.listeners.CacheDataLoadedListener;

import android.util.Log;

import static com.leboro.model.news.News.NewsKind.COBROTHERS_YOUTUBE_VIDEO;
import static com.leboro.util.Constants.NEWS_MAX_VIDEOS_PER_PLAYLIST;
import static com.leboro.util.Constants.NEWS_YOUTUBE_ITEMS;
import static com.leboro.util.Constants.SECRET_YOUTUBE_API;

public class NewsServiceImpl implements NewsService {


    @Override
    public void getAllProviderNews(final CacheDataLoadedListener dataLoadedListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!ApplicationCacheManager.hasNewsData()) {
                    List<News> febNews = getFEBNews();
                    List<News> youtubeNews = getYoutubeVideos(COBROTHERS_YOUTUBE_VIDEO);
                    List<News> zonaDeBasquetNews = getZonaDeBasquetNews();

                    List<News> newsToCache = ListUtils.getJoinedLists(febNews, youtubeNews, zonaDeBasquetNews);

                    Collections.sort(newsToCache);

                    ApplicationCacheManager.setNews(newsToCache);
                }

                dataLoadedListener.onDataLoadedIntoCache();
            }
        }).start();
    }

    @Override
    public List<News> getFEBNews() {
        String newsHTML = HttpHelper.getHtmlFromSimpleHttpRequestUsingProperties(Constants.NEWS_FEB_URL);
        return ApplicationServiceProvider.getFebNewsParser().getNews(newsHTML);
    }

    @Override
    public List<News> getZonaDeBasquetNews() {
        String newsHTML = HttpHelper.getHtmlFromSimpleHttpRequestUsingProperties(Constants.NEWS_ZONA_DE_BASQUET_URL);
        return ApplicationServiceProvider.getZonaDeBasquetNewsParser().getNews(newsHTML);
    }

    @Override
    public List<News> getYoutubeVideos(News.NewsKind... newsYoutubeProviders) {

        long maxYoutubeVideosPerPlaylist = PropertiesHelper.getPropertyAsLong(NEWS_MAX_VIDEOS_PER_PLAYLIST);
        String youtubeRequestItems = PropertiesHelper.getProperty(NEWS_YOUTUBE_ITEMS);

        List<News> youtubeNewsList = Lists.newArrayList();

        for (News.NewsKind newsYoutubeProvider : newsYoutubeProviders) {

            try {
                YouTube youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(),
                        new HttpRequestInitializer() {
                            @Override
                            public void initialize(HttpRequest hr) throws IOException {}
                        }).setApplicationName(MainActivity.context.getString(R.string.app_name)).build();

                List<PlaylistItem> playlistItemList = Lists.newArrayList();

                String playListId = newsYoutubeProvider.getProviderValue();

                YouTube.PlaylistItems.List playlistItemRequest = youtube.playlistItems().list("snippet");
                playlistItemRequest.setKey(PropertiesHelper.getSecretProperty(SECRET_YOUTUBE_API));
                playlistItemRequest.setPlaylistId(playListId);

                playlistItemRequest.setFields(youtubeRequestItems);

                String nextToken = "";

                // Call the API one or more times to retrieve all items in the
                // list. As long as the API response returns a nextPageToken,
                // there are still more items to retrieve.
                do {
                    playlistItemRequest.setPageToken(nextToken);
                    playlistItemRequest.setMaxResults(maxYoutubeVideosPerPlaylist);
                    PlaylistItemListResponse playlistItemResult = playlistItemRequest.execute();

                    playlistItemList.addAll(playlistItemResult.getItems());

                    nextToken = playlistItemResult.getNextPageToken();
                } while (nextToken != null && maxYoutubeVideosPerPlaylist > playlistItemList.size());

                Log.d(MainActivity.DEBUG_APP_NAME, "Done retrieving videos");

                for (PlaylistItem playlistItem : playlistItemList) {
                    PlaylistItemSnippet snippet = playlistItem.getSnippet();

                    News youtubeNews = new News(snippet.getTitle(), snippet.getDescription(),
                            snippet.getThumbnails().getStandard().getUrl(), snippet.getResourceId().getVideoId(),
                            newsYoutubeProvider, new Date(snippet.getPublishedAt().getValue()));

                    youtubeNewsList.add(youtubeNews);

                }

            } catch (GoogleJsonResponseException e) {
                Log.e(MainActivity.DEBUG_APP_NAME, "Error parsing json response for youtube playlist videos", e);
            } catch (Throwable e) {
                Log.e(MainActivity.DEBUG_APP_NAME, "Error retrieving youtube playlist videos", e);
            }
        }

        return youtubeNewsList;
    }

}
