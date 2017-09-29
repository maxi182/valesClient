package com.mcba.comandaclient.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mcba.comandaclient.R;
import com.mcba.comandaclient.model.Comanda;
import com.mcba.comandaclient.model.ComandaItem;
import com.mcba.comandaclient.presenter.ComandaListPresenter;
import com.mcba.comandaclient.presenter.ComandaListPresenterImpl;
import com.mcba.comandaclient.ui.ComandaListView;
import com.mcba.comandaclient.ui.ComandaSearchActivity;
import com.mcba.comandaclient.ui.SettingsActivity;
import com.mcba.comandaclient.ui.adapter.MainListAdapter;
import com.mcba.comandaclient.utils.Constants;
import com.mcba.comandaclient.utils.StorageProvider;

import io.realm.RealmList;

/**
 * Created by mac on 25/06/2017.
 */

public class EntryFragment extends BaseNavigationFragment<EntryFragment.EntryFragmentCallbacks> implements ComandaListView {


    private LinearLayout mBtnNewComanda;
    private LinearLayout mBtnComandaList;
    private LinearLayout mBtnSettings;
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
        mBtnComandaList = (LinearLayout) findViewById(R.id.btn_comanda_list);
        mBtnSettings = (LinearLayout) findViewById(R.id.btn_settings);

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

        mBtnComandaList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(ComandaSearchActivity.getNewIntent(getActivity()));

            }
        });

        mBtnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(SettingsActivity.getNewIntent(getActivity()));

            }
        });


    }

    @Override
    public void showComanda(Comanda comanda) {

    }

    @Override
    public void showItemsComanda(RealmList<ComandaItem> items) {

    }

    @Override
    public void onFetchComandaItemsForPrint(StringBuilder stringBuilderItems, StringBuilder stringBuilderSubTotales, StringBuilder stringBuilderTotal, StringBuilder stringBuilderCopyItems) {

    }

    @Override
    public void showLastComandaId(int id) {

        mNextComandaId = id;
        StorageProvider.savePreferences(Constants.RESTOREMAIN, false);

        Toast.makeText(getActivity(), String.valueOf(id), Toast.LENGTH_SHORT).show();
        StorageProvider.savePreferences(Constants.LAST_COMANDA_ID, id);

    }

    @Override
    public void onTotalesFetched(double total, double senia, double bultos) {

    }

    @Override
    public void onStoreItemSuccess(boolean isSuccess) {

    }

    @Override
    public void onDeleteItemSuccess(boolean isSuccess) {

    }

    @Override
    public void onDeleteComandaSuccess(boolean isSuccess) {

    }

    @Override
    public void onStoreItemFail() {

    }

    @Override
    public void onFetchItemFail() {

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
