package com.leboro.util.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferencesHelper {

    public static SharedPreferences.Editor getDefaultSharedPreferencesEditor(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).edit();
    }

    public static SharedPreferences getDefaultSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

}
