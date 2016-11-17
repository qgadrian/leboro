package com.leboro.util.parser.standing;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.common.collect.Lists;
import com.leboro.MainActivity;
import com.leboro.model.api.standing.PlayerStanding;
import com.leboro.util.parser.BaseParser;

import android.util.Log;

public class StandingParser extends BaseParser {

    public static List<PlayerStanding> getPlayerStandings(Document htmlInfoData) {
        List<PlayerStanding> playerStandings = Lists.newArrayList();

        try {
            Elements standingsTables = htmlInfoData.getElementsByClass("tabla-estadistica");

            if (CollectionUtils.isEmpty(standingsTables)) {
                Log.d(MainActivity.DEBUG_APP_NAME, "Couldnt found any standing table");
                return playerStandings;
            }

            if (standingsTables.size() > 1) {
                Log.d(MainActivity.DEBUG_APP_NAME, "Found more than one standing table retrieving the first one");
            }

            Element standingTable = standingsTables.first();

            for (Element playerStandingRow : standingTable.getElementsByTag("tbody").first().getElementsByTag("tr")) {
                String playerImageUrl = playerStandingRow.getElementsByTag("img").first().attr("src");

                Element playerNameElement = playerStandingRow.getElementsByClass("nombre").first();
                String playerName = playerNameElement.getElementsByTag("a").first().text();

                Element teamNameElement = playerStandingRow.getElementsByClass("equipo").first();
                String teamName = teamNameElement.getElementsByTag("a").first().text();

                int numberOfGames = Integer
                        .parseInt(playerStandingRow.getElementsByClass("numeroPartidos").first().text());

                double totalValue = getTotalValue(playerStandingRow);
                double averageValue = getAverageValue(playerStandingRow);

                PlayerStanding playerStanding = new PlayerStanding(playerImageUrl, playerName, teamName, totalValue,
                        numberOfGames, averageValue);
                playerStandings.add(playerStanding);
            }

            Collections.sort(playerStandings);
        } catch (Exception e) {
            Log.d(MainActivity.DEBUG_APP_NAME, "Error parsing standing html data", e);
        }

        return playerStandings;
    }

    // Those geniuses made me write shitty code
    private static double getAverageValue(Element playerStandingRow) {
        double averageValue;
        try {
            averageValue = Double.parseDouble(
                    playerStandingRow.getElementsByClass("media").first().text().replace(",", "."));
        } catch (NumberFormatException e) {
            String[] timeString = playerStandingRow.getElementsByClass("media").first().text().split(":");
            double minutes = Double.parseDouble(timeString[0]);
            double seconds = Double.parseDouble(timeString[1]);

            averageValue = minutes + (seconds / 60);
        }
        return averageValue;
    }

    private static double getTotalValue(Element playerStandingRow) {
        double totalValue;
        try {
            totalValue = Double.parseDouble(playerStandingRow.getElementsByClass("total").first().text());
        } catch (NumberFormatException e) {
            String[] timeString = playerStandingRow.getElementsByClass("total").first().text().split(":");
            double minutes = Double.parseDouble(timeString[0]);
            double seconds = Double.parseDouble(timeString[1]);

            totalValue = (minutes + (seconds / 60));
        }
        return totalValue;
    }

}
