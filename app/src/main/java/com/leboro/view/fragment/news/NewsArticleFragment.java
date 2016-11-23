package com.leboro.view.fragment.news;

import org.apache.commons.lang3.StringUtils;

import com.leboro.MainActivity;
import com.leboro.R;
import com.leboro.model.news.News;
import com.leboro.service.ApplicationServiceProvider;
import com.leboro.util.Constants;
import com.leboro.view.fragment.LoadableFragment;
import com.leboro.view.helper.news.NewsHelper;
import com.leboro.view.listeners.DataLoadedListener;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class NewsArticleFragment extends LoadableFragment implements DataLoadedListener<News> {

    private View mView;

    private News news;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.news_article_fragment, container, false);

        Bundle bundle = getArguments();
        news = bundle.getParcelable(Constants.BUNDLE_ARG_NEWS);

        if (news != null && !StringUtils.isEmpty(news.getArticleText())) {
            removeLoadingLayoutAndShowResource(mView, R.id.newsArticleContentLayout);
            initializeViews(news);
        } else {
            final DataLoadedListener<News> dataLoadedListener = this;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    updateNewsImage();

                    switch (news.getKind()) {
                        case FEB_ARTICLE:
                            ApplicationServiceProvider.getFebNewsParser()
                                    .fillNewsWithArticleText(news, dataLoadedListener);
                            break;
                        case ZONA_DE_BASQUET_ARTICLE:
                            ApplicationServiceProvider.getZonaDeBasquetNewsParser()
                                    .fillNewsWithArticleText(news, dataLoadedListener);
                            break;
                        default:
                            Log.e(MainActivity.DEBUG_APP_NAME, "Unrecognized news kind [" + news.getKind() + "]");
                    }
                }
            }).start();
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
                    initializeViews(news);
                }
            });
        }
    }

    private void initializeViews(News news) {
        updateCollapseViewTitle(news);
        updateNewsImage();
        updateNewsText(news);
    }

    private void updateCollapseViewTitle(News news) {
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) mView.findViewById(R.id.newsArticleCollapsingToolbar);
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.AppTheme_NewsArticleCollapse_Expanded);
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.AppTheme_NewsArticleCollapse_Collapsed);
        collapsingToolbar.setTitle(news.getTitle());
    }

    private void updateNewsText(News news) {
        WebView articleTextWebView = (WebView) mView.findViewById(R.id.newsArticleTextWebView);
        articleTextWebView.loadData(news.getArticleText(), "text/html; charset=UTF-8", null);
    }

    private void updateNewsImage() {
        NewsHelper.loadNewsImage(mView, news, R.id.newsArticleImage);
    }

}
