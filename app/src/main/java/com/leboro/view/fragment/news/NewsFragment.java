package com.leboro.view.fragment.news;

import java.util.Collections;
import java.util.List;

import com.leboro.MainActivity;
import com.leboro.R;
import com.leboro.model.news.News;
import com.leboro.service.ApplicationServiceProvider;
import com.leboro.util.Constants;
import com.leboro.util.cache.ApplicationCacheManager;
import com.leboro.view.adapters.news.NewsListAdapter;
import com.leboro.view.fragment.LoadableFragment;
import com.leboro.view.listeners.DataLoadedListener;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class NewsFragment extends LoadableFragment implements DataLoadedListener<News> {

    private View mView;

    private ListView newsList;

    private NewsListAdapter newsListAdapter;

    private List<News> news;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.news_fragment, container, false);

        if (ApplicationCacheManager.hasNewsData()) {
            news = ApplicationCacheManager.getNews();
            removeLoadingLayoutAndShowResource(mView, R.id.newsListView);
        } else {
            news = Collections.emptyList();
            final DataLoadedListener<News> dataLoadedListener = this;
            AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
                @Override
                public void run() {
                    ApplicationServiceProvider.getNewsService().getNews(dataLoadedListener);
                }
            });
        }

        initializeViews();
        initializeAdapters();
        initializeEvents();

        return mView;
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
                Bundle bundle = new Bundle(1);
                bundle.putParcelable(Constants.BUNDLE_ARG_NEWS, news);

                NewsArticleFragment newsArticleFragment = new NewsArticleFragment();
                newsArticleFragment.setArguments(bundle);

                ((MainActivity) getActivity())
                        .fragmentTransition(newsArticleFragment, getString(R.string.news_article_toolbar_title));
            }
        });
    }

    @Override
    public void onDataLoadedIntoCache() {
        this.news = ApplicationCacheManager.getNews();

        if (isVisible()) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    removeLoadingLayoutAndShowResource(mView, R.id.newsListView);
                    newsListAdapter.updateDataAndNotify(news);
                }
            });
        }
    }

    @Override
    public void onDataLoaded(News data) {
        // noop
    }

    @Override
    public void onDataLoaded(final List<News> news) {
        // noop
    }

}
