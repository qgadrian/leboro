package com.leboro.service.teaminfo.impl;


import com.leboro.model.team.info.TeamInfo;
import com.leboro.model.team.info.roster.TeamRoster;
import com.leboro.service.teaminfo.TeamInfoService;
import com.leboro.util.Constants;
import com.leboro.util.cache.ApplicationCacheManager;
import com.leboro.util.exception.InstanceNotFoundException;
import com.leboro.util.parser.BaseParser;
import com.leboro.util.parser.teaminfo.TeamInfoParser;
import com.leboro.util.properties.PropertiesHelper;
import com.leboro.view.helper.http.HttpHelper;
import com.leboro.view.listeners.CacheDataLoadedListener;

import org.apache.commons.collections4.CollectionUtils;
import org.jsoup.nodes.Document;

import java.util.List;

public class TeamInfoServiceImpl implements TeamInfoService {

    @Override
    public void getTeamInfos(CacheDataLoadedListener dataLoadedListener) {
        if (!ApplicationCacheManager.hasTeamsInfoCacheData()) {
            String teamsInfoHTML = HttpHelper.getHtmlFromSimpleHttpRequestUsingProperties
                    (Constants.TEAMS_INFO_URL_PROP);
            Document teamsInfoData = BaseParser.parseHTMLData(teamsInfoHTML);
            List<TeamInfo> teamInfos = TeamInfoParser.getTeamsInfoFromData(teamsInfoData);
            ApplicationCacheManager.setTeamInfos(teamInfos);
        }

        dataLoadedListener.onDataLoadedIntoCache();
    }

    @Override
    public void getTeamRosterAndUpdateCache(long teamInfoId, CacheDataLoadedListener
            dataLoadedListener) throws InstanceNotFoundException {
        TeamInfo teamInfo = ApplicationCacheManager.getTeamInfo(teamInfoId);

        if (CollectionUtils.isEmpty(teamInfo.getTeamRoster())) {
            String url = PropertiesHelper.getProperty(Constants.TEAM_ROSTER_URL_PROP) + teamInfo
                    .getId();

            String teamRosterInfoHTML = HttpHelper.getHtmlFromSimpleHttpRequest(url);
            Document teamRosterInfoData = BaseParser.parseHTMLData(teamRosterInfoHTML);
            List<TeamRoster> teamRoster = TeamInfoParser.getTeamsInfoWithRosterFromData(teamRosterInfoData);
            teamInfo.setTeamRoster(teamRoster);
            ApplicationCacheManager.updateTeamInfo(teamInfo);
        }

        dataLoadedListener.onDataLoadedIntoCache();
    }

}
