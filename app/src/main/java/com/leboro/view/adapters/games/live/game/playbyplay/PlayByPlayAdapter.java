package com.leboro.view.adapters.games.live.game.playbyplay;

import java.util.List;

import com.leboro.view.adapters.BaseRecyclerViewAdapter;

import static com.leboro.model.game.live.playbyplay.PlayByPlay.PlayLine;

public class PlayByPlayAdapter
        extends BaseRecyclerViewAdapter<PlayLine, PlayByPlayViewHolder> {

    public PlayByPlayAdapter(List<PlayLine> elements, int rowResource,
            Class<PlayByPlayViewHolder> holderClazz) {
        super(elements, rowResource, holderClazz);
    }

}
