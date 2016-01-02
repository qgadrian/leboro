package com.leboro.service.volley;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;

public interface NetworkImageLoaderService {

    RequestQueue getRequestQueue();

    ImageLoader getImageLoader();

    <T> void addToRequestQueue(Request<T> req, String tag);

    <T> void addToRequestQueue(Request<T> req);

    void cancelPendingRequests(Object tag);
}
