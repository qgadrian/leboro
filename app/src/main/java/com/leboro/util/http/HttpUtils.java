package com.leboro.util.http;

import android.util.Log;

import com.google.common.base.Charsets;
import com.leboro.MainActivity;
import com.leboro.service.task.http.HttpGetAsyncTask;

import java.util.concurrent.ExecutionException;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpRequestBase;
import cz.msebera.android.httpclient.util.EntityUtils;

public class HttpUtils {

    public static String doAsyncGet(HttpRequestBase requestBase) {
        HttpGetAsyncTask httpGetAsyncTask = new HttpGetAsyncTask();
        try {
            return httpGetAsyncTask.execute(requestBase).get();
        } catch (InterruptedException | ExecutionException e) {
            Log.d(MainActivity.DEBUG_APP_NAME, "Error obtaining classification data", e);
        }

        return null;
    }

    public static HttpRequestResponse doRequest(HttpClient client, HttpRequestBase request) {
        String responseString;

        HttpResponse response;

        try {
            response = client.execute(request);
            responseString = EntityUtils.toString(response.getEntity(), Charsets.UTF_8);
        } catch (Exception e) {
            Log.d(MainActivity.DEBUG_APP_NAME, "Error doing request", e);
            throw new RuntimeException("Error doing request");
        }

        int responseCode = response.getStatusLine().getStatusCode();

        return new HttpRequestResponse(responseCode, responseString);
    }

}
