package com.mcba.comandaclient.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mcba.comandaclient.R;
import com.mcba.comandaclient.model.Client;
import com.mcba.comandaclient.presenter.ClientPresenter;
import com.mcba.comandaclient.presenter.ClientPresenterImpl;
import com.mcba.comandaclient.ui.adapter.ClientAdapter;
import com.mcba.comandaclient.ui.fragment.dialog.AddClientDialogFragment;
import com.mcba.comandaclient.ui.fragment.dialog.IAddClientDialogCallbacks;

import java.util.ArrayList;
import java.util.List;

import br.com.mauker.materialsearchview.MaterialSearchView;

/**
 * Created by mac on 14/01/2018.
 */

public class ClientActivity extends MainSearchClientActivity implements ClientView, ClientAdapter.AdapterCallbacks, IAddClientDialogCallbacks {

    private ClientPresenter mPresenter;
    private RecyclerView mRecyclerview;
    private ClientAdapter mAdapter;
    private TextView mSearchHint;
    private Toolbar mToolbar;

    private AddClientDialogFragment addClientDialogFragment;
    private List<Client> mList = new ArrayList<>();


    public static Intent getNewIntent(Context context) {
        return new Intent(context, ClientActivity.class);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_comanda);


        mPresenter = new ClientPresenterImpl(this);
        mPresenter.attachView();


        mRecyclerview = (RecyclerView) findViewById(R.id.recycler_comandas);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ClientAdapter(this, this);
        mRecyclerview.setAdapter(mAdapter);

        mSearchHint = (TextView) findViewById(R.id.txt_hint);
        mSearchHint.setText("Cliente");


        mPresenter.fetchClients();

        setupToolbar();
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }


    private void setupToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        if (mToolbar != null) {
            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_description
            );
            mToolbar.setNavigationIcon(drawable);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //open activity client
                }
            });
            setSupportActionBar(mToolbar);
            setupActionBar(getSupportActionBar());
        }
    }

    private void openDialog(){

        addClientDialogFragment = new AddClientDialogFragment();
        addClientDialogFragment.initDialog(this, this, 1);
        addClientDialogFragment.show(getFragmentManager(), "");
    }

    private void setupActionBar(ActionBar actionBar) {
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    @Override
    public void searchByTerm(String searchTerm) {

    }

    @Override
    public void setSearchHint(MaterialSearchView searchView) {

    }

    @Override
    public void getProgressSearch(ProgressBar progressSearch) {

    }

    @Override
    public void setOnSearchviewClose() {

    }

    @Override
    public void setInputType(MaterialSearchView searchView) {

    }

    @Override
    public void goToOpenAddClient() {

        addClientDialogFragment = new AddClientDialogFragment();
        addClientDialogFragment.initDialog(this, this, 1);
        addClientDialogFragment.show(getFragmentManager(), "");

    }

    @Override
    public void onItemPress(Client client) {

    }

    @Override
    public void showClientList(List<Client> clientList) {

        mList.clear();
        mList.addAll(clientList);
        mAdapter.setItems(clientList);
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void storeSuccess(boolean isSuccess) {
        mPresenter.fetchClients();

    }

    @Override
    public void clientExists(boolean exist, String name) {

        if (!exist) {
            mPresenter.storeClient(name);
            addClientDialogFragment.dismiss();
        } else {
            Toast.makeText(this, "Nombre cliente no debe repetirse", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDialogClientAccept(String clientName, int id, Dialog dialog) {
        mPresenter.validateExistingUser(clientName, mList);
    }

    @Override
    public void onDialogClientCancel() {

    }
}
