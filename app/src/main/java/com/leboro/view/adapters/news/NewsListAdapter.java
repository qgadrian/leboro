package com.leboro.view.adapters.news;

import java.util.List;

import com.android.volley.toolbox.NetworkImageView;
import com.leboro.MainActivity;
import com.leboro.R;
import com.leboro.model.news.News;
import com.leboro.view.adapters.BaseArrayAdapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
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
        TextView newsProviderLabel = (TextView) view.findViewById(R.id.newsProviderLabel);

        newsImage.setColorFilter(Color.argb(123, 0, 0, 0));

        News news = elements.get(position);

        newsImage.setImageUrl(news.getImageUrl(), imageLoader);
        newsImage.setDefaultImageResId(R.drawable.loading_background);

        newsTitle.setText(news.getTitle());

        newsProviderLabel.setText(news.getKind().getProviderNameResourceId());
        setNewsProviderBackgroundColor(newsProviderLabel, news);

        return view;
    }

    private void setNewsProviderBackgroundColor(TextView newsProviderLabel, News news) {
        GradientDrawable mDrawable = (GradientDrawable) MainActivity.context.getResources()
                .getDrawable(R.drawable.tag_provider_label);
        int color = ContextCompat.getColor(MainActivity.context, news.getKind().getProviderLabelBackgroundColorId());
        mDrawable.setColor(color);
        newsProviderLabel.setBackground(mDrawable);
    }
}
