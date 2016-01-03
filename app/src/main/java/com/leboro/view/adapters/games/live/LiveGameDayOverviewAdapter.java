package com.leboro.view.adapters.games.live;

import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.leboro.MainActivity;
import com.leboro.R;
import com.leboro.model.game.live.LiveOverview;
import com.leboro.service.ApplicationServiceProvider;
import com.leboro.util.calendar.CalendarUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class LiveGameDayOverviewAdapter extends ArrayAdapter<LiveOverview> {

    private static LayoutInflater inflater = null;

    private final int resource;

    private List<LiveOverview> liveOverViews;

    private final static ImageLoader imageLoader = ApplicationServiceProvider.getNetworkImageLoaderService()
            .getImageLoader();

    public LiveGameDayOverviewAdapter(Context context, int resource, List<LiveOverview> liveOverViews) {
        super(context, resource, liveOverViews);
        this.resource = resource;
        this.liveOverViews = liveOverViews;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void updateData(List<LiveOverview> liveOverviews) {
        this.liveOverViews = liveOverviews;
    }

    @Override
    public int getCount() {
        return liveOverViews.size();
    }

    @Override
    public LiveOverview getItem(int position) {
        return liveOverViews.get(position);
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
        TextView gameDate = (TextView) view.findViewById(R.id.gameDayRowStatus);

        LiveOverview liveOverview = liveOverViews.get(position);

        homeTeamLogo.setImageUrl(liveOverview.getHomeTeam().getLogoUrl(), imageLoader);
        awayTeamLogo.setImageUrl(liveOverview.getAwayTeam().getLogoUrl(), imageLoader);
        homeTeamScore.setText(String.valueOf(liveOverview.getHomeScore()));
        awayTeamScore.setText(String.valueOf(liveOverview.getAwayScore()));

        if ((liveOverview.getHomeScore() == 0 && liveOverview.getAwayScore() == 0 && liveOverview.getStartDate()
                .isBeforeNow()) || liveOverview.getStartDate().isAfterNow()) {
            gameDate.setText(CalendarUtils.toString(liveOverview.getStartDate()));
        } else if (!liveOverview.getHomeScore().equals(liveOverview.getAwayScore()) && liveOverview.getTimeLeft()
                .equals("00:00")) {
            gameDate.setText(MainActivity.context.getString(R.string.live_game_overview_current_status_finished));
        } else {
            String statusInfoString = MainActivity.context.getString(R.string.live_game_overview_current_status)
                    .replace("{quarter}", getGamePeriodString(liveOverview)).replace("{timeLeft}",
                            liveOverview.getTimeLeft());
            gameDate.setText(statusInfoString);
        }

        return view;
    }

    private String getGamePeriodString(LiveOverview liveOverview) {
        if (liveOverview.getQuarter() > 4) {
            return MainActivity.context.getString(R.string.game_attr_overtime_res) + String
                    .valueOf(liveOverview.getQuarter() - 4);
        }

        return liveOverview.getQuarter() + MainActivity.context.getString(R.string.game_attr_quarter_res);
    }

}