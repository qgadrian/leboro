package com.leboro.view.helper.http;

import java.util.Properties;

import com.leboro.MainActivity;
import com.leboro.util.Constants;
import com.leboro.util.http.HttpUtils;

import cz.msebera.android.httpclient.client.methods.HttpGet;

public class HttpHelper {

    public static String getHtmlFromSimpleHttpRequestUsingProperties(String urlPropertyKey) {
        Properties properties = MainActivity.properties;
        String url = properties.getProperty(urlPropertyKey);
        return getHtmlFromSimpleHttpRequest(url);
    }

    public static String getHtmlFromSimpleHttpRequest(String url) {
        Properties properties = MainActivity.properties;

        String acceptHeader = properties.getProperty(Constants.URL_HEADER_ACCEPT_PROP);
        String referrerHeader = properties.getProperty(Constants.URL_HEADER_REFERRER_PROP);
        HttpGet request = new HttpGet(url);
        request.setHeader("Accept", acceptHeader);
        request.setHeader("Referrer", referrerHeader);

        return HttpUtils.doAsyncGet(request);
    }

    public static void addApiRequestHeaders(HttpGet request) {
        request.setHeader("Accept", "application/json");
    }
}
