package com.leboro.view.adapters;

import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.leboro.service.ApplicationServiceProvider;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;

public abstract class BaseArrayAdapter<T> extends ArrayAdapter<T> {

    protected final static ImageLoader imageLoader = ApplicationServiceProvider.getNetworkImageLoaderService()
            .getImageLoader();

    protected static LayoutInflater inflater = null;

    protected List<T> elements;

    protected final int rowLayoutResource;

    public BaseArrayAdapter(Context context, int rowLayoutResource, List<T> elements) {
        super(context, rowLayoutResource);
        this.rowLayoutResource = rowLayoutResource;
        this.elements = elements;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void updateDataAndNotify(List<T> elements) {
        this.elements = elements;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return elements.size();
    }

    @Override
    public T getItem(int position) {
        return elements.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
