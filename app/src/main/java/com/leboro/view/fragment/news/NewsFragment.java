package com.leboro.view.fragment.news;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.leboro.MainActivity;
import com.leboro.R;
import com.leboro.model.news.News;
import com.leboro.service.ApplicationServiceProvider;
import com.leboro.util.Constants;
import com.leboro.util.cache.ApplicationCacheManager;
import com.leboro.util.properties.PropertiesHelper;
import com.leboro.view.adapters.news.NewsListAdapter;
import com.leboro.view.fragment.LoadableFragment;
import com.leboro.view.listeners.CacheDataLoadedListener;

import java.util.List;

import static com.leboro.util.Constants.SECRET_YOUTUBE_API;

public class NewsFragment extends LoadableFragment implements CacheDataLoadedListener {

    private View mView;

    private ListView newsList;

    private NewsListAdapter newsListAdapter;

    private List<News> news;

    private final CacheDataLoadedListener dataLoadedListener = this;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.news_fragment, container, false);

        new Thread(new Runnable() {
            @Override
            public void run() {
                ApplicationServiceProvider.getNewsService().getAllProviderNews(dataLoadedListener);
            }
        }).start();

        return mView;
    }

    @Override
    public void onDataLoadedIntoCache() {
        this.news = ApplicationCacheManager.getNews();

        if (isAdded()) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    initializeView();
                    removeLoadingLayoutAndShowResource(mView, R.id.newsListView);
                    newsListAdapter.updateDataAndNotify(news);
                }
            });
        }
    }

    @Override
    protected void updateActionAndNavigationBar() {
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.navigation_drawer_news));
        MainActivity.navigationView.setCheckedItem(R.id.nav_news);
    }

    private void initializeView() {
        initializeViews();
        initializeAdapters();
        initializeEvents();
    }

    private void initializeViews() {
        newsList = (ListView) mView.findViewById(R.id.newsListView);
    }

    private void initializeAdapters() {
        newsListAdapter = new NewsListAdapter(mView.getContext(), R.layout.news_row, news);
        newsList.setAdapter(newsListAdapter);
    }

    private void initializeEvents() {
        newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news = newsListAdapter.getItem(position);

                switch (news.getKind()) {
                    case FEB_ARTICLE:
                    case ZONA_DE_BASQUET_ARTICLE:
                        openNewsArticle(news);
                        break;
                    case COBROTHERS_YOUTUBE_VIDEO:
                        openYoutubeVideo(news);
                        break;
                }
            }
        });
    }

    private void openNewsArticle(News news) {
        Bundle bundle = new Bundle(1);
        bundle.putParcelable(Constants.BUNDLE_ARG_NEWS, news);

        NewsArticleFragment newsArticleFragment = new NewsArticleFragment();
        newsArticleFragment.setArguments(bundle);

        ((MainActivity) getActivity())
                .fragmentTransition(newsArticleFragment, getString(R.string.news_article_toolbar_title));
    }

    private void openYoutubeVideo(News news) {
        Intent intent = YouTubeStandalonePlayer
                .createVideoIntent(getActivity(), PropertiesHelper.getSecretProperty(SECRET_YOUTUBE_API),
                        news.getArticleUrl(), 0, true, false);
        startActivity(intent);
    }

}
