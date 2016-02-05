package com.leboro.view.adapters.news;

import java.util.List;

import com.android.volley.toolbox.NetworkImageView;
import com.leboro.R;
import com.leboro.model.news.News;
import com.leboro.view.adapters.BaseArrayAdapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NewsListAdapter extends BaseArrayAdapter<News> {

    public NewsListAdapter(Context context, int resource, List<News> news) {
        super(context, resource, news);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(rowLayoutResource, null);
        }

        NetworkImageView newsImage = (NetworkImageView) view.findViewById(R.id.newsArticleImage);
        TextView newsTitle = (TextView) view.findViewById(R.id.newsTitle);
        newsImage.setColorFilter(Color.argb(123, 0, 0, 0));

        News news = elements.get(position);

        newsImage.setImageUrl(news.getImageUrl(), imageLoader);
        newsTitle.setText(news.getTitle());

        return view;
    }
}
