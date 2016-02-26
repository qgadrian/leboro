package com.leboro.util.calendar;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.leboro.MainActivity;
import com.leboro.util.Constants;

import android.util.Log;

public class CalendarUtils {

    private static String serverDateFormat = null;

    private static String apiGameInfoDateFormat = null;

    public static DateTime parseServerBuiltStringDate(String serverBuiltStringDate) {
        if (serverDateFormat == null) {
            serverDateFormat = MainActivity.properties.getProperty(Constants.SERVER_GAME_DATE_FORMAT_PROP);
        }

        DateTimeFormatter dtf = DateTimeFormat.forPattern(serverDateFormat);
        return dtf.parseDateTime(serverBuiltStringDate);
    }

    public static String toString(DateTime dateTime) {
        String dateFormat = MainActivity.properties.getProperty(Constants.GAME_DATE_FORMAT_PROP);
        DateTimeFormatter dtf = DateTimeFormat.forPattern(dateFormat);
        return dtf.print(dateTime);
    }

    public static DateTime parseApiGameInfoStartDate(String apiGameInfoDateAsString) {
        if (apiGameInfoDateFormat == null) {
            apiGameInfoDateFormat = MainActivity.properties.getProperty(Constants.API_LIVE_GAME_DATE_FORMAT_PROP);
        }

        if (!apiGameInfoDateAsString.contains(",")) {
            Log.e(MainActivity.DEBUG_APP_NAME, "Received bad date format from api [" + apiGameInfoDateAsString + "]");
            return null;
        } else {
            String parsedApiLiveGameDate = apiGameInfoDateAsString.split(",")[1].trim();
            DateTimeFormatter dtf = DateTimeFormat.forPattern(apiGameInfoDateFormat);
            return dtf.parseDateTime(parsedApiLiveGameDate);
        }
    }
}
