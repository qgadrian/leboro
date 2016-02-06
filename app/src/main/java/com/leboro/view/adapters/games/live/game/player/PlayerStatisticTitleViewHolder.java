package com.leboro.view.adapters.games.live.game.player;

import com.android.volley.toolbox.NetworkImageView;
import com.leboro.R;
import com.leboro.model.team.Team;
import com.leboro.service.ApplicationServiceProvider;
import com.leboro.view.adapters.BaseViewHolder;

import android.view.View;
import android.widget.TextView;

public class PlayerStatisticTitleViewHolder extends BaseViewHolder<Team> {

    public TextView teamName;

    public NetworkImageView teamLogo;

    public PlayerStatisticTitleViewHolder(View view) {
        super(view);
        teamName = (TextView) view.findViewById(R.id.teamTitle);
        teamLogo = (NetworkImageView) view.findViewById(R.id.teamLogo);
    }

    @Override
    public void setHolderData(Team element) {
        teamName.setText(element.getName());
        teamLogo.setImageUrl(element.getLogoUrl(),
                ApplicationServiceProvider.getNetworkImageLoaderService().getImageLoader());
    }

}
