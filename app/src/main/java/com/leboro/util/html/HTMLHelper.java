package com.leboro.util.html;

import android.util.Log;

import com.leboro.MainActivity;
import com.leboro.util.Constants;
import com.leboro.util.sharedpreferences.SharedPreferencesHelper;

public class HTMLHelper {

    public enum StandingType {
        POINTS(0),
        GAME_VALUE(12, 1),
        TOTAL_REBOUNDS(1, 2),
        DEFENSIVE_REBOUNDS(3, 3),
        OFFENSIVE_REBOUNDS(2, 4),
        ASSISTS(4, 5),
        STEALS(5, 6),
        TURNOVERS(6, 7),
        BLOCKS(7, 8)
        //        MINUTES_PER_GAME(13, 9),
        //        FIELD_GOALS_PERCENTAGE(14, 10),
        //        THREE_POINTER_PERCENTAGE(15, 11),
        //        FREE_THROW_PERCENTAGE(16, 12)
        ;

        private final int id;

        private final int position;

        StandingType(int id, int position) {
            this.id = id;
            this.position = position;
        }

        StandingType(int idAndPosition) {
            this.id = idAndPosition;
            this.position = idAndPosition;
        }

        public int getId() {
            return id;
        }

        public int getPosition() {
            return position;
        }

        public static StandingType getFromPosition(int position) {
            switch (position) {
                case 0:
                    return POINTS;
                case 1:
                    return GAME_VALUE;
                case 2:
                    return TOTAL_REBOUNDS;
                case 3:
                    return DEFENSIVE_REBOUNDS;
                case 4:
                    return OFFENSIVE_REBOUNDS;
                case 5:
                    return ASSISTS;
                case 6:
                    return STEALS;
                case 7:
                    return TURNOVERS;
                case 8:
                    return BLOCKS;
                //                case 9:
                //                    return MINUTES_PER_GAME;
                //                case 10:
                //                    return FIELD_GOALS_PERCENTAGE;
                //                case 11:
                //                    return THREE_POINTER_PERCENTAGE;
                //                case 12:
                //                    return FREE_THROW_PERCENTAGE;
                default:
                    Log.d(MainActivity.DEBUG_APP_NAME,
                            "Unrecognized standing position [" + position + "]. Returning points type");
                    return POINTS;
            }
        }

        public static StandingType getFromId(int id) {
            switch (id) {
                case 0:
                    return POINTS;
                case 1:
                    return TOTAL_REBOUNDS;
                case 2:
                    return OFFENSIVE_REBOUNDS;
                case 3:
                    return DEFENSIVE_REBOUNDS;
                case 4:
                    return ASSISTS;
                case 5:
                    return STEALS;
                case 6:
                    return TURNOVERS;
                case 7:
                    return BLOCKS;
                case 12:
                    return GAME_VALUE;
                //                case 9:
                //                    return MINUTES_PER_GAME;
                //                case 10:
                //                    return FIELD_GOALS_PERCENTAGE;
                //                case 11:
                //                    return THREE_POINTER_PERCENTAGE;
                //                case 12:
                //                    return FREE_THROW_PERCENTAGE;
                default:
                    Log.d(MainActivity.DEBUG_APP_NAME,
                            "Unrecognized standing id [" + id + "]. Returning points type");
                    return POINTS;
            }
        }
    }

    public static void clearStandingViewStateToken() {
        SharedPreferencesHelper.getDefaultSharedPreferences(MainActivity
                .context).edit().remove(Constants.STANDING_VIEW_STATE_TOKEN_SHARED_PROP).apply();
    }

    public static void clearStandingEventValidationToken() {
        SharedPreferencesHelper.getDefaultSharedPreferences(MainActivity
                .context).edit().remove(Constants.STANDING_EVENT_VALIDATION_TOKEN_SHARED_PROP).apply();
    }

    public static String getStandingViewStateToken() {
        return SharedPreferencesHelper.getDefaultSharedPreferences(MainActivity
                .context).getString(Constants.STANDING_VIEW_STATE_TOKEN_SHARED_PROP, null);
    }

    public static String getStandingEventValidationToken() {
        return SharedPreferencesHelper.getDefaultSharedPreferences(MainActivity
                .context).getString(Constants.STANDING_EVENT_VALIDATION_TOKEN_SHARED_PROP, null);
    }

    public static String getGameDayViewStateToken() {
        return SharedPreferencesHelper.getDefaultSharedPreferences(MainActivity
                .context).getString(Constants.GAME_DAY_VIEW_STATE_TOKEN_SHARED_PROP, null);
    }

    public static String getGameDayEventValidationToken() {
        return SharedPreferencesHelper.getDefaultSharedPreferences(MainActivity
                .context).getString(Constants.GAME_DAY_EVENT_VALIDATION_TOKEN_SHARED_PROP, null);
    }

}
