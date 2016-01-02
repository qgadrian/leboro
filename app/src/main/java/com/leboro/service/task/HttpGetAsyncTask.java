package com.leboro.service.task;

import com.leboro.util.http.HttpUtils;

import android.os.AsyncTask;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpRequestBase;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;

public class HttpGetAsyncTask extends AsyncTask<HttpRequestBase, Void, String> {

    @Override
    protected String doInBackground(HttpRequestBase... params) {
        HttpRequestBase request = params[0];
        //        String acceptHeader = params[1];
        //        String referrerHeader = params[2];

        HttpClient client = HttpClientBuilder.create().build();

        //        HttpGet request = new HttpGet(url);
        //        request.setHeader("Accept", acceptHeader);
        //        request.setHeader("Referrer", referrerHeader);

        return HttpUtils.doRequest(client, request);
    }
}
