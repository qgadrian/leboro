package com.leboro.util.parser.classification;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.text.WordUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.common.collect.Lists;
import com.leboro.model.classification.Position;
import com.leboro.model.team.Team;
import com.leboro.util.parser.BaseParser;

public class ClassificationParser extends BaseParser {

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

}
