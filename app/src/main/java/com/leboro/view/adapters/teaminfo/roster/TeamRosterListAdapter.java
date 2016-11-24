package com.leboro.view.adapters.teaminfo.roster;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.leboro.R;
import com.leboro.model.team.info.roster.TeamRoster;
import com.leboro.service.ApplicationServiceProvider;
import com.leboro.view.adapters.BaseArrayAdapter;

import java.util.List;

public class TeamRosterListAdapter extends BaseArrayAdapter<TeamRoster> {

    private static ImageLoader imageLoader = ApplicationServiceProvider.getNetworkImageLoaderService()
            .getImageLoader();

    public TeamRosterListAdapter(Context context, int resource, List<TeamRoster> teamInfos) {
        super(context, resource, teamInfos);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(rowLayoutResource, null);
        }


        TextView teamRosterName = (TextView) view.findViewById(R.id.teamRosterName);
        TextView teamPosition = (TextView) view.findViewById(R.id.teamRosterPosition);
        TextView teamRosterCountry = (TextView) view.findViewById(R.id.teamInfoRosterCountry);
        TextView teamRosterHeight = (TextView) view.findViewById(R.id.teamInfoRosterHeight);

        TeamRoster teamRoster = elements.get(position);

        NetworkImageView teamInfoLogo = (NetworkImageView) view.findViewById(R.id.teamRosterImage);
        teamInfoLogo.setImageUrl(teamRoster.getImageUrl(), imageLoader);

        teamRosterName.setText(teamRoster.getName());
        teamPosition.setText(teamRoster.getPosition());
        teamRosterCountry.setText(teamRoster.getCountry());

        if (teamRoster.getHeight() != null) {
            String heightString = getContext().getString(R.string.team_roster_height, teamRoster
                    .getHeight());
            teamRosterHeight.setText(heightString);
        } else {
            teamRosterHeight.setVisibility(View.GONE);
        }

        return view;
    }

}
