package com.leboro.util.parser.game;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.joda.time.DateTime;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.common.collect.Lists;
import com.leboro.model.game.GameDay;
import com.leboro.model.game.GameDayInfo;
import com.leboro.model.game.GameInfo;
import com.leboro.model.game.GameResult;
import com.leboro.model.team.Team;
import com.leboro.util.calendar.CalendarUtils;
import com.leboro.util.parser.BaseParser;

public class GameParser extends BaseParser {

    public static GameDayInfo getGameInfoFromData(Document gameDayInfoData) {
        List<GameInfo> gameInfos = getGameInfos(gameDayInfoData);

        Elements elements = gameDayInfoData.getElementsByTag("select");

        int season = Integer.valueOf(elements.get(0).getElementsByAttribute("selected").val());
        int kind = Integer.valueOf(elements.get(1).getElementsByAttribute("selected").val());
        int current = Integer.valueOf(elements.get(2).getElementsByAttribute("selected").val());

        List<GameDay> gameDays = Lists.newArrayList();
        GameDay gameDay;
        for (Element element : elements.get(2).getElementsByTag("option")) {
            int gameDayId = Integer.valueOf(element.val());
            if (current == gameDayId) {
                gameDay = new GameDay(gameInfos, gameDayId);
            } else {
                gameDay = new GameDay(null, gameDayId);
            }
            gameDays.add(gameDay);
        }

        return new GameDayInfo(gameDays, current, kind, season);
    }

    public static GameDay getGameDay(Document gameDayInfoData) {
        List<GameInfo> gameInfos = getGameInfos(gameDayInfoData);

        Elements gameDayElements = gameDayInfoData.getElementsByTag("select").get(2).getElementsByAttribute("selected");

        GameDay gameDay = null;
        for (Element element : gameDayElements) {
            int gameDayId = Integer.valueOf(element.val());
            gameDay = new GameDay(gameInfos, gameDayId);
        }

        return gameDay;
    }

    private static List<GameInfo> getGameInfos(Document gamesDocument) {
        List<GameInfo> gameInfos = Lists.newArrayList();

        for (Element nodeElement : gamesDocument.getElementsByClass("nodo")) {
            Element gameIdElement = nodeElement.getElementsByClass("wrap-resultado-info-ficha").get(0);
            long gameId = Long.valueOf(gameIdElement.attr("id"));
            Element resultElement = nodeElement.getElementsByClass("row-resultado").get(0);
            Pair<String, String> homeTeamNameAndLogo = getTeamInfo(resultElement, "local");
            Pair<String, String> awayTeamNameAndLogo = getTeamInfo(resultElement, "visitante");
            Pair<Integer, Integer> gameResultHomeAndAway = getResultInfo(resultElement, "resultado");
            Team homeTeam = new Team(homeTeamNameAndLogo.getLeft(), homeTeamNameAndLogo.getRight());
            Team awayTeam = new Team(awayTeamNameAndLogo.getLeft(), awayTeamNameAndLogo.getRight());
            DateTime startDate = getGameDateInfo(nodeElement.getElementsByClass("fecha-label").get(0));
            GameResult gameResult = new GameResult(startDate, homeTeam, awayTeam,
                    gameResultHomeAndAway.getLeft(), gameResultHomeAndAway.getRight());
            gameInfos.add(new GameInfo(gameId, gameResult));
        }

        Collections.sort(gameInfos);
        return gameInfos;
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

}
