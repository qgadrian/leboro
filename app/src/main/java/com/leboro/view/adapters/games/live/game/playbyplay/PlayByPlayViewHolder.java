package com.leboro.view.adapters.games.live.game.playbyplay;

import com.android.volley.toolbox.NetworkImageView;
import com.leboro.R;
import com.leboro.model.game.live.playbyplay.PlayByPlay.PlayLine;
import com.leboro.service.ApplicationServiceProvider;
import com.leboro.view.adapters.BaseViewHolder;

import android.view.View;
import android.widget.TextView;

public class PlayByPlayViewHolder extends BaseViewHolder<PlayLine> {

    public TextView playLineText;

    public NetworkImageView teamLogo;

    public PlayByPlayViewHolder(View itemView) {
        super(itemView);
        playLineText = (TextView) itemView.findViewById(R.id.playByPayRowText);
        teamLogo = (NetworkImageView) itemView.findViewById(R.id.teamLogo);
    }

    @Override
    public void setHolderData(PlayLine element) {
        playLineText.setText(element.getText());
        if (element.getTeam() != null) {
            teamLogo.setImageUrl(element.getTeam().getLogoUrl(), ApplicationServiceProvider.getNetworkImageLoaderService
                    ().getImageLoader());
        } else {
            teamLogo.setVisibility(View.INVISIBLE);
        }
    }

}
