package com.leboro.util.parser.teaminfo;


import com.google.common.collect.Lists;
import com.leboro.model.team.info.TeamInfo;
import com.leboro.model.team.info.roster.TeamRoster;
import com.leboro.util.Constants;
import com.leboro.util.parser.BaseParser;
import com.leboro.util.properties.PropertiesHelper;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Collections;
import java.util.List;

import static com.leboro.model.team.info.roster.TeamRoster.RosterKind.COACH;
import static com.leboro.model.team.info.roster.TeamRoster.RosterKind.PLAYER;

public class TeamInfoParser extends BaseParser {

    public static List<TeamInfo> getTeamsInfoFromData(Document teamsInfoData) {
        List<TeamInfo> teamInfos = Lists.newArrayList();

        Element teamsNodeParent = teamsInfoData.getElementsByClass("content-mod").first();

        for (Element teamElement : teamsNodeParent.getElementsByClass("nodo")) {
            Element teamBasicInfoElement = teamElement.getElementsByClass("equipo").first();

            String teamLogoUrl = teamBasicInfoElement.getElementsByTag("img").first().attr("src");

            Element teamNameElement = teamBasicInfoElement.getElementsByTag("a").first();
            String teamName = teamNameElement.text();
            String hrefString = teamNameElement.attr("href");
            long id = Long.valueOf(hrefString.substring(hrefString.indexOf("?i=") + 3));

            Element teamArenaInfoElement = teamElement.getElementsByClass("datosJuego").first();

            Element teamArenaNameElement = teamArenaInfoElement.getElementsByClass("nombreCancha").first();
            teamArenaNameElement.children().first().remove();
            String teamArenaName = teamArenaNameElement.text();

            Element teamArenaAddressElement = teamArenaInfoElement.getElementsByClass
                    ("direccionCancha").first();
            teamArenaAddressElement.children().first().remove();
            String teamArenaAddress = teamArenaAddressElement.text();

            TeamInfo teamInfo = new TeamInfo(teamName, teamLogoUrl, teamArenaName,
                    teamArenaAddress, id);
            teamInfos.add(teamInfo);
        }

        Collections.sort(teamInfos);
        return teamInfos;
    }

    public static List<TeamRoster> getTeamsInfoWithRosterFromData(Document teamRosterInfoData) {

        String rosterImagePrefix = PropertiesHelper.getProperty(Constants
                .TEAM_ROSTER_IMAGE_PREFIX_URL_PROP);

        List<TeamRoster> teamRoster = Lists.newArrayList();

        Element coachElement = teamRosterInfoData.getElementsByClass("tablaFichaEntrenador")
                .first().getElementsByClass("fichaEntrenador").first();
        String coachImageUrl = rosterImagePrefix + coachElement.getElementById
                ("fotoEntrenadorImage").attr("src");
        String coachName = coachElement.getElementById("nombreEntrenadorLabel").text();

        TeamRoster coachRoster = new TeamRoster(COACH, coachName, coachImageUrl);

        teamRoster.add(coachRoster);

        for (Element playerTupleElement : teamRosterInfoData.select
                ("table#componentesDataList > tbody > tr")) {
            for (Element playerElement : playerTupleElement.getElementsByTag("td")) {
                if (!playerElement.children().isEmpty()) {
                    String playerImageUrl = rosterImagePrefix + playerElement.getElementsByClass
                            ("foto").first().attr("src");

                    String playerName = playerElement.getElementsByClass("nombre").first().text();
                    String position = playerElement.select("ul > li.puesto > span").text();
                    String country = playerElement.select("ul > li.nacionalidad > span").text();
                    int height = Integer.valueOf(playerElement.select("ul > li.altura > span")
                            .text());

                    TeamRoster playerRoster = new TeamRoster(PLAYER, playerName, playerImageUrl,
                            position, null, country, height);
                    teamRoster.add(playerRoster);
                }
            }
        }

        return teamRoster;
    }
}
