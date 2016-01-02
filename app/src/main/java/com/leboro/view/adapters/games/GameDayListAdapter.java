package com.leboro.view.adapters.games;

import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.leboro.R;
import com.leboro.model.game.GameResult;
import com.leboro.service.ApplicationServiceProvider;
import com.leboro.util.calendar.CalendarUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class GameDayListAdapter extends ArrayAdapter<GameResult> {

    private static LayoutInflater inflater = null;

    private final int resource;

    private final List<GameResult> gameResults;

    private final static ImageLoader imageLoader = ApplicationServiceProvider.getNetworkImageLoaderService()
            .getImageLoader();

    public GameDayListAdapter(Context context, int resource, List<GameResult> gameResults) {
        super(context, resource, gameResults);
        this.resource = resource;
        this.gameResults = gameResults;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return gameResults.size();
    }

    @Override
    public GameResult getItem(int position) {
        return gameResults.get(position);
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

        NetworkImageView homeTeamLogo = (NetworkImageView) view.findViewById(R.id.gameDayRowHomeLogo);
        NetworkImageView awayTeamLogo = (NetworkImageView) view.findViewById(R.id.gameDayRowAwayLogo);
        TextView homeTeamScore = (TextView) view.findViewById(R.id.gameDayHomeScore);
        TextView awayTeamScore = (TextView) view.findViewById(R.id.gameDayAwayScore);
        TextView gameDate = (TextView) view.findViewById(R.id.gamedayRowDate);

        GameResult gameResult = gameResults.get(position);

        homeTeamLogo.setImageUrl(gameResult.getHomeTeam().getLogoUrl(), imageLoader);
        awayTeamLogo.setImageUrl(gameResult.getAwayTeam().getLogoUrl(), imageLoader);
        homeTeamScore.setText(String.valueOf(gameResult.getHomeScore()));
        awayTeamScore.setText(String.valueOf(gameResult.getAwayScore()));
        gameDate.setText(CalendarUtils.toString(gameResult.getStartDate()));

        return view;
    }

}