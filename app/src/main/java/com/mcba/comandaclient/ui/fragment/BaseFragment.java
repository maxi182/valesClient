package com.mcba.comandaclient.ui.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mcba.comandaclient.R;
import com.mcba.comandaclient.ui.MainActivity;
import com.mcba.comandaclient.utils.Utils;




/**
 * Created by ash.mosaheb on 11/08/2015.
 */
public abstract class BaseFragment extends Fragment {
    public final String TAG = this.getClass().getSimpleName();

    protected View mMainFragmentView;
    private ProgressBar mContentProgressBar;
    private View mContentMainContainerView;
    private ProgressDialog mProgressDialog;

    protected abstract int getLayoutResource();

    /**
     * Sets up view references. Called in the {@link Fragment#onActivityCreated(Bundle)}
     */
    protected abstract void setViewReferences();

    /**
     * Generic setup of the fragment. Called in the {@link Fragment#onActivityCreated(Bundle)}
     * after the {@link BaseFragment#setViewReferences()} method.
     */
    protected abstract void setupFragment(Bundle savedInstanceState);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mMainFragmentView = inflater.inflate(getLayoutResource(), container, false);

        mContentMainContainerView = findViewById(R.id.container_body);
        mContentProgressBar = (ProgressBar) findViewById(R.id.content_progress_bar);

        setViewReferences();
        setupFragment(savedInstanceState);


        return mMainFragmentView;
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);

        // Always ensure this fragment comes from base activity
        if (!(activity instanceof MainActivity)) {
            throw new ClassCastException(activity.toString() + " must be a " + MainActivity.class.getSimpleName());
        }
    }

    /* This code moved into onCreateView
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setViewReferences();
        setupFragment(savedInstanceState);
    }
    */

    protected View findViewById(int id) {
        return mMainFragmentView.findViewById(id);
    }


    public void showToast(@NonNull String message) {
        Log.i(TAG, message);
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void showDialog(String title, @Nullable String message) {
        Log.i(TAG, "Dialog message: " + message);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext())
                .setTitle(title)
                .setPositiveButton(R.string.dialog_ok, null);

        if (message != null) {
            dialogBuilder.setMessage(message);
        }
        dialogBuilder.show();
    }

    public void showDialog(int titleResId, int messageResId) {
        Log.i(TAG, "Dialog message: " + getString(messageResId));
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext())
                .setTitle(titleResId)
                .setPositiveButton(R.string.dialog_ok, null);

        if (messageResId != 0) {
            dialogBuilder.setMessage(messageResId);
        }
        dialogBuilder.show();
    }



    public void showProgressDialog(String title, String message) {
        if ((mProgressDialog != null) && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        mProgressDialog = ProgressDialog.show(getContext(), title, message, false);
    }

    public void hideProgressDialog() {
        if ((mProgressDialog != null) && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    protected void setTitle(int titleId) {
        getActivity().setTitle(titleId);
    }

    protected void setTitle(CharSequence title) {
        getActivity().setTitle(title);
    }

    protected boolean isNetworkConnectionUp() {
        if (!Utils.isNetworkConnectionAvailable(getActivity())) {
            Toast.makeText(getActivity(), "Network connection not available", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    protected void showProgressBar() {
        if (mContentProgressBar != null) {
            mContentProgressBar.setVisibility(View.VISIBLE);
        }
        if (mContentMainContainerView != null) {
            mContentMainContainerView.setVisibility(View.GONE);
        }
    }

    protected void hideProgressBar() {
        if (mContentProgressBar != null) {
            mContentProgressBar.setVisibility(View.GONE);
        }
        if (mContentMainContainerView != null) {
            mContentMainContainerView.setVisibility(View.VISIBLE);
        }
    }

    public View getContentMainContainerView() {
        return mContentMainContainerView;
    }

    public ProgressBar getContentProgressBar() {
        return mContentProgressBar;
    }

    public MainActivity getBaseActivity() {
        Activity activity = getActivity();
        if (activity instanceof MainActivity) {
            return (MainActivity) activity;
        } else {
            return null;
        }
    }
}
