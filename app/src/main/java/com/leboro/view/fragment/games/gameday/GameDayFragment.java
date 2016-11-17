package com.leboro.view.fragment.games.gameday;

import java.util.Collections;

import org.apache.commons.collections4.CollectionUtils;

import com.leboro.MainActivity;
import com.leboro.R;
import com.leboro.model.game.GameDay;
import com.leboro.model.game.GameDayInfo;
import com.leboro.model.game.GameInfo;
import com.leboro.service.ApplicationServiceProvider;
import com.leboro.util.Constants;
import com.leboro.util.cache.ApplicationCacheManager;
import com.leboro.util.exception.InstanceNotFoundException;
import com.leboro.view.adapters.games.GameDayListAdapter;
import com.leboro.view.fragment.LoadableFragment;
import com.leboro.view.fragment.games.live.game.LiveGameViewFragment;
import com.leboro.view.helper.gameday.GameDayHelper;
import com.leboro.view.listeners.CacheDataLoadedListener;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class GameDayFragment extends LoadableFragment implements CacheDataLoadedListener {

    private View mView;

    private static final String ARG_SECTION_NUMBER = "section_number";

    private GameDay gameDay;

    private GameDayListAdapter gameDayListAdapter;

    private ListView gamesList;

    private int pagerPosition;

    public GameDayFragment() {
    }

    public static GameDayFragment newInstance(int sectionNumber) {
        GameDayFragment fragment = new GameDayFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.game_day_fragment, container, false);
        pagerPosition = getArguments().getInt(ARG_SECTION_NUMBER) - 1;

        initializeAdapter();
        initializeGameDayData(pagerPosition);
        initializeListeners();

        return mView;
    }

    private void initializeAdapter() {
        gamesList = (ListView) mView.findViewById(R.id.gameDayListView);
        gameDayListAdapter = new GameDayListAdapter(mView.getContext(), R.layout.game_day_row, Collections
                .<GameInfo>emptyList());
        gamesList.setAdapter(gameDayListAdapter);
    }

    private void initializeGameDayData(int position) {
        try {
            final GameDayInfo gameDayInfo = ApplicationCacheManager.getGameDayInfo();

            gameDay = gameDayInfo.getGameDays().get(position);

            if (CollectionUtils.isEmpty(gameDay.getGames())) {
                final CacheDataLoadedListener dataLoadedListener = this;
                AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
                    @Override
                    public void run() {
                        ApplicationServiceProvider.getStatisticsService()
                                .refreshGameInfo(gameDay.getId(), gameDayInfo.getKind(), gameDayInfo.getSeason(),
                                        dataLoadedListener);
                    }
                });
            } else {
                refreshView();
            }
        } catch (InstanceNotFoundException e) {
            Log.e(MainActivity.DEBUG_APP_NAME, "Could not get game info for game day", e);
        }
    }

    private void initializeListeners() {
        gamesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GameInfo gameInfo = gameDayListAdapter.getItem(position);
                if (GameDayHelper.isStarted(gameInfo.getGameResult())) {
                    long gameId = gameInfo.getGameId();
                    Bundle bundle = new Bundle(1);
                    bundle.putLong(Constants.BUNDLE_ARG_GAME_ID, gameId);
                    LiveGameViewFragment liveGameViewFragment = new LiveGameViewFragment();
                    liveGameViewFragment.setArguments(bundle);

                    String liveGameViewTitle = getString(R.string.navigation_game_view_title,
                            gameInfo.getGameResult().getHomeTeam().getName(),
                            gameInfo.getGameResult().getAwayTeam().getName());

                    ((MainActivity) getActivity()).fragmentTransition(liveGameViewFragment, liveGameViewTitle);
                } else {
                    Toast.makeText(getContext(), R.string.toast_game_not_started_yet, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void refreshView() {
        removeLoadingLayout(mView);

        final GameDayInfo gameDayInfo;
        try {
            gameDayInfo = ApplicationCacheManager.getGameDayInfo();
            gameDay = gameDayInfo.getGameDays().get(pagerPosition);
            gameDayListAdapter.setGameResultsAndNotify(gameDay.getGames());
        } catch (InstanceNotFoundException e) {
            Log.e(MainActivity.DEBUG_APP_NAME, "Could not get game info for game day", e);
        }
    }

    @Override
    public void onDataLoadedIntoCache() {
        if (isVisible()) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    refreshView();
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.navigation_drawer_games));
    }
}
