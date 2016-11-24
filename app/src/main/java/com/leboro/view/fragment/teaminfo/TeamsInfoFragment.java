package com.leboro.view.fragment.teaminfo;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.api.client.util.Lists;
import com.leboro.MainActivity;
import com.leboro.R;
import com.leboro.model.team.info.TeamInfo;
import com.leboro.service.ApplicationServiceProvider;
import com.leboro.util.Constants;
import com.leboro.util.cache.ApplicationCacheManager;
import com.leboro.view.adapters.teaminfo.TeamInfoListAdapter;
import com.leboro.view.fragment.LoadableFragment;
import com.leboro.view.fragment.teaminfo.roster.TeamRosterFragment;
import com.leboro.view.listeners.CacheDataLoadedListener;

import java.util.List;

public class TeamsInfoFragment extends LoadableFragment implements CacheDataLoadedListener {

    private View mView;

    private ListView teamsInfoList;

    private TeamInfoListAdapter teamsInfoListAdapter;

    private List<TeamInfo> teamInfos = Lists.newArrayList();

    public static String TEAM_INFO_PARCELABLE_NAME = "TeamInfo";

    private final CacheDataLoadedListener dataLoadedListener = this;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.teams_info_fragment, container, false);

        new Thread(new Runnable() {
            @Override
            public void run() {
                ApplicationServiceProvider.getTeamInfoService().getTeamInfos(dataLoadedListener);
            }
        }).start();

        return mView;
    }

    @Override
    public void onDataLoadedIntoCache() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                teamInfos = ApplicationCacheManager.getTeamInfos();
                initializeView();
                removeLoadingLayoutAndShowResource(mView, R.id.teamInfoListView);
                teamsInfoListAdapter.updateDataAndNotify(teamInfos);
            }
        });
    }

    @Override
    protected void updateActionAndNavigationBar() {
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.navigation_drawer_teams_info));
        MainActivity.navigationView.setCheckedItem(R.id.nav_teams_info);
    }

    private void initializeView() {
        initializeViews();
        initializeAdapters();
        initializeEvents();
    }

    private void initializeViews() {
        teamsInfoList = (ListView) mView.findViewById(R.id.teamInfoListView);
    }

    private void initializeAdapters() {
        teamsInfoListAdapter = new TeamInfoListAdapter(mView.getContext(), R.layout.team_info_row, teamInfos);
        teamsInfoList.setAdapter(teamsInfoListAdapter);
    }

    private void initializeEvents() {
        teamsInfoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TeamInfo teamInfo = teamsInfoListAdapter.getItem(position);

                Bundle bundle = new Bundle(1);
                bundle.putParcelable(Constants.BUNDLE_ARG_TEAM_INFO, teamInfo);

                TeamRosterFragment teamRosterFragment = new TeamRosterFragment();
                teamRosterFragment.setArguments(bundle);

                ((MainActivity) getActivity()).fragmentTransition(teamRosterFragment, getString(R.string.news_article_toolbar_title));
            }
        });
    }
}
