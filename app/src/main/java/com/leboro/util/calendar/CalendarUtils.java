package com.leboro.util.calendar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.leboro.MainActivity;
import com.leboro.util.Constants;
import com.leboro.util.properties.PropertiesHelper;

import android.util.Log;

public class CalendarUtils {

    private static String serverDateFormat = null;

    private static String apiGameInfoDateFormat = null;

    public static DateTime parseServerBuiltStringDate(String serverBuiltStringDate) {
        if (serverDateFormat == null) {
            serverDateFormat = PropertiesHelper.getProperty(Constants.SERVER_GAME_DATE_FORMAT_PROP);
        }

        DateTimeFormatter dtf = DateTimeFormat.forPattern(serverDateFormat);
        return dtf.parseDateTime(serverBuiltStringDate);
    }

    public static String toString(DateTime dateTime) {
        String dateFormat = PropertiesHelper.getProperty(Constants.GAME_DATE_FORMAT_PROP);
        DateTimeFormatter dtf = DateTimeFormat.forPattern(dateFormat);
        return dtf.print(dateTime);
    }

    public static DateTime parseApiGameInfoStartDate(String apiGameInfoDateAsString) {
        if (apiGameInfoDateFormat == null) {
            apiGameInfoDateFormat = PropertiesHelper.getProperty(Constants.API_LIVE_GAME_DATE_FORMAT_PROP);
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

    public static Date parsePublicationDateFromUrl(String articleUrl) {
        return parsePublicationDateFromUrl(articleUrl, false);
    }

    public static Date parsePublicationDateFromUrl(String articleUrl, boolean addOneDayToPublicationDate) {
        try {
            Pattern pattern = Pattern.compile(".*.[es|com]/([0-9]{4})/([0-9]{2})/([0-9]{2}).*");

            Matcher matcher = pattern.matcher(articleUrl);

            if (matcher.matches()) {
                int year = Integer.parseInt(matcher.group(1));
                int month = Integer.parseInt(matcher.group(2));
                int day = Integer.parseInt(matcher.group(3));

                if (addOneDayToPublicationDate) {
                    day += 1;
                }

                DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
                return formatter.parse(month + "/" + day + "/" + year);
            }
        } catch (Exception e) {
            Log.d(MainActivity.DEBUG_APP_NAME, "Error parsing article url date", e);
        }

        return null;
    }
}
