package com.leboro.service.task.gameday;

import java.util.List;

import com.google.common.collect.Lists;
import com.leboro.util.http.HttpUtils;

import android.os.AsyncTask;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.EntityBuilder;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class GameDayHttpPostAsyncTask extends AsyncTask<GameDayHttpPostAsyncTask.ResultTaskArgument, Void, String> {

    @Override
    protected String doInBackground(ResultTaskArgument... params) {
        String url = params[0].getUrl();
        String acceptHeader = params[0].getAcceptHeader();
        String referrerHeader = params[0].getReferrerHeader();
        String gameDayId = params[0].getGameDayId();
        String kind = params[0].getKind();
        String season = params[0].getSeason();
        String _EVENTVALIDATION = params[0].get_EVENTVALIDATION();
        String _VIEWSTATE = params[0].get_VIEWSTATE();

        HttpClient client = HttpClientBuilder.create().build();

        HttpPost request = new HttpPost(url);
        request.setHeader("Accept", acceptHeader);
        request.setHeader("Referrer", referrerHeader);

        List<NameValuePair> pairs = Lists.newArrayList();
        pairs.add(new BasicNameValuePair("ctl00$jornadasDropDownList", gameDayId));
        pairs.add(new BasicNameValuePair("ctl00$gruposDropDownList", kind));
        pairs.add(new BasicNameValuePair("ctl00$temporadasDropDownList", season));
        pairs.add(new BasicNameValuePair("__VIEWSTATE", _VIEWSTATE));
        pairs.add(new BasicNameValuePair("__EVENTVALIDATION", _EVENTVALIDATION));
        HttpEntity entity = EntityBuilder.create().setParameters(pairs).setContentType(null).build();
        request.setEntity(entity);

        return HttpUtils.doRequest(client, request);
    }

    public static class ResultTaskArgument {

        private final String url;

        private final String acceptHeader;

        private final String referrerHeader;

        private final int gameDayId;

        private final int kind;

        private final int season;

        private final String _EVENTVALIDATION;

        private final String _VIEWSTATE;

        public ResultTaskArgument(int gameDayId, int kind, int season, String referrerHeader, String acceptHeader,
                String url, String _VIEWSTATE, String _EVENTVALIDATION) {
            this._VIEWSTATE = _VIEWSTATE;
            this._EVENTVALIDATION = _EVENTVALIDATION;
            this.season = season;
            this.kind = kind;
            this.gameDayId = gameDayId;
            this.referrerHeader = referrerHeader;
            this.acceptHeader = acceptHeader;
            this.url = url;
        }

        public String getUrl() {
            return url;
        }

        public String getAcceptHeader() {
            return acceptHeader;
        }

        public String getReferrerHeader() {
            return referrerHeader;
        }

        public String getGameDayId() {
            return String.valueOf(gameDayId);
        }

        public String getKind() {
            return String.valueOf(kind);
        }

        public String getSeason() {
            return String.valueOf(season);
        }

        public String get_EVENTVALIDATION() {
            return _EVENTVALIDATION;
        }

        public String get_VIEWSTATE() {
            return _VIEWSTATE;
        }
    }
}
