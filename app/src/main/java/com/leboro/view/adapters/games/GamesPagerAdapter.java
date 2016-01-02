package com.leboro.view.adapters.games;

import org.apache.commons.collections4.CollectionUtils;

import com.leboro.MainActivity;
import com.leboro.R;
import com.leboro.model.game.GameDay;
import com.leboro.model.game.GameDayInfo;
import com.leboro.service.ApplicationServiceProvider;
import com.leboro.util.Constants;
import com.leboro.util.cache.GameDayCacheManager;
import com.leboro.view.fragment.games.GameDayFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

public class GamesPagerAdapter extends FragmentPagerAdapter {

    public GamesPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        GameDayInfo gameDayInfo = GameDayCacheManager.getGameDayInfo();

        GameDay gameDay = GameDayCacheManager.getGameDayInfo().getGameDays().get(position);
        if (CollectionUtils.isEmpty(gameDay.getGames())) {
            ApplicationServiceProvider.getStatisticsService()
                    .refreshGameInfo(gameDay.getId(), gameDayInfo.getKind(), gameDayInfo.getSeason());
        }

        GameDayFragment gameDayFragment = GameDayFragment.newInstance(position + 1);
        gameDayFragment.getArguments()
                .putParcelable(Constants.BUNDLE_ARG_GAME_DAY, gameDayInfo.getGameDays().get(position));

        return gameDayFragment;
    }

    @Override
    public int getCount() {
        return GameDayCacheManager.getGameDayInfo().getGameDays().size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return MainActivity.context.getString(R.string.game_list_bar_title) + " " + position;
    }
}