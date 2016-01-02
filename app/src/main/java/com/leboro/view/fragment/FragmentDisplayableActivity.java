package com.leboro.view.fragment;

import com.leboro.R;
import com.leboro.view.fragment.classification.ClassificationFragment;
import com.leboro.view.fragment.games.GamesFragment;
import com.leboro.view.fragment.games.live.LiveGameDayOverviewFragment;
import com.leboro.view.helper.classification.ClassificationHelper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;

public abstract class FragmentDisplayableActivity extends AppCompatActivity {

    public void displayView(int viewId) {

        Fragment fragment = null;
        String title = getString(R.string.app_name);

        switch (viewId) {
            case R.id.nav_classification:
                fragment = new ClassificationFragment();
                ClassificationHelper.addPositionsDataArguments(fragment);
                title = getString(R.string.navigation_drawer_classification);
                break;
            case R.id.nav_games:
                fragment = new GamesFragment();
                title = getString(R.string.navigation_drawer_games);
                break;
            case R.id.nav_live_games:
                fragment = new LiveGameDayOverviewFragment();
                title = getString(R.string.navigation_drawer_live_games);
                break;
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        // set the toolbar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }

}
