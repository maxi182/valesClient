package com.mcba.comandaclient.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.mcba.comandaclient.R;
import com.mcba.comandaclient.model.NameId;
import com.mcba.comandaclient.model.ProductType;
import com.mcba.comandaclient.model.Provider;
import com.mcba.comandaclient.presenter.AddProviderPresenter;
import com.mcba.comandaclient.presenter.AddProviderPresenterImpl;
import com.mcba.comandaclient.ui.adapter.AddProductAdapter;
import com.mcba.comandaclient.ui.adapter.AddProviderAdapter;
import com.mcba.comandaclient.ui.adapter.ClientAdapter;
import com.mcba.comandaclient.ui.adapter.HeaderListModel;
import com.mcba.comandaclient.ui.adapter.ListModel;
import com.mcba.comandaclient.ui.adapter.ProviderListModel;
import com.mcba.comandaclient.ui.fragment.dialog.AddClientDialogFragment;
import com.mcba.comandaclient.ui.fragment.dialog.AddItemDialogFragment;
import com.mcba.comandaclient.ui.fragment.dialog.IAddItemCallbacks;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 05/05/2018.
 */

public class AddProviderActivity extends AppCompatActivity implements AddProviderView, AddProviderAdapter.AdapterCallbacks, IAddItemCallbacks {

    private final int PROVIDER = 1;
    private final int AMOUNT = 2;

    private Toolbar mToolbar;
    private AddProviderPresenter mPresenter;
    private TextView mTxtHint;
    private AddProductAdapter adapter;
    private RecyclerView mRecyclerviewProvider;

    private List<Provider> providersList = new ArrayList<>();
    private AddItemDialogFragment addItemDialogFragment;

    public static Intent getNewIntent(Context context) {
        return new Intent(context, AddProviderActivity.class);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_provider);

        mTxtHint = (TextView) findViewById(R.id.txt_hint);

        // mProviderAutoComplete = (AutoCompleteTextView) findViewById(R.id.provider_name_edit_text);

        mRecyclerviewProvider = (RecyclerView) findViewById(R.id.recycler_providers);

        mRecyclerviewProvider.setLayoutManager(new LinearLayoutManager(this));
        mPresenter = new AddProviderPresenterImpl(this);
        mPresenter.attachView();
        adapter = new AddProductAdapter(this);//new AddProductAdapter(this);
        mRecyclerviewProvider.setAdapter(adapter);


        //adapter.setItems(providersList);


        mPresenter.fetchProviders();
        mPresenter.fetchProducts();


        setupToolbar();
        displayTitle();
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }


    private void openDialog(int dialogToOpen) {

        addItemDialogFragment = new AddItemDialogFragment();
        addItemDialogFragment.initDialog(this, this, dialogToOpen);
        addItemDialogFragment.show(getFragmentManager(), "");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.activity_provider, menu);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_add_provider).setTitle("Add card");

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_provider:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void setupToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            setupActionBar(getSupportActionBar());
        }
    }

    private void displayTitle() {

        mTxtHint.setText(getResources().getString(R.string.add_new_item));
        mTxtHint.setTextColor(ContextCompat.getColor(this, R.color.white));
        mTxtHint.setTypeface(Typeface.DEFAULT_BOLD);
        mTxtHint.setTextSize(20);

    }

    private void setupActionBar(ActionBar actionBar) {
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
    }

    @Override
    public void onProvidersFetched(List<Provider> providers) {

        providersList.addAll(providers);

        List<ListModel> list = new ArrayList<>();

        for (Provider provider : providers) {

            ProviderListModel providerListModel = new ProviderListModel(provider);
            list.add(providerListModel);
        }

        adapter.setItems(list);
        // adapter.notifyDataSetChanged();
        // List<Provider> providersList = new ArrayList<>();
        //  providersList.addAll(providers);

    }

    @Override
    public void onProductsFetched(List<NameId> products) {

    }

    @Override
    public void onItemPress(Provider provider) {


    }

    @Override
    public void onAddItem(String name, int dialogOpened) {


    }
}
