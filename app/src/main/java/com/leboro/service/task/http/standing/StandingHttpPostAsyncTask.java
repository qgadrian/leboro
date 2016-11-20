package com.leboro.service.task.http.standing;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.leboro.util.http.HttpRequestResponse;
import com.leboro.util.http.HttpUtils;

import android.os.AsyncTask;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.EntityBuilder;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class StandingHttpPostAsyncTask
        extends AsyncTask<StandingHttpPostAsyncTask.ResultTaskArgument, Void, HttpRequestResponse> {

    @Override
    protected HttpRequestResponse doInBackground(ResultTaskArgument... params) {
        String url = params[0].getUrl();
        String acceptHeader = params[0].getAcceptHeader();
        String referrerHeader = params[0].getReferrerHeader();
        int standingType = params[0].getStandingType();
        String _EVENTVALIDATION = params[0].get_EVENTVALIDATION();
        String _VIEWSTATE = params[0].get_VIEWSTATE();

        HttpClient client = HttpClientBuilder.create().build();

        HttpPost request = new HttpPost(url);
        request.setHeader("Accept", acceptHeader);
        request.setHeader("Referrer", referrerHeader);

        List<NameValuePair> pairs = Lists.newArrayList();
        pairs.add(new BasicNameValuePair("ctl00$tiposRankingDropDownList", String.valueOf(standingType)));

        // Server throws an error if the tokens are empty or invalid
        if (StringUtils.isNotBlank(_VIEWSTATE) && StringUtils.isNotBlank(_EVENTVALIDATION)) {
            pairs.add(new BasicNameValuePair("__VIEWSTATE", _VIEWSTATE));
            pairs.add(new BasicNameValuePair("__EVENTVALIDATION", _EVENTVALIDATION));
        }

        HttpEntity entity = EntityBuilder.create().setParameters(pairs).setContentType(null).build();
        request.setEntity(entity);

        return HttpUtils.doRequest(client, request);
    }

    public static class ResultTaskArgument {

        private final String url;

        private final String acceptHeader;

        private final String referrerHeader;

        private final int standingType;

        private final String _EVENTVALIDATION;

        private final String _VIEWSTATE;

        public ResultTaskArgument(String url, String acceptHeader, String referrerHeader, int standingType,
                String _EVENTVALIDATION, String _VIEWSTATE) {
            this.url = url;
            this.acceptHeader = acceptHeader;
            this.referrerHeader = referrerHeader;
            this.standingType = standingType;
            this._EVENTVALIDATION = _EVENTVALIDATION;
            this._VIEWSTATE = _VIEWSTATE;
        }

        private int getStandingType() {
            return standingType;
        }

        private String getUrl() {
            return url;
        }

        private String getAcceptHeader() {
            return acceptHeader;
        }

        private String getReferrerHeader() {
            return referrerHeader;
        }

        private String get_EVENTVALIDATION() {
            return _EVENTVALIDATION;
        }

        private String get_VIEWSTATE() {
            return _VIEWSTATE;
        }
    }
}
