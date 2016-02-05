package com.leboro.view.listeners;

import java.util.List;

public interface DataLoadedListener<T> {

    void onDataLoadedIntoCache();

    void onDataLoaded(T data);

    void onDataLoaded(List<T> data);

}
