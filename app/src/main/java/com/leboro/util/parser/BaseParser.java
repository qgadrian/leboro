package com.leboro.util.parser;

import android.content.SharedPreferences;

import com.leboro.MainActivity;
import com.leboro.util.Constants;
import com.leboro.util.sharedpreferences.SharedPreferencesHelper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class BaseParser {

    public static Document parseHTMLData(String stringToParse) {
        return Jsoup.parse(stringToParse);
    }

    public static Document parseHTMLAndSaveStandingTokenData(String htmlAsString) {
        return parseHTMLAndSaveToken(htmlAsString, Constants.STANDING_VIEW_STATE_TOKEN_SHARED_PROP, Constants
                .STANDING_EVENT_VALIDATION_TOKEN_SHARED_PROP);
    }

    public static Document parseHTMLAndSaveGameDayTokenData(String htmlAsString) {
        return parseHTMLAndSaveToken(htmlAsString, Constants.GAME_DAY_VIEW_STATE_TOKEN_SHARED_PROP, Constants
                .GAME_DAY_EVENT_VALIDATION_TOKEN_SHARED_PROP);
    }

    private static Document parseHTMLAndSaveToken(String htmlAsString, String viewStateTokenPropertyName, String
            eventValidationTokenPropertyName) {
        Document parsedHTML = BaseParser.parseHTMLData(htmlAsString);
        saveHTMLTokensIfPresent(parsedHTML, viewStateTokenPropertyName, eventValidationTokenPropertyName);
        return parsedHTML;
    }

    private static void saveHTMLTokensIfPresent(Document parsedHTML, String viewStateTokenPropertyName,
            String eventValidationTokenPropertyName) {

        Element viewStateElement = parsedHTML.getElementsByAttributeValue("name", "__VIEWSTATE").first();
        Element eventValidationStateElement =
                parsedHTML.getElementsByAttributeValue("name", "__EVENTVALIDATION").first();

        if (viewStateElement != null && eventValidationStateElement != null) {
            SharedPreferences.Editor sharedPrefsEditor = SharedPreferencesHelper.getDefaultSharedPreferencesEditor
                    (MainActivity.context);
            sharedPrefsEditor.putString(viewStateTokenPropertyName, viewStateElement.val());
            sharedPrefsEditor.putString(eventValidationTokenPropertyName, eventValidationStateElement.val());
            sharedPrefsEditor.commit();
        }
    }

}
