package com.leboro.util.parser.news.zonadebasquet;

import java.util.Date;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.google.common.collect.Lists;
import com.leboro.MainActivity;
import com.leboro.model.news.News;
import com.leboro.util.calendar.CalendarUtils;
import com.leboro.util.parser.news.NewsParser;
import com.leboro.view.helper.http.HttpHelper;
import com.leboro.view.listeners.DataLoadedListener;

import android.util.Log;

public class ZonaDeBasquetNewsParser extends NewsParser {

    @Override
    public List<News> getNews(String newsHTML) {
        List<News> news = Lists.newArrayList();

        try {

            Document data = parseHTMLData(newsHTML);
            Element articleParentElement = data.getElementById("infinite-wrap");

            for (Element articleElement : articleParentElement.getElementsByTag("article")) {
                try {
                    Element aElement = articleElement.getElementsByTag("a").first();
                    String title = aElement.attr("title");
                    String articleUrl = aElement.attr("href");

                    // TODO Change this replace to use a regexp
                    String imageUrl = articleElement.getElementsByTag("img").first().attr("src")
                            .replace("?w=400&h=200&crop=1", "?w=800&h=400");
                    String description = "";

                    Date publicationDate = CalendarUtils.parsePublicationDateFromUrl(articleUrl, true);

                    news.add(new News(title, description, imageUrl, articleUrl, News.NewsKind.ZONA_DE_BASQUET_ARTICLE,
                            publicationDate));
                } catch (Exception e) {
                    Log.e(MainActivity.DEBUG_APP_NAME, "Could not parse a news entry", e);
                }
            }
        } catch (Exception e) {
            Log.e(MainActivity.DEBUG_APP_NAME, "Error parsing zonaDeBasquet news", e);
        }

        return news;
    }

    @Override
    public String getArticleText(String newsArticleHTML) {
        String articleText = "";

        try {
            Document htmlDocument = parseHTMLData(newsArticleHTML);

            Element articleParagraphs = htmlDocument.getElementsByClass("post-content").first();

            articleParagraphs.getElementById("jp-post-flair").remove(); // Unstyled div

            articleText = articleParagraphs.outerHtml();
        } catch (Exception e) {
            Log.e(MainActivity.DEBUG_APP_NAME, "Error parsing news article", e);
        }

        return articleText;
    }

    @Override
    public void fillNewsWithArticleText(News news, DataLoadedListener<News> dataLoadedListener) {
        String newsArticleHTML = HttpHelper.getHtmlFromSimpleHttpRequest(news.getArticleUrl());
        String articleText = getArticleText(newsArticleHTML);
        news.setArticleText(articleText);
        dataLoadedListener.onDataLoaded(news);
    }

}
