package com.leboro.view.helper.news;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.leboro.R;
import com.leboro.model.news.News;
import com.leboro.service.ApplicationServiceProvider;

import android.graphics.Color;
import android.view.View;

public class NewsHelper {

    private static ImageLoader imageLoader = ApplicationServiceProvider.getNetworkImageLoaderService()
            .getImageLoader();

    public static void loadNewsImage(View view, News news, int newsImageResourceId) {
        NetworkImageView newsImage = (NetworkImageView) view.findViewById(newsImageResourceId);
        NewsHelper.addOverlayBackgroundToNewsImage(newsImage);
        newsImage.setImageUrl(news.getImageUrl(), imageLoader);
        newsImage.setDefaultImageResId(R.drawable.loading_background);
    }

    public static void addOverlayBackgroundToNewsImage(NetworkImageView newsImage) {
        newsImage.setColorFilter(Color.argb(123, 0, 0, 0));
    }
}
