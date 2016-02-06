package com.leboro.view.fragment.news;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.leboro.R;
import com.leboro.model.news.News;
import com.leboro.service.ApplicationServiceProvider;
import com.leboro.util.Constants;
import com.leboro.view.fragment.LoadableFragment;
import com.leboro.view.listeners.DataLoadedListener;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class NewsArticleFragment extends LoadableFragment implements DataLoadedListener<News> {

    private View mView;

    private News news;

    private static ImageLoader imageLoader = ApplicationServiceProvider.getNetworkImageLoaderService()
            .getImageLoader();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.news_article_fragment, container, false);

        Bundle bundle = getArguments();
        news = bundle.getParcelable(Constants.BUNDLE_ARG_NEWS);

        if (news != null && !StringUtils.isEmpty(news.getArticleText())) {
            removeLoadingLayoutAndShowResource(mView, R.id.newsArticleContentLayout);
            updateNewsImage();
            updateNewsText(news);
        } else {
            final DataLoadedListener<News> dataLoadedListener = this;
            AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
                @Override
                public void run() {
                    updateNewsImage();
                    ApplicationServiceProvider.getNewsService().fillNewsWithArticleText(news, dataLoadedListener);
                }
            });
        }
        
        return mView;
    }

    @Override
    public void onDataLoaded(final News news) {
        if (isVisible()) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    removeLoadingLayoutAndShowResource(mView, R.id.newsArticleContentLayout);
                    updateNewsText(news);
                }
            });
        }
    }

    private void updateNewsText(News news) {
        WebView articleTextWebView = (WebView) mView.findViewById(R.id.newsArticleTextWebView);
        articleTextWebView.loadData(news.getArticleText(), "text/html; charset=UTF-8", null);
    }

    private void updateNewsImage() {
        NetworkImageView newsImage = (NetworkImageView) mView.findViewById(R.id.newsArticleImage);
        newsImage.setImageUrl(news.getImageUrl(), imageLoader);
    }

}
