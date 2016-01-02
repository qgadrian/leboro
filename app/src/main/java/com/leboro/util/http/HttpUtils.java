package com.leboro.util.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;

import com.leboro.MainActivity;
import com.leboro.service.task.HttpGetAsyncTask;

import android.util.Log;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpRequestBase;

public class HttpUtils {

    public static String doAsyncGet(HttpRequestBase requestBase) {
        HttpGetAsyncTask httpGetAsyncTask = new HttpGetAsyncTask();
        try {
            return httpGetAsyncTask.execute(requestBase).get();
        } catch (InterruptedException | ExecutionException e) {
            Log.d(MainActivity.DEBUG_APP, "Error obtaining classification data", e);
        }

        return null;
    }

    public static String doRequest(HttpClient client, HttpRequestBase request) {
        StringBuilder result = new StringBuilder();

        try {
            HttpResponse response = client.execute(request);

            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            Log.d(MainActivity.DEBUG_APP, "Error obtaining classification data", e);
        }

        return result.toString();
    }

}
