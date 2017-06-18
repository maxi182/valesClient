package com.mcba.comandaclient.ui.interfaces;

/**
 * Created by maximilianoferraiuolo on 22/06/2016.
 */
public interface ISearchCallbacks {

    void onSearchRequestCompleted();

    void onSearchRequestFailed(Exception e);
}
