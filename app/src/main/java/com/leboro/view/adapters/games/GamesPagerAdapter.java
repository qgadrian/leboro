package com.leboro.view.adapters.games;

import com.leboro.MainActivity;
import com.leboro.R;
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
        return GameDayFragment.newInstance(position + 1);
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