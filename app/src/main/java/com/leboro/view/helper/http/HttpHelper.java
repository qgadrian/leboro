package com.leboro.view.helper.http;

import com.leboro.util.Constants;
import com.leboro.util.http.HttpUtils;
import com.leboro.util.properties.PropertiesHelper;

import cz.msebera.android.httpclient.client.methods.HttpGet;

public class HttpHelper {

    public static String getHtmlFromSimpleHttpRequestUsingProperties(String urlPropertyKey) {
        String url = PropertiesHelper.getProperty(urlPropertyKey);
        return getHtmlFromSimpleHttpRequest(url);
    }

    public static String getHtmlFromSimpleHttpRequest(String url) {
        String acceptHeader = PropertiesHelper.getProperty(Constants.URL_HEADER_ACCEPT_PROP);
        String referrerHeader = PropertiesHelper.getProperty(Constants.URL_HEADER_REFERRER_PROP);
        HttpGet request = new HttpGet(url);
        request.setHeader("Accept", acceptHeader);
        request.setHeader("Referrer", referrerHeader);

        return HttpUtils.doAsyncGet(request);
    }

    public static void addApiRequestHeaders(HttpGet request) {
        request.setHeader("Accept", "application/json");
    }
}
