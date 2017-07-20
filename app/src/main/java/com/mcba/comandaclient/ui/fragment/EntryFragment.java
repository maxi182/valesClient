package com.mcba.comandaclient.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mcba.comandaclient.R;
import com.mcba.comandaclient.model.Comanda;
import com.mcba.comandaclient.presenter.ComandaListPresenter;
import com.mcba.comandaclient.presenter.ComandaListPresenterImpl;
import com.mcba.comandaclient.ui.ComandaListView;
import com.mcba.comandaclient.ui.adapter.MainListAdapter;

/**
 * Created by mac on 25/06/2017.
 */

public class EntryFragment extends BaseNavigationFragment<EntryFragment.EntryFragmentCallbacks> implements ComandaListView {


    private LinearLayout mBtnNewComanda;
    private ComandaListPresenter mPresenter;
    private int mNextComandaId;


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

        mPresenter = new ComandaListPresenterImpl(this);
        mPresenter.attachView();

        mPresenter.fetchLastComanda();

        mBtnNewComanda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mCallbacks.onGoToMainListFromEntryFragment(mNextComandaId);
            }
        });

    }

    @Override
    public void showItemsComanda(Comanda comanda) {

    }

    @Override
    public void showLastComandaId(int id) {

        mNextComandaId = id;
        Toast.makeText(getActivity(), String.valueOf(id), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onStoreItemSuccess(boolean isSuccess) {

    }

    public interface EntryFragmentCallbacks {
        void onGoToMainListFromEntryFragment(int nextComandaId);
    }


    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public EntryFragment.EntryFragmentCallbacks getDummyCallbacks() {
        return new EntryFragment.EntryFragmentCallbacks() {
            @Override
            public void onGoToMainListFromEntryFragment(int nextComandaId) {

            }
        };
    }
}
