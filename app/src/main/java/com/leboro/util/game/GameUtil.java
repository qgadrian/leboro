package com.leboro.util.game;

import com.leboro.MainActivity;
import com.leboro.R;
import com.leboro.model.api.live.overview.LiveGameOverview;

public class GameUtil {

    public static String getGamePeriodString(int quarter) {
        if (quarter > 4) {
            return MainActivity.context.getString(R.string.game_attr_overtime_res) + String
                    .valueOf(quarter - 4);
        }

        return quarter + MainActivity.context.getString(R.string.game_attr_quarter_res);
    }
}
