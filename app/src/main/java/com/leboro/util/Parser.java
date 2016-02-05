package com.leboro.util;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.joda.time.DateTime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.common.collect.Lists;
import com.leboro.MainActivity;
import com.leboro.model.classification.Position;
import com.leboro.model.game.GameDay;
import com.leboro.model.game.GameDayInfo;
import com.leboro.model.game.GameResult;
import com.leboro.model.news.News;
import com.leboro.model.team.Team;
import com.leboro.util.calendar.CalendarUtils;
import com.leboro.util.sharedpreferences.SharedPreferencesHelper;

import android.content.SharedPreferences;

public class Parser {

    public static Document parseHTMLData(String stringToParse) {
        return Jsoup.parse(stringToParse);
    }

    public static Document parseHTMLAndSaveTokenData(String htmlAsString) {
        Document parsedHTML = Parser.parseHTMLData(htmlAsString);
        String _VIEWSTATE = parsedHTML.getElementsByAttributeValue("name", "__VIEWSTATE").get(0).val();
        String _EVENTVALIDATION = parsedHTML.getElementsByAttributeValue("name", "__EVENTVALIDATION").get(0).val();
        SharedPreferences.Editor sharedPrefsEditor = SharedPreferencesHelper.getDefaultSharedPreferencesEditor
                (MainActivity.context);
        sharedPrefsEditor.putString(Constants.VIEW_STATE_TOKEN_SHARED_PROP, _VIEWSTATE);
        sharedPrefsEditor.putString(Constants.EVENT_VALIDATION_TOKEN_SHARED_PROP, _EVENTVALIDATION);
        sharedPrefsEditor.commit();
        return parsedHTML;
    }

    public static GameDayInfo getGameInfoFromData(Document gameDayInfoData) {
        List<GameResult> gameResults = getGameInfos(gameDayInfoData);

        Elements elements = gameDayInfoData.getElementsByTag("select");

        int season = Integer.valueOf(elements.get(0).getElementsByAttribute("selected").val());
        int kind = Integer.valueOf(elements.get(1).getElementsByAttribute("selected").val());
        int current = Integer.valueOf(elements.get(2).getElementsByAttribute("selected").val());

        List<GameDay> gameDays = Lists.newArrayList();
        GameDay gameDay;
        for (Element element : elements.get(2).getElementsByTag("option")) {
            int gameDayId = Integer.valueOf(element.val());
            if (current == gameDayId) {
                gameDay = new GameDay(gameResults, gameDayId);
            } else {
                gameDay = new GameDay(null, gameDayId);
            }
            gameDays.add(gameDay);
        }

        return new GameDayInfo(gameDays, current, kind, season);
    }

    public static GameDay getGameDay(Document gameDayInfoData) {
        List<GameResult> gameResults = getGameInfos(gameDayInfoData);

        Elements gameDayElements = gameDayInfoData.getElementsByTag("select").get(2).getElementsByAttribute("selected");

        GameDay gameDay = null;
        for (Element element : gameDayElements) {
            int gameDayId = Integer.valueOf(element.val());
            gameDay = new GameDay(gameResults, gameDayId);
        }

        return gameDay;
    }

    private static List<GameResult> getGameInfos(Document gamesDocumment) {
        List<GameResult> gameResults = Lists.newArrayList();

        for (Element nodeElement : gamesDocumment.getElementsByClass("nodo")) {
            Element resultElement = nodeElement.getElementsByClass("row-resultado").get(0);
            Pair<String, String> homeTeamNameAndLogo = getTeamInfo(resultElement, "local");
            Pair<String, String> awayTeamNameAndLogo = getTeamInfo(resultElement, "visitante");
            Pair<Integer, Integer> gameResultHomeAndAway = getResultInfo(resultElement, "resultado");
            Team homeTeam = new Team(homeTeamNameAndLogo.getLeft(), homeTeamNameAndLogo.getRight());
            Team awayTeam = new Team(awayTeamNameAndLogo.getLeft(), awayTeamNameAndLogo.getRight());
            DateTime startDate = getGameDateInfo(nodeElement.getElementsByClass("fecha-label").get(0));
            GameResult gameResult = new GameResult(startDate, homeTeam, awayTeam,
                    gameResultHomeAndAway.getLeft(), gameResultHomeAndAway.getRight());
            gameResults.add(gameResult);
        }

        Collections.sort(gameResults);
        return gameResults;
    }

    private static DateTime getGameDateInfo(Element element) {
        return CalendarUtils.parseServerBuiltStringDate(element.text());
    }

    private static Pair<String, String> getTeamInfo(Element element, String classSelector) {
        String teamName = element.getElementsByClass(classSelector).get(0).getElementsByTag("a").get(0)
                .text();
        String logoUrl = element.getElementsByClass(classSelector).get(0).getElementsByTag("img").get(0)
                .attr("src");
        return Pair.of(teamName, logoUrl);
    }

    private static Pair<Integer, Integer> getResultInfo(Element element, String classSelector) {
        Element resultElement = element.getElementsByClass(classSelector).get(0);

        for (Element spanElement : resultElement.getElementsByTag("span")) {
            if (spanElement.text().contains("-")) {
                String[] resultsSplitted = spanElement.text().split("-");
                return Pair.of(Integer.valueOf(resultsSplitted[0].trim()), Integer.valueOf(resultsSplitted[1].trim()));
            }
        }

        throw new IllegalArgumentException("Cannot find result data to parse");
    }

    public static List<Position> getPositionsInfo(Elements elements) {
        List<Position> positions = Lists.newArrayList();

        for (Element element : elements) {
            positions.add(buildPositionFromElement(element));
        }

        Collections.sort(positions);
        return positions;
    }

    private static Position buildPositionFromElement(Element element) {
        Elements elements = element.children();
        int position = Integer.valueOf(elements.get(0).childNode(0).outerHtml());
        Team team = getTeamInfo(elements.get(1));
        int wonGames = Integer.valueOf(elements.get(3).childNode(0).outerHtml());
        int loseGames = Integer.valueOf(elements.get(4).childNode(0).outerHtml());
        int totalScored = Integer.valueOf(elements.get(5).childNode(0).outerHtml());
        int opponentTotalScored = Integer.valueOf(elements.get(6).childNode(0).outerHtml());
        int classificationPoints = Integer.valueOf(elements.get(7).childNode(0).outerHtml());
        int strike = Integer.valueOf(elements.get(8).childNode(0).outerHtml());

        return new Position(position, team, wonGames, loseGames, totalScored, opponentTotalScored,
                classificationPoints, strike);
    }

    private static Team getTeamInfo(Element element) {
        String teamName = WordUtils.capitalizeFully(element.getElementsByTag("a").get(0).text());
        String logoUrl = element.getElementsByTag("img").get(0).attr("src");
        return new Team(teamName, logoUrl);
    }

    public static List<News> getNews(Elements children) {
        List<News> news = Lists.newArrayList();

        for (Element element : children) {
            Element aElement = element.getElementsByTag("a").get(0);
            String title = aElement.attr("title");
            String articleUrl = aElement.attr("href");

            String imageUrl = element.getElementsByTag("img").get(0).attr("src").replace("_4", "_1");
            String description = element.getElementsByClass("entradilla").html();
            news.add(new News(title, description, imageUrl, articleUrl));
        }

        return news;
    }

    public static String getArticleText(String newsArticleHTML) {
        String articleText = "";
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

        return articleText;
    }
}
