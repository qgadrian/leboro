package com.leboro.view.adapters.games.live;

import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.leboro.MainActivity;
import com.leboro.R;
import com.leboro.model.api.live.overview.LiveGameOverview;
import com.leboro.service.ApplicationServiceProvider;
import com.leboro.util.calendar.CalendarUtils;
import com.leboro.util.game.GameUtil;
import com.leboro.view.helper.gameday.GameDayHelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class LiveGameDayOverviewAdapter extends ArrayAdapter<LiveGameOverview> {

    private static LayoutInflater inflater = null;

    private final int resource;

    private List<LiveGameOverview> liveGameOverViews;

    private final static ImageLoader imageLoader = ApplicationServiceProvider.getNetworkImageLoaderService()
            .getImageLoader();

    public LiveGameDayOverviewAdapter(Context context, int resource, List<LiveGameOverview> liveGameOverViews) {
        super(context, resource, liveGameOverViews);
        this.resource = resource;
        this.liveGameOverViews = liveGameOverViews;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void updateDataAndNotify(List<LiveGameOverview> liveGameOverviews) {
        this.liveGameOverViews = liveGameOverviews;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return liveGameOverViews.size();
    }

    @Override
    public LiveGameOverview getItem(int position) {
        return liveGameOverViews.get(position);
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

        LiveGameOverview liveGameOverview = liveGameOverViews.get(position);

        homeTeamLogo.setImageUrl(liveGameOverview.getHomeTeam().getLogoUrl(), imageLoader);
        awayTeamLogo.setImageUrl(liveGameOverview.getAwayTeam().getLogoUrl(), imageLoader);

        if (GameDayHelper.isStarted(liveGameOverview)) {
            homeTeamScore.setText(String.valueOf(liveGameOverview.getHomeScore()));
            awayTeamScore.setText(String.valueOf(liveGameOverview.getAwayScore()));
        } else {
            homeTeamScore.setText("");
            awayTeamScore.setText("");
        }

        if (!GameDayHelper.isStarted(liveGameOverview)) {
            gameDate.setText(CalendarUtils.toString(liveGameOverview.getStartDate()));
        } else if (!liveGameOverview.getHomeScore().equals(liveGameOverview.getAwayScore())
                && liveGameOverview.getCurrentQuarter() >= 4
                && (liveGameOverview.getTimeLeft().equals("00:00") || liveGameOverview.getTimeLeft().equals("FINAL"))) {
            gameDate.setText(MainActivity.context.getString(R.string.live_game_overview_current_status_finished));
        } else {
            String statusInfoString = MainActivity.context.getString(R.string.live_game_overview_current_status)
                    .replace("{quarter}", GameUtil.getGamePeriodString(liveGameOverview.getCurrentQuarter())).replace("{timeLeft}",
                            liveGameOverview.getTimeLeft());
            gameDate.setText(statusInfoString);
        }

        return view;
    }

}