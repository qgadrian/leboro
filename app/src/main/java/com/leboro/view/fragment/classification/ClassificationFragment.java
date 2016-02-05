package com.leboro.view.fragment.classification;

import java.util.Collections;
import java.util.List;

import com.leboro.R;
import com.leboro.model.classification.Position;
import com.leboro.service.ApplicationServiceProvider;
import com.leboro.util.cache.ApplicationCacheManager;
import com.leboro.view.adapters.classification.ClassificationListAdapter;
import com.leboro.view.fragment.LoadableFragment;
import com.leboro.view.listeners.DataLoadedListener;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ClassificationFragment extends LoadableFragment implements DataLoadedListener<Position> {

    private View mView;

    private ListView classificationListView;

    private ClassificationListAdapter classificationListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.classification_fragment, container, false);

        initializeViews();
        initializeAdapters();
        initializeData();

        return mView;
    }

    private void initializeData() {
        final DataLoadedListener<Position> dataLoadedListener = this;
        AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                ApplicationServiceProvider.getStatisticsService().getClassification(dataLoadedListener);
            }
        });

    }

    private void initializeViews() {
        classificationListView = (ListView) mView.findViewById(R.id.classificationListView);
    }

    private void initializeAdapters() {
        classificationListAdapter =
                new ClassificationListAdapter(mView.getContext(), R.layout.classification_list_view_row,
                        Collections.<Position>emptyList());
        classificationListView.setAdapter(classificationListAdapter);
    }

    @Override
    public void onDataLoadedIntoCache() {
        if (isVisible()) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    removeLoadingLayout(mView);
                    classificationListAdapter.updateDataAndNotifify(ApplicationCacheManager.getClassification());
                    classificationListView.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    @Override
    public void onDataLoaded(Position data) {

    }

    @Override
    public void onDataLoaded(final List<Position> positions) {

    }
}
