package com.leboro.view.adapters.teaminfo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.leboro.R;
import com.leboro.model.team.info.TeamInfo;
import com.leboro.service.ApplicationServiceProvider;
import com.leboro.view.adapters.BaseArrayAdapter;

import java.util.List;

public class TeamInfoListAdapter extends BaseArrayAdapter<TeamInfo> {

    private static ImageLoader imageLoader = ApplicationServiceProvider.getNetworkImageLoaderService()
            .getImageLoader();

    public TeamInfoListAdapter(Context context, int resource, List<TeamInfo> teamInfos) {
        super(context, resource, teamInfos);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(rowLayoutResource, null);
        }


        TextView teamInfoName = (TextView) view.findViewById(R.id.teamInfoName);
        TextView teamInfoArenaName = (TextView) view.findViewById(R.id.teamInfoArenaName);
        TextView teamInfoArenaAddress = (TextView) view.findViewById(R.id.teamInfoArenaAddress);

        TeamInfo teamInfo = elements.get(position);

        NetworkImageView teamInfoLogo = (NetworkImageView) view.findViewById(R.id.teamInfoLogo);
        teamInfoLogo.setImageUrl(teamInfo.getLogoUrl(), imageLoader);

        teamInfoName.setText(teamInfo.getName());
        teamInfoArenaName.setText(teamInfo.getArenaName());
        teamInfoArenaAddress.setText(teamInfo.getArenaAddress());

        return view;
    }

}
