package com.leboro.view.adapters.games;

import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.leboro.MainActivity;
import com.leboro.R;
import com.leboro.model.game.GameInfo;
import com.leboro.model.game.GameResult;
import com.leboro.service.ApplicationServiceProvider;
import com.leboro.util.calendar.CalendarUtils;
import com.leboro.view.helper.gameday.GameDayHelper;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class GameDayListAdapter extends ArrayAdapter<GameInfo> {

    private static LayoutInflater inflater = null;

    private final int resource;

    private List<GameInfo> gameInfos;

    private final static ImageLoader imageLoader = ApplicationServiceProvider.getNetworkImageLoaderService()
            .getImageLoader();

    public GameDayListAdapter(Context context, int resource, List<GameInfo> gameInfos) {
        super(context, resource, gameInfos);
        this.resource = resource;
        this.gameInfos = gameInfos;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setGameResultsAndNotify(List<GameInfo> gameInfos) {
        this.gameInfos = gameInfos;
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return gameInfos.size();
    }

    @Override
    public GameInfo getItem(int position) {
        return gameInfos.get(position);
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

        GameResult gameResult = gameInfos.get(position).getGameResult();

        homeTeamLogo.setImageUrl(gameResult.getHomeTeam().getLogoUrl(), imageLoader);
        awayTeamLogo.setImageUrl(gameResult.getAwayTeam().getLogoUrl(), imageLoader);

        if (GameDayHelper.isStarted(gameResult)) {
            gameStatus.setText(MainActivity.context.getString(R.string.game_attr_status_finished));
            gameStatus.setTypeface(Typeface.DEFAULT_BOLD);
            homeTeamScore.setText(String.valueOf(gameResult.getHomeScore()));
            awayTeamScore.setText(String.valueOf(gameResult.getAwayScore()));
        } else {
            gameStatus.setText(CalendarUtils.toString(gameResult.getStartDate()));
            gameStatus.setTypeface(Typeface.DEFAULT);
            homeTeamScore.setText("");
            awayTeamScore.setText("");
        }

        return view;
    }

}