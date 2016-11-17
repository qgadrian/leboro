package com.leboro.util.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.leboro.MainActivity;
import com.leboro.util.Constants;
import com.leboro.util.sharedpreferences.SharedPreferencesHelper;

import android.content.SharedPreferences;

public class BaseParser {

    // TODO: This method should be protected
    public static Document parseHTMLData(String stringToParse) {
        return Jsoup.parse(stringToParse);
    }

    public static Document parseHTMLAndSaveTokenData(String htmlAsString) {
        return parseHTMLAndSaveToken(htmlAsString, Constants.VIEW_STATE_TOKEN_SHARED_PROP, Constants
                .EVENT_VALIDATION_TOKEN_SHARED_PROP);
    }

    public static Document parseHTMLAndSaveStandingTokenData(String htmlAsString) {
        return parseHTMLAndSaveToken(htmlAsString, Constants.STANDING_VIEW_STATE_TOKEN_SHARED_PROP, Constants
                .STANDING_EVENT_VALIDATION_TOKEN_SHARED_PROP);
    }

    private static Document parseHTMLAndSaveToken(String htmlAsString, String viewStateTokenPropertyName, String
            eventValidationTokenPropertyName) {
        Document parsedHTML = BaseParser.parseHTMLData(htmlAsString);
        String _VIEWSTATE = parsedHTML.getElementsByAttributeValue("name", "__VIEWSTATE").get(0).val();
        String _EVENTVALIDATION = parsedHTML.getElementsByAttributeValue("name", "__EVENTVALIDATION").get(0).val();
        SharedPreferences.Editor sharedPrefsEditor = SharedPreferencesHelper.getDefaultSharedPreferencesEditor
                (MainActivity.context);
        sharedPrefsEditor.putString(viewStateTokenPropertyName, _VIEWSTATE);
        sharedPrefsEditor.putString(eventValidationTokenPropertyName, _EVENTVALIDATION);
        sharedPrefsEditor.commit();
        return parsedHTML;
    }

}
