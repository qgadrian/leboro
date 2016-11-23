package com.leboro.view.fragment.classification;

import java.util.Collections;

import com.leboro.MainActivity;
import com.leboro.R;
import com.leboro.model.classification.Position;
import com.leboro.service.ApplicationServiceProvider;
import com.leboro.util.cache.ApplicationCacheManager;
import com.leboro.view.adapters.classification.ClassificationListAdapter;
import com.leboro.view.fragment.LoadableFragment;
import com.leboro.view.listeners.CacheDataLoadedListener;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ClassificationFragment extends LoadableFragment implements CacheDataLoadedListener {

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
        final CacheDataLoadedListener dataLoadedListener = this;
        new Thread(new Runnable() {
            @Override
            public void run() {
                ApplicationServiceProvider.getStatisticsService().getClassification(dataLoadedListener);
            }
        }).start();

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
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.navigation_drawer_classification));
    }
}
