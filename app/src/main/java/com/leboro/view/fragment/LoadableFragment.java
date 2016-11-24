package com.leboro.view.fragment;

import android.support.v4.app.Fragment;
import android.view.View;

import com.leboro.R;

public abstract class LoadableFragment extends Fragment {

    @Override
    public void onResume() {
        super.onResume();
        updateActionAndNavigationBar();
    }

    protected abstract void updateActionAndNavigationBar();

    protected void removeLoadingLayout(View contextView) {
        removeLoadingLayout(contextView, null, R.id.loadingFragment);
    }

    protected void removeLoadingLayout(View contextView, int loadingLayoutResource) {
        removeLoadingLayout(contextView, null, loadingLayoutResource);
    }

    protected void removeLoadingLayoutAndShowResource(View contextView, int viewToDisplay) {
        removeLoadingLayout(contextView, contextView.findViewById(viewToDisplay), R.id.loadingFragment);
    }

    protected void removeLoadingLayoutAndShowResource(View contextView, View viewToDisplay) {
        removeLoadingLayout(contextView, viewToDisplay, R.id.loadingFragment);
    }

    private void removeLoadingLayout(View contextView, View viewToDisplay, int loadingLayoutResource) {
        View loadingView = contextView.findViewById(loadingLayoutResource);
        if (loadingView.getVisibility() != View.GONE) {
            loadingView.setVisibility(View.GONE);
            if (viewToDisplay != null) {
                viewToDisplay.setVisibility(View.VISIBLE);
            }
        }
    }
}
