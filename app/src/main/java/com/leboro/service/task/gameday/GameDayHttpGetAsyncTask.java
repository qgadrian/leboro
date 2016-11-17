package com.leboro.service.task.gameday;

import com.leboro.util.http.HttpUtils;

import android.os.AsyncTask;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpRequestBase;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;

public class GameDayHttpGetAsyncTask extends AsyncTask<HttpRequestBase, Void, String> {

    @Override
    protected String doInBackground(HttpRequestBase... params) {
        HttpRequestBase request = params[0];
        HttpClient client = HttpClientBuilder.create().build();
        return HttpUtils.doRequest(client, request);
    }
}
