package com.leboro.util.mapper.api;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.joda.time.DateTime;

import com.google.common.collect.Lists;
import com.leboro.MainActivity;
import com.leboro.model.api.live.game.LiveGameInfo;
import com.leboro.model.api.live.game.statistic.player.GamePlayerStatisticHolder;
import com.leboro.model.api.live.game.statistic.team.GameTeamStatisticHolder;
import com.leboro.model.game.GameResult;
import com.leboro.model.game.live.LiveGame;
import com.leboro.model.game.live.playbyplay.PlayByPlay;
import com.leboro.model.game.live.playbyplay.PlayByPlay.PlayLine;
import com.leboro.model.game.live.statistic.GameStatistics;
import com.leboro.model.game.live.statistic.player.PlayerStatistic;
import com.leboro.model.game.live.statistic.player.PlayerStatisticBuilder;
import com.leboro.model.game.live.statistic.team.TeamStatistic;
import com.leboro.model.game.live.statistic.team.TeamStatisticBuilder;
import com.leboro.model.game.live.status.CurrentGameStatus;
import com.leboro.model.team.Team;
import com.leboro.service.ApplicationServiceProvider;
import com.leboro.util.calendar.CalendarUtils;
import com.leboro.util.exception.InstanceNotFoundException;
import com.leboro.util.exception.NoLiveDataException;

import android.util.Log;

public class LiveGameMapper {

    public static LiveGame toLiveGame(LiveGameInfo liveGameInfo) throws NoLiveDataException {
        Log.d(MainActivity.DEBUG_APP, "Parsing game [" + liveGameInfo.getGameId() + "]");

        com.leboro.model.api.live.game.header.Team apiHomeTeam = liveGameInfo.getHeader().getTeams().get(0);
        com.leboro.model.api.live.game.header.Team apiAwayTeam = liveGameInfo.getHeader().getTeams().get(1);

        Team homeTeam = mapTeam(apiHomeTeam);
        Team awayTeam = mapTeam(apiAwayTeam);

        PlayByPlay playByPlay = toPlayByPlay(liveGameInfo.getPlayByPlay(), homeTeam, awayTeam);

        DateTime gameStartDate = getStartDate(liveGameInfo, liveGameInfo.getHeader().getGameInfoStartDateAndTime());
        GameResult gameResult = new GameResult(gameStartDate, homeTeam, awayTeam, apiHomeTeam.getScore(),
                apiAwayTeam.getScore());

        CurrentGameStatus currentGameStatus = new CurrentGameStatus(liveGameInfo.getHeader().getQuarter(),
                liveGameInfo.getHeader().getCurrentGameTime());

        return new LiveGame(liveGameInfo.getGameId(), gameResult, playByPlay, toGameTeamStatistics(liveGameInfo
                .getGameTeamStatisticHolder(), liveGameInfo.getGamePlayerStatisticHolder()), currentGameStatus);
    }

    private static DateTime getStartDate(LiveGameInfo liveGameInfo, String gameInfoStartDateAndTime) {
        DateTime dateTime = CalendarUtils.parseApiGameInfoStartDate(gameInfoStartDateAndTime);

        if (dateTime == null) {
            try {
                dateTime = ApplicationServiceProvider.getGameService().getGameInfoFromCacheByGameId(liveGameInfo.getGameId());
            } catch (InstanceNotFoundException e) {
                Log.e(MainActivity.DEBUG_APP, "Cannot found game id in cache [" + liveGameInfo.getGameId() + "]", e);
                if ("FINAL".equalsIgnoreCase(liveGameInfo.getHeader().getCurrentGameTime())) {
                    Log.d(MainActivity.DEBUG_APP, "Cannot found game information, but it has a finished status");
                    DateTime.now().minus(100L);
                    // Create a before time since it's a finished game, ergo it already started
                }
                Log.d(MainActivity.DEBUG_APP, "No information about this game at all, returning current timestamp.");
                return DateTime.now();
            }
        }

        return dateTime;
    }

    public static PlayByPlay toPlayByPlay(com.leboro.model.api.live.game.playbyplay.PlayByPlay apiPlayByPlay,
            Team homeTeam, Team awayTeam) {
        List<PlayLine> playLines = mapPlayByPlayLines(apiPlayByPlay.getLines(), homeTeam, awayTeam);
        return new PlayByPlay(playLines);
    }

    private static GameStatistics toGameTeamStatistics(GameTeamStatisticHolder apiGameTeamStatisticHolder,
            GamePlayerStatisticHolder apiGamePlayerStatisticHolder) throws NoLiveDataException {
        if (!CollectionUtils.isEmpty(apiGameTeamStatisticHolder.getGameStatistics())) {
            com.leboro.model.api.live.game.statistic.team.TeamStatistic apiHomeTeamStatistic = apiGameTeamStatisticHolder
                    .getGameStatistics().get(0);
            com.leboro.model.api.live.game.statistic.team.TeamStatistic apiAwayTeamStatistic = apiGameTeamStatisticHolder
                    .getGameStatistics().get(1);

            List<com.leboro.model.api.live.game.statistic.player.PlayerStatistic> apiHomePlayerStatistics = apiGamePlayerStatisticHolder
                    .getGameStatistics().get(0).getTeamPlayersStatistics();
            List<com.leboro.model.api.live.game.statistic.player.PlayerStatistic> apiAwayPlayerStatistics = apiGamePlayerStatisticHolder
                    .getGameStatistics().get(1).getTeamPlayersStatistics();

            return new GameStatistics(mapTeamStatistic(apiHomeTeamStatistic), mapTeamStatistic(apiAwayTeamStatistic),
                    mapPlayerStatistics(apiHomePlayerStatistics), mapPlayerStatistics(apiAwayPlayerStatistics));
        } else {
            throw new NoLiveDataException();
        }
    }

