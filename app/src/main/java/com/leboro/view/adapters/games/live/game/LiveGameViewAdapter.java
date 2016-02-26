package com.leboro.view.adapters.games.live.game;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.leboro.MainActivity;
import com.leboro.view.fragment.games.live.game.statistic.StatisticPageFragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

public class LiveGameViewAdapter extends FragmentPagerAdapter {

    List<Class<? extends StatisticPageFragment>> fragments = Lists.newArrayList();

    Map<Integer, StatisticPageFragment> instancedFragments = Maps.newLinkedHashMap();

    public LiveGameViewAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addViewPageStatisticFragments(Class<? extends StatisticPageFragment>... classes) {
        List<Class<? extends StatisticPageFragment>> classesList = Lists.newArrayList(classes);
        fragments.addAll(classesList);
    }

    public Collection<StatisticPageFragment> getInstancedFragments() {
        return instancedFragments.values();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public StatisticPageFragment getItem(int position) {
        try {
            if (!instancedFragments.containsKey(position)) {
                StatisticPageFragment fragment = fragments.get(position).getConstructor().newInstance();
                instancedFragments.put(position, fragment);
            }
            return instancedFragments.get(position);
        } catch (Exception e) {
            Log.e(MainActivity.DEBUG_APP_NAME, "Error creating view page fragment for live game", e);
        }

        return null; // TODO
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        try {
            if (!instancedFragments.containsKey(position)) {
                StatisticPageFragment fragment = fragments.get(position).getConstructor().newInstance();
                instancedFragments.put(position, fragment);
            }
            int titleResourceId = (int) fragments.get(position).getDeclaredMethod("getTitleStringResource")
                    .invoke(instancedFragments.get(position));
            return MainActivity.context.getString(titleResourceId);
        } catch (Exception e) {
            Log.e(MainActivity.DEBUG_APP_NAME, "Error creating fragment title for live game");
            e.printStackTrace();
        }

        return "";
    }
}