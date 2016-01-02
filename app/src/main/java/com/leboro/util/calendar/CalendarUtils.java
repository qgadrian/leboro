package com.leboro.util.calendar;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.leboro.MainActivity;
import com.leboro.util.Constants;

public class CalendarUtils {

    public static DateTime parseServerBuiltStringDate(String serverBuiltStringDate) {
        String dateFormat = MainActivity.properties.getProperty(Constants.SERVER_GAME_DATE_FORMAT_PROP);
        DateTimeFormatter dtf = DateTimeFormat.forPattern(dateFormat);
        return dtf.parseDateTime(serverBuiltStringDate);
    }

    public static String toString(DateTime dateTime) {
        String dateFormat = MainActivity.properties.getProperty(Constants.GAME_DATE_FORMAT_PROP);
        DateTimeFormatter dtf = DateTimeFormat.forPattern(dateFormat);
        return dtf.print(dateTime);
    }

}
