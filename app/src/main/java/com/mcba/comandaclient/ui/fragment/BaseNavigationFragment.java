package com.mcba.comandaclient.ui.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;


/**
 * Created by maximilianoferraiuolo on 05/07/2016.
 */
public abstract class BaseNavigationFragment<T> extends BaseFragment {

    protected T mCallbacks;

    @SuppressWarnings("unchecked")
    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (T) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement fragment's callbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = getDummyCallbacks();
    }

    public abstract T getDummyCallbacks();

    // Used it if the fragment doesn't declare their own navigation callbacks
    public static interface VoidCallbacks {
    }

    protected static VoidCallbacks VOID_CALLBACKS = new VoidCallbacks() {
    };

}