package com.leboro.view.listeners;

public interface DataLoadedListener<T> {

    void onDataLoaded();

    void onDataLoaded(T data);

}
