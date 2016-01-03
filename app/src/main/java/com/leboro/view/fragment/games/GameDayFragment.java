package com.leboro.view.fragment.games;

import java.util.Collections;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;

import com.leboro.R;
import com.leboro.model.game.GameDay;
import com.leboro.model.game.GameDayInfo;
import com.leboro.model.game.GameResult;
import com.leboro.service.ApplicationServiceProvider;
import com.leboro.util.cache.GameDayCacheManager;
import com.leboro.view.adapters.games.GameDayListAdapter;
import com.leboro.view.listeners.DataLoadedListener;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class GameDayFragment extends Fragment implements DataLoadedListener<GameDay> {

    private View mView;

    private static final String ARG_SECTION_NUMBER = "section_number";

    private GameDay gameDay;

    private GameDayListAdapter gameDayListAdapter;

    private int pagerPosition;

    private final static ThreadPoolExecutor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(10, 10, 3, TimeUnit
            .SECONDS, new ArrayBlockingQueue<Runnable>(10));

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
        pagerPosition = getArguments().getInt(ARG_SECTION_NUMBER);

        initializeAdapter();
        initializeGameDayData();

        return mView;
    }

    private void initializeAdapter() {
        ListView gamesList = (ListView) mView.findViewById(R.id.gameDayListView);
        gameDayListAdapter = new GameDayListAdapter(mView.getContext(), R.layout.game_day_row, Collections
                .<GameResult>emptyList());
        gamesList.setAdapter(gameDayListAdapter);
    }

    private void initializeGameDayData() {
        final GameDayInfo gameDayInfo = GameDayCacheManager.getGameDayInfo();
        gameDay = gameDayInfo.getGameDays().get(pagerPosition - 1);
        if (CollectionUtils.isEmpty(gameDay.getGames())) {
            final DataLoadedListener dataLoadedListener = this;
            THREAD_POOL_EXECUTOR.submit(new Runnable() {
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
    }

    private void refreshView() {
        View loadingView = mView.findViewById(R.id.gameDayFragmentLoadingFrame);
        loadingView.setVisibility(View.GONE);

        final GameDayInfo gameDayInfo = GameDayCacheManager.getGameDayInfo();
        gameDay = gameDayInfo.getGameDays().get(pagerPosition - 1);
        gameDayListAdapter.setGameResultsAndNotify(gameDay.getGames());
    }

    @Override
    public void onDataLoaded() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                refreshView();
            }
        });
    }

    @Override
    public void onDataLoaded(GameDay data) {
        // noop
    }
}
