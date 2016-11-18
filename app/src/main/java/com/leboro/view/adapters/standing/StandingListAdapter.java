package com.leboro.view.adapters.standing;

import java.text.DecimalFormat;
import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.leboro.R;
import com.leboro.model.api.standing.PlayerStanding;
import com.leboro.service.ApplicationServiceProvider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class StandingListAdapter extends ArrayAdapter<PlayerStanding> {

    private static LayoutInflater inflater = null;

    private final int resource;

    private List<PlayerStanding> playerStandings;

    private final static ImageLoader imageLoader = ApplicationServiceProvider.getNetworkImageLoaderService()
            .getImageLoader();

    public StandingListAdapter(Context context, int resource, List<PlayerStanding> playerStandings) {
        super(context, resource, playerStandings);
        this.resource = resource;
        this.playerStandings = playerStandings;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setPlayerStandingsAndNotify(List<PlayerStanding> playerStandings) {
        this.playerStandings = playerStandings;
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return playerStandings.size();
    }

    @Override
    public PlayerStanding getItem(int position) {
        return playerStandings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(resource, null);
        }

        NetworkImageView playerImage = (NetworkImageView) view.findViewById(R.id.playerStandingImage);
        TextView playerStandingPosition = (TextView) view.findViewById(R.id.playerStandingPosition);
        TextView playerStandingName = (TextView) view.findViewById(R.id.playerStandingName);
        TextView playerStandingTeamName = (TextView) view.findViewById(R.id.playerStandingTeamName);
        TextView playerStandingAverageValue = (TextView) view.findViewById(R.id.playerStandingAverageValue);

        PlayerStanding playerStanding = playerStandings.get(position);

        playerImage.setImageUrl(playerStanding.getImageUrl(), imageLoader);
        playerStandingPosition.setText(String.valueOf(position + 1));
        playerStandingName.setText(playerStanding.getPlayerName());
        playerStandingTeamName.setText(playerStanding.getTeamName());
        playerStandingAverageValue
                .setText(String.valueOf(new DecimalFormat("#.##").format(playerStanding.getValueAveraged())));

        return view;
    }

}