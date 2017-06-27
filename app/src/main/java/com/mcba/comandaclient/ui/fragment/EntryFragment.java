package com.mcba.comandaclient.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.mcba.comandaclient.R;

/**
 * Created by mac on 25/06/2017.
 */

public class EntryFragment extends BaseNavigationFragment<EntryFragment.EntryFragmentCallbacks> {


    private LinearLayout mBtnNewComanda;

    public static EntryFragment newInstance() {

        EntryFragment fragment = new EntryFragment();
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.entry_fragment;
    }

    @Override
    protected void setViewReferences() {

        mBtnNewComanda = (LinearLayout) findViewById(R.id.btn_new_comanda);
    }

    @Override
    protected void setupFragment(Bundle savedInstanceState) {

        mBtnNewComanda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mCallbacks.onGoToMainListFromEntryFragment();
            }
        });

    }

    public interface EntryFragmentCallbacks {
        void onGoToMainListFromEntryFragment();
    }

    @Override
    public EntryFragment.EntryFragmentCallbacks getDummyCallbacks() {
        return new EntryFragment.EntryFragmentCallbacks() {
            @Override
            public void onGoToMainListFromEntryFragment() {

            }
        };
    }
}
