package com.leboro.view.fragment.games.live.game.statistic;

import com.leboro.view.fragment.LoadableFragment;

public abstract class StatisticPageFragment extends LoadableFragment {

    @SuppressWarnings("unused")
    /**
     * This method is reflectively @see {@link com.leboro.view.adapters.games.live.game.LiveGameViewAdapter}
     */
    public abstract int getTitleStringResource();

    public abstract void notifySetDataChanged();

}
