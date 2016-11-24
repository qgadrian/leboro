package com.leboro.view.fragment.standings;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.leboro.MainActivity;
import com.leboro.R;
import com.leboro.model.api.standing.PlayerStanding;
import com.leboro.service.ApplicationServiceProvider;
import com.leboro.util.cache.ApplicationCacheManager;
import com.leboro.util.html.HTMLHelper;
import com.leboro.view.adapters.standing.StandingListAdapter;
import com.leboro.view.fragment.LoadableFragment;
import com.leboro.view.listeners.CacheDataLoadedListener;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.List;

public class StandingFragment extends LoadableFragment implements CacheDataLoadedListener {

    private View mView;

    private static final String ARG_SECTION_NUMBER = "section_number";

    private StandingListAdapter standingListAdapter;

    private int pagerPosition;

    public StandingFragment() {
    }

    public static StandingFragment newInstance(int sectionNumber) {
        StandingFragment fragment = new StandingFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.standing_fragment, container, false);
        pagerPosition = getArguments().getInt(ARG_SECTION_NUMBER) - 1;

        initializeAdapter();
        initializeStandingData(pagerPosition);

        return mView;
    }

    private void initializeAdapter() {
        ListView standingList = (ListView) mView.findViewById(R.id.standingListView);
        standingListAdapter = new StandingListAdapter(mView.getContext(), R.layout.standing_row, Collections
                .<PlayerStanding>emptyList());
        standingList.setAdapter(standingListAdapter);
    }

    private void initializeStandingData(int position) {
        try {
            final HTMLHelper.StandingType standingType = HTMLHelper.StandingType.getFromPosition(position);

            final List<PlayerStanding> playerStandings = ApplicationCacheManager
                    .getPlayerStandings(standingType.getId());

            if (CollectionUtils.isEmpty(playerStandings)) {
                final CacheDataLoadedListener dataLoadedListener = this;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ApplicationServiceProvider.getStandingService()
                                .getPlayerStandings(standingType.getId(), dataLoadedListener);
                    }
                }).start();
            } else {
                refreshView(standingType.getPosition());
            }
        } catch (Exception e) {
            Log.e(MainActivity.DEBUG_APP_NAME, "Could not get standing info for position [" + position + "]", e);
        }
    }

    private void refreshView(int pagerPosition) {
        removeLoadingLayout(mView);

        final HTMLHelper.StandingType standingType = HTMLHelper.StandingType.getFromPosition(pagerPosition);

        try {
            List<PlayerStanding> playerStandings = ApplicationCacheManager.getPlayerStandings(standingType.getId());
            standingListAdapter.setPlayerStandingsAndNotify(playerStandings);
        } catch (Exception e) {
            Log.e(MainActivity.DEBUG_APP_NAME, "Could not get standing info for type [" + standingType.getId() + "]",
                    e);
        }
    }

    @Override
    public void onDataLoadedIntoCache() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                refreshView(pagerPosition);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.navigation_drawer_standings));
    }
}
