package com.leboro.view.fragment.games;

import com.leboro.R;
import com.leboro.model.game.GameDayInfo;
import com.leboro.service.ApplicationServiceProvider;
import com.leboro.view.adapters.games.GamesPagerAdapter;
import com.leboro.view.helper.gameday.GameDayHelper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class GamesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.games_fragment, container, false);

        GameDayInfo gameDayInfo = ApplicationServiceProvider.getStatisticsService().getGameDayInfo();

        GamesPagerAdapter gamesPagerAdapter = new GamesPagerAdapter(getFragmentManager());

        ViewPager viewPager = (ViewPager) mView.findViewById(R.id.games_pager_container);
        viewPager.setAdapter(gamesPagerAdapter); // to set current item it's necessary to set adapter first
        viewPager.setCurrentItem(GameDayHelper.getGameDayCurrentPositionIndex(gameDayInfo));

        return mView;
    }

}
