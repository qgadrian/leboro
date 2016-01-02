package com.leboro.view.adapters.classification;

import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.leboro.MainActivity;
import com.leboro.R;
import com.leboro.model.classification.Position;
import com.leboro.service.ApplicationServiceProvider;
import com.leboro.util.Constants;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ClassificationListAdapter extends ArrayAdapter<Position> {

    private static LayoutInflater inflater = null;

    private final int resource;

    private final List<Position> positions;

    private final static ImageLoader imageLoader = ApplicationServiceProvider.getNetworkImageLoaderService()
            .getImageLoader();

    private final int numberOfDirectAscendTeams;

    private final int numberOfPlayoffsTeams;

    private final int numberOfDescendPlayoffsTeams;

    private final int numberOfDirectDescendTeams;

    public ClassificationListAdapter(Context context, int resource, List<Position> positions) {
        super(context, resource, positions);
        this.resource = resource;
        this.positions = positions;
        this.numberOfDirectAscendTeams = Integer
                .valueOf(MainActivity.properties.getProperty(Constants.COMPETITION_DIRECT_ASCEND_TEAMS_PROP));
        this.numberOfPlayoffsTeams = Integer
                .valueOf(MainActivity.properties.getProperty(Constants.COMPETITION_PLAYOFF_TEAMS_PROP));
        this.numberOfDescendPlayoffsTeams = Integer
                .valueOf(MainActivity.properties.getProperty(Constants.COMPETITION_PLAYOFF_DESCEND_TEAMS_PROP));
        this.numberOfDirectDescendTeams = Integer
                .valueOf(MainActivity.properties.getProperty(Constants.COMPETITION_DIRECT_DESCEND_TEAMS_PROP));
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return positions.size();
    }

    @Override
    public Position getItem(int position) {
        return positions.get(position);
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

        NetworkImageView logo = (NetworkImageView) view.findViewById(R.id.classificationListLogo);
        TextView classificationPosition = (TextView) view.findViewById(R.id.classificationListPosition);
        TextView teamName = (TextView) view.findViewById(R.id.classificationListTeamName);
        TextView gamesWon = (TextView) view.findViewById(R.id.classificationListGamesWon);
        TextView gamesLost = (TextView) view.findViewById(R.id.classificationListGamesLost);
        TextView points = (TextView) view.findViewById(R.id.classificationListPoints);

        Position aTeamPosition = positions.get(position);

        setStyleForTeamPositions(position, classificationPosition, teamName, gamesWon, gamesLost, points);

        logo.setImageUrl(aTeamPosition.getTeam().getLogoUrl(), imageLoader);
        classificationPosition.setText(String.valueOf(aTeamPosition.getPosition()));
        teamName.setText(aTeamPosition.getTeam().getName());
        points.setText(String.valueOf(aTeamPosition.getClassificationPoints()));
        gamesWon.setText(String.valueOf(aTeamPosition.getGamesWon()));
        gamesLost.setText(String.valueOf(aTeamPosition.getGamesLost()));

        return view;
    }

    private void setStyleForTeamPositions(int position, TextView... textViews) {
        String textColor = "#212121";
        if (position < numberOfDirectAscendTeams) {
            textColor = "#3F51B5";
        } else if (position < numberOfPlayoffsTeams + numberOfDirectAscendTeams) {
            textColor = "#448AFF";
        } else if (getCount() - position <= numberOfDirectDescendTeams) {
            textColor = "#B71C1C";
        } else if (getCount() - position <= numberOfDescendPlayoffsTeams + numberOfDirectDescendTeams) {
            textColor = "#E57373";
        }

        for (TextView textView : textViews) {
            textView.setTextColor(Color.parseColor(textColor));
        }
    }

}