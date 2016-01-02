package com.leboro.view.adapters.games.live;

import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
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

    private final List<LiveOverview> liveOverViews;

    private final static ImageLoader imageLoader = ApplicationServiceProvider.getNetworkImageLoaderService()
            .getImageLoader();

    public LiveGameDayOverviewAdapter(Context context, int resource, List<LiveOverview> liveOverViews) {
        super(context, resource, liveOverViews);
        this.resource = resource;
        this.liveOverViews = liveOverViews;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        TextView gameDate = (TextView) view.findViewById(R.id.gamedayRowDate);

        LiveOverview liveOverview = liveOverViews.get(position);

        homeTeamLogo.setImageUrl(liveOverview.getHomeTeam().getLogoUrl(), imageLoader);
        awayTeamLogo.setImageUrl(liveOverview.getAwayTeam().getLogoUrl(), imageLoader);
        homeTeamScore.setText(String.valueOf(liveOverview.getHomeScore()));
        awayTeamScore.setText(String.valueOf(liveOverview.getAwayScore()));
        gameDate.setText(CalendarUtils.toString(liveOverview.getStartDate()));

        return view;
    }

}