package com.leboro.view.fragment.games.live;

import java.util.List;

import com.leboro.R;
import com.leboro.model.game.live.LiveData;
import com.leboro.model.game.live.LiveOverview;
import com.leboro.service.ApplicationServiceProvider;
import com.leboro.view.adapters.games.live.LiveGameDayOverviewAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class LiveGameDayOverviewFragment extends Fragment {

    private View mView;

    private static final String ARG_SECTION_NUMBER = "section_number";

    private LiveData liveData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.game_day_fragment, container, false);

        initializeViews();
        hideListHeader();

        return mView;
    }

    private void hideListHeader() {
        TextView headerTitle = (TextView) mView.findViewById(R.id.gameDayListHeaderTitle);
        headerTitle.setVisibility(View.GONE);
    }

    private void initializeViews() {
        ListView gamesList = (ListView) mView.findViewById(R.id.gameDayListView);
        LiveData liveData = ApplicationServiceProvider.getStatisticsService().getLiveData();
        List<LiveOverview> gameDayOverviews = liveData.getOverview().getCompetitions().get(0).getOverviews();
        if (gameDayOverviews != null) {
            gamesList.setAdapter(
                    new LiveGameDayOverviewAdapter(mView.getContext(), R.layout.game_day_row, gameDayOverviews));
        }
    }
}
