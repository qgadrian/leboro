package com.leboro.view.fragment.games;

import org.apache.commons.collections4.CollectionUtils;

import com.leboro.R;
import com.leboro.model.game.GameDay;
import com.leboro.util.Constants;
import com.leboro.view.adapters.games.GameDayListAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class GameDayFragment extends Fragment {

    private View mView;

    private static final String ARG_SECTION_NUMBER = "section_number";

    private GameDay gameDay;

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
        gameDay = getArguments().getParcelable(Constants.BUNDLE_ARG_GAME_DAY);
        int position = getArguments().getInt(ARG_SECTION_NUMBER);

        initializeViews();
        updateListHeader(position);

        return mView;
    }

    private void updateListHeader(int position) {
        TextView headerTitle = (TextView) mView.findViewById(R.id.gameDayListHeaderTitle);
        headerTitle.setText(getContext().getString(R.string.game_list_bar_title) + " " + position);
    }

    private void initializeViews() {
        ListView gamesList = (ListView) mView.findViewById(R.id.gameDayListView);
        if (!CollectionUtils.isEmpty(gameDay.getGames())) {
            gamesList.setAdapter(new GameDayListAdapter(mView.getContext(), R.layout.game_day_row, gameDay.getGames()));
        }
    }
}
