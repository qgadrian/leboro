package com.leboro.view.adapters.games;

import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.leboro.MainActivity;
import com.leboro.R;
import com.leboro.model.game.GameResult;
import com.leboro.service.ApplicationServiceProvider;
import com.leboro.util.calendar.CalendarUtils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class GameDayListAdapter extends ArrayAdapter<GameResult> {

    private static LayoutInflater inflater = null;

    private final int resource;

    private List<GameResult> gameResults;

    private final static ImageLoader imageLoader = ApplicationServiceProvider.getNetworkImageLoaderService()
            .getImageLoader();

    public GameDayListAdapter(Context context, int resource, List<GameResult> gameResults) {
        super(context, resource, gameResults);
        this.resource = resource;
        this.gameResults = gameResults;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setGameResultsAndNotify(List<GameResult> gameResults) {
        this.gameResults = gameResults;
        super.notifyDataSetChanged();
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
        TextView gameStatus = (TextView) view.findViewById(R.id.gameDayRowStatus);

        GameResult gameResult = gameResults.get(position);

        homeTeamLogo.setImageUrl(gameResult.getHomeTeam().getLogoUrl(), imageLoader);
        awayTeamLogo.setImageUrl(gameResult.getAwayTeam().getLogoUrl(), imageLoader);
        homeTeamScore.setText(String.valueOf(gameResult.getHomeScore()));
        awayTeamScore.setText(String.valueOf(gameResult.getAwayScore()));

        if (gameResult.getStartDate().isBeforeNow() && gameResult.getHomeScore() != 0
                && gameResult.getAwayScore() != 0) {
            gameStatus.setText(MainActivity.context.getString(R.string.game_attr_status_finished));
            gameStatus.setTypeface(Typeface.DEFAULT_BOLD);
        } else {
            gameStatus.setText(CalendarUtils.toString(gameResult.getStartDate()));
            gameStatus.setTypeface(Typeface.DEFAULT);
        }

        return view;
    }

}