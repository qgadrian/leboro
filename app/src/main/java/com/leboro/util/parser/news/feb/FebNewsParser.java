package com.leboro.util.parser.news.feb;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.common.collect.Lists;
import com.leboro.MainActivity;
import com.leboro.model.news.News;
import com.leboro.util.calendar.CalendarUtils;
import com.leboro.util.parser.news.NewsParser;
import com.leboro.view.helper.http.HttpHelper;
import com.leboro.view.listeners.DataLoadedListener;

import android.util.Log;

public class FebNewsParser extends NewsParser {

    @Override
    public List<News> getNews(String newsHTML) {
        List<News> febNews = Lists.newArrayList();

        try {
            Document data = parseHTMLData(newsHTML);
            Elements elements = data.getElementsByClass("content-mod").first().getElementsByClass("nodo");

            for (Element element : elements) {
                try {
                    Element aElement = element.getElementsByTag("a").get(0);
                    String title = aElement.attr("title");
                    String articleUrl = aElement.attr("href");

                    String imageUrl = element.getElementsByTag("img").get(0).attr("src").replace("_4", "_1");
                    String description = element.getElementsByClass("entradilla").html();

                    Date publicationDate = CalendarUtils.parsePublicationDateFromUrl(articleUrl, true);

                    febNews.add(new News(title, description, imageUrl, articleUrl, News.NewsKind.FEB_ARTICLE,
                            publicationDate));
                } catch (Exception e) {
                    Log.e(MainActivity.DEBUG_APP_NAME, "Could not parse a news entry", e);
                }
            }
        } catch (Exception e) {
            Log.e(MainActivity.DEBUG_APP_NAME, "Error parsing feb news", e);
        }

        return febNews;
    }

    @Override
    public String getArticleText(String newsArticleHTML) {
        String articleText = "";

        try {
            String brs = "<br/><br/>";

            Document htmlDocument = parseHTMLData(newsArticleHTML);

            Element titleElement = htmlDocument.getElementsByClass("titulo").get(0);
            articleText += "<h3>" + titleElement.html() + "</h3>";

            Element descriptionElement = htmlDocument.getElementsByClass("entradilla").get(0);
            articleText += descriptionElement.outerHtml() + brs;

            Element articleElement = htmlDocument.getElementsByClass("cuerpo").get(0);
            Elements psElements = articleElement.getElementsByTag("p");

            if (CollectionUtils.isEmpty(psElements)) {
                articleText += articleElement.outerHtml();
            } else {
                for (Element psElement : psElements) {
                    articleText += psElement.html() + brs;
                }
            }
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
