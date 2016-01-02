package com.leboro.view.fragment.classification;

import com.leboro.R;
import com.leboro.model.classification.Position;
import com.leboro.util.Constants;
import com.leboro.view.adapters.classification.ClassificationListAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ClassificationFragment extends Fragment {

    private View mView;

    private ListView classificationListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.classification_fragment, container, false);

        initializeViews();
        initializeAdapters();

        return mView;
    }

    private void initializeViews() {
        classificationListView = (ListView) mView.findViewById(R.id.classificationListView);
    }

    private void initializeAdapters() {
        ClassificationListAdapter classificationListAdapter =
                new ClassificationListAdapter(mView.getContext(), R.layout.classification_list_view_row,
                        getArguments().<Position>getParcelableArrayList(Constants.BUNDLE_ARG_POSITIONS));
        classificationListView.setAdapter(classificationListAdapter);
    }
}
