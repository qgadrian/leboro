package com.leboro.view.fragment;

import com.leboro.R;
import com.leboro.view.fragment.classification.ClassificationFragment;
import com.leboro.view.fragment.games.gameday.GameDaysFragment;
import com.leboro.view.fragment.games.live.LiveGameDayOverviewFragment;
import com.leboro.view.fragment.games.live.LiveGameVideoFragment;
import com.leboro.view.fragment.news.NewsFragment;
import com.leboro.view.fragment.standings.StandingsFragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;

public abstract class FragmentDisplayableActivity extends AppCompatActivity {

    private boolean isFirstFragment = true;

    private Integer currentFragmentResourceId;

    protected void displayView(int viewId) {

        Fragment fragment = null;
        String title = getString(R.string.app_name);

        switch (viewId) {
            case R.id.nav_classification:
                fragment = new ClassificationFragment();
                title = getString(R.string.navigation_drawer_classification);
                break;
            case R.id.nav_games:
                fragment = new GameDaysFragment();
                title = getString(R.string.navigation_drawer_games);
                break;
            case R.id.nav_live_games:
                fragment = new LiveGameDayOverviewFragment();
                title = getString(R.string.navigation_drawer_live_games);
                break;
            case R.id.nav_news:
                fragment = new NewsFragment();
                title = getString(R.string.navigation_drawer_news);
                break;
            case R.id.nav_standings:
                fragment = new StandingsFragment();
                title = getString(R.string.navigation_drawer_standings);
                break;
            case R.id.live_game_video_layout:
                fragment = new LiveGameVideoFragment();
                title = getString(R.string.navigation_live_video_game);
                break;
            case R.id.nav_share:
                shareApplication();
                break;
        }

        if (currentFragmentResourceId == null || !currentFragmentResourceId.equals(viewId)) {
            currentFragmentResourceId = viewId;
            fragmentTransition(fragment, title);
        } else {
            closeDrawer();
        }

    }

    public void fragmentTransition(Fragment fragment, String title) {
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            ft.replace(R.id.content_frame, fragment);

            if (!isFirstFragment) {
                ft.addToBackStack(null);
            }

            isFirstFragment = false;

            ft.commit();
        }

        setActionBarTitle(title);
        closeDrawer();
    }

    public void setActionBarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    private void shareApplication() {
        String sharingMessage = getString(R.string.share_application_message)
                .replace("{url}", "https://goo.gl/UEqryn");
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, sharingMessage);
        startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_application_title)));
    }

    private void closeDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

}
