package com.leboro.view.fragment.teaminfo.roster;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.leboro.MainActivity;
import com.leboro.R;
import com.leboro.model.team.info.TeamInfo;
import com.leboro.model.team.info.roster.TeamRoster;
import com.leboro.service.ApplicationServiceProvider;
import com.leboro.util.cache.ApplicationCacheManager;
import com.leboro.util.exception.InstanceNotFoundException;
import com.leboro.view.adapters.teaminfo.roster.TeamRosterListAdapter;
import com.leboro.view.fragment.LoadableFragment;
import com.leboro.view.fragment.teaminfo.TeamsInfoFragment;
import com.leboro.view.listeners.CacheDataLoadedListener;

import java.util.List;

public class TeamRosterFragment extends LoadableFragment implements CacheDataLoadedListener {

    private View mView;

    private TeamInfo teamInfo;

    private ListView teamRosterList;

    private TeamRosterListAdapter teamRosterListAdapter;

    private List<TeamRoster> teamRoster;

    private final CacheDataLoadedListener dataLoadedListener = this;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.teams_roster_fragment, container, false);

        teamInfo = getArguments().getParcelable(TeamsInfoFragment.TEAM_INFO_PARCELABLE_NAME);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ApplicationServiceProvider.getTeamInfoService().getTeamRosterAndUpdateCache
                            (teamInfo.getId(), dataLoadedListener);
                } catch (InstanceNotFoundException e) {
                    Log.e(MainActivity.DEBUG_APP_NAME, "Error obtaining team info", e);
                }
            }
        }).start();

        return mView;
    }

    @Override
    protected void updateActionAndNavigationBar() {
        ((MainActivity) getActivity()).setActionBarTitle(teamInfo.getName());
        MainActivity.navigationView.setCheckedItem(R.id.nav_teams_info);
    }

    @Override
    public void onDataLoadedIntoCache() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    teamRoster = ApplicationCacheManager.getTeamInfo(teamInfo.getId()).getTeamRoster();
                    initializeView();
                    removeLoadingLayoutAndShowResource(mView, R.id.teamRosterListView);
                    teamRosterListAdapter.updateDataAndNotify(teamRoster);
                } catch (InstanceNotFoundException e) {
                    Log.e(MainActivity.DEBUG_APP_NAME, "Error caching team info", e);
                }
            }
        });
    }

    private void initializeView() {
        initializeViews();
        initializeAdapters();
        initializeEvents();
    }

    private void initializeViews() {
        teamRosterList = (ListView) mView.findViewById(R.id.teamRosterListView);
    }

    private void initializeAdapters() {
        teamRosterListAdapter = new TeamRosterListAdapter(mView.getContext(), R.layout.team_roster_row,
                teamRoster);
        teamRosterList.setAdapter(teamRosterListAdapter);
    }

    private void initializeEvents() {
        teamRosterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO show player detailed info
            }
        });
    }
}
