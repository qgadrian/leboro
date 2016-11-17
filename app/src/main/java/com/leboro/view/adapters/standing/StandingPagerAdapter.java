package com.leboro.view.adapters.standing;

import com.leboro.MainActivity;
import com.leboro.service.ApplicationServiceProvider;
import com.leboro.util.html.HTMLHelper;
import com.leboro.view.fragment.standings.StandingFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

public class StandingPagerAdapter extends FragmentPagerAdapter {

    public StandingPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        return StandingFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return HTMLHelper.StandingType.values().length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        int pageTitleStringResourceId = ApplicationServiceProvider.getStandingService()
                .getPlayerStandingResourceId(position);
        return MainActivity.context.getString(pageTitleStringResourceId);
    }
}