    private static List<PlayerStatistic> mapPlayerStatistics(
            List<com.leboro.model.api.live.game.statistic.player.PlayerStatistic> teamPlayerStatistics) {
        List<PlayerStatistic> playerStatistics = Lists.newArrayList();

        for (com.leboro.model.api.live.game.statistic.player.PlayerStatistic teamPlayerStatistic : teamPlayerStatistics) {
            playerStatistics.add(mapPlayerStatistic(teamPlayerStatistic));
        }

        return playerStatistics;
    }

    private static PlayerStatistic mapPlayerStatistic(
            com.leboro.model.api.live.game.statistic.player.PlayerStatistic homeTeamPlayerStatistic) {
        return new PlayerStatisticBuilder().setPoints(homeTeamPlayerStatistic.getPoints()).setAssists
                (homeTeamPlayerStatistic.getAssists()).setDefensiveRebounds(homeTeamPlayerStatistic
                .getDefensiveRebounds()).setOffensiveRebounds(homeTeamPlayerStatistic.getOffensiveRebounds())
                .setFieldGoalsAttempted(homeTeamPlayerStatistic.getFieldGoalsAttempted()).setFieldGoalsMade
                        (homeTeamPlayerStatistic.getFieldGoalsMade()).setThreePointsAttempted(homeTeamPlayerStatistic
                        .getThreePointersAttempted()).setThreePointsMade(homeTeamPlayerStatistic.getThreePointersMade
                        ()).setFreeThrowsAttempted(homeTeamPlayerStatistic.getFreeThrowsAttempted())
                .setFreeThrowsMade(homeTeamPlayerStatistic.getFreeThrowsMade())
                .setSteals(homeTeamPlayerStatistic.getSteals())
                .setTurnovers(homeTeamPlayerStatistic.getTurnovers()).setPlayerName(homeTeamPlayerStatistic
                        .getPlayerName()).setPlayerNumber(homeTeamPlayerStatistic.getPlayerNumber())
                .setPlayerImageUrl(homeTeamPlayerStatistic.getPlayerImageUrl()).createPlayerStatistic();
    }

    private static TeamStatistic mapTeamStatistic(
            com.leboro.model.api.live.game.statistic.team.TeamStatistic apiTeamStatistic) {
        return new TeamStatisticBuilder().setPoints(apiTeamStatistic.getPoints()).setAssists
                (apiTeamStatistic.getAssists()).setDefensiveRebounds(apiTeamStatistic
                .getDefensiveRebounds()).setOffensiveRebounds(apiTeamStatistic.getOffensiveRebounds())
                .setFieldGoalsAttempted(apiTeamStatistic.getFieldGoalsAttempted()).setFieldGoalsMade
                        (apiTeamStatistic.getFieldGoalsMade()).setThreePointersAttempted(apiTeamStatistic
                        .getThreePointersAttempted()).setThreePointersMade(apiTeamStatistic.getThreePointersMade
                        ()).setFreeThrowsAttempted(apiTeamStatistic.getFreeThrowsAttempted())
                .setFreeThrowsMade(apiTeamStatistic.getFreeThrowsMade())
                .setSteals(apiTeamStatistic.getSteals())
                .setTurnovers(apiTeamStatistic.getTurnovers()).createTeamStatistic();
    }

    private static List<PlayLine> mapPlayByPlayLines(List<com.leboro.model.api.live.game.playbyplay.PlayByPlay.Line>
            apiPlayByPlayLines, Team homeTeam, Team awayTeam) {
        List<PlayLine> playLines = Lists.newArrayList();

        for (com.leboro.model.api.live.game.playbyplay.PlayByPlay.Line line : apiPlayByPlayLines) {
            playLines.add(mapPlayByPlayLine(line, homeTeam, awayTeam));
        }

        Collections.sort(playLines);
        return playLines;
    }

    private static PlayLine mapPlayByPlayLine(com.leboro.model.api.live.game.playbyplay.PlayByPlay.Line line,
            Team homeTeam,
            Team awayTeam) {
        Team team = null;
        if (line.getTeam() == 1) {
            team = homeTeam;
        } else if (line.getTeam() == 2) {
            team = awayTeam;
        }

        return new PlayLine(line.getOrder(), line.getText(), team, line.getTime(), line.getHomeScore(),
                line.getAwayScore(), line.getQuarter());
    }

    private static Team mapTeam(com.leboro.model.api.live.game.header.Team apiTeam) {
        String name = apiTeam.getName();
        String logoUrl = apiTeam.getLogoUrl();
        return new Team(name, logoUrl);
    }
}
