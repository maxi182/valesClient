package com.mcba.comandaclient.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.mcba.comandaclient.R;
import com.mcba.comandaclient.model.NameId;
import com.mcba.comandaclient.model.Product;
import com.mcba.comandaclient.model.ProductType;
import com.mcba.comandaclient.model.Provider;
import com.mcba.comandaclient.presenter.AddProviderPresenter;
import com.mcba.comandaclient.presenter.AddProviderPresenterImpl;
import com.mcba.comandaclient.ui.adapter.AddProductAdapter;
import com.mcba.comandaclient.ui.adapter.AddProviderAdapter;
import com.mcba.comandaclient.ui.adapter.HeaderListModel;
import com.mcba.comandaclient.ui.adapter.ListModel;
import com.mcba.comandaclient.ui.adapter.ProductListModel;
import com.mcba.comandaclient.ui.adapter.ProductTypeListModel;
import com.mcba.comandaclient.ui.adapter.ProviderListModel;
import com.mcba.comandaclient.ui.fragment.dialog.AddItemDialogFragment;
import com.mcba.comandaclient.ui.fragment.dialog.IAddItemCallbacks;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 05/05/2018.
 */

public class AddProviderActivity extends AppCompatActivity implements AddProviderView, AddProviderAdapter.AdapterCallbacks, IAddItemCallbacks, AddProductAdapter.adapterCallbacks {

    private final int PROVIDER = 1;
    private final int AMOUNT = 2;

    private Toolbar mToolbar;
    private AddProviderPresenter mPresenter;
    private TextView mTxtHint;
    private AddProductAdapter adapter;
    private RecyclerView mRecyclerviewProvider;
    private List<Provider> mProvider = new ArrayList<>();

    private List<ListModel> mListModel = new ArrayList<>();
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
        adapter = new AddProductAdapter(this, this);//new AddProductAdapter(this);
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

        List<ListModel> list = new ArrayList<>();

        mProvider.addAll(providers);
        list.add(new HeaderListModel("Proveedor", ListModel.TYPE.Companion.getPROVIDER()));
        for (Provider provider : providers) {

            ProviderListModel providerListModel = new ProviderListModel(provider);
            list.add(providerListModel);
        }
        mListModel.addAll(list);
        // for (Provider provider : providers)
        adapter.setItems(mListModel);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onProductsFetched(List<NameId> products) {
//
//        List<ListModel> list = new ArrayList<>();
//        list.add(new HeaderListModel("Producto", ListModel.TYPE.Companion.getPRODUCT()));
//        for (NameId nameId : products) {
//            ProductListModel productListModel = new ProductListModel(nameId);
//            list.add(productListModel);
//        }
//        listModel.addAll(list);
//        adapter.setItems(listModel);

    }

    @Override
    public void onItemPress(Provider provider) {


    }

    @Override
    public void onAddItem(String name, int dialogOpened) {


    }

    private void removeProducts() {

        if (mListModel.size() > mProvider.size()) {
            for (int i = mListModel.size() - 1; i >= 0; i--) {

                if (mListModel.get(i).getUpdateType().equals(ListModel.TYPE.Companion.getPRODUCT())) {

                    mListModel.remove(i);
                }
            }
        }
    }

    @Override
    public void onItemSelected(@NotNull ListModel listModel) {

        removeProducts();

        List<Product> mProducts = new ArrayList<>();
        List<ProductType> mProductType = new ArrayList<>();
        if (listModel.getUpdateType().equals(ListModel.TYPE.Companion.getPROVIDER())) {
            for (int i = 0; i < mProvider.size(); i++) {
                if (mProvider.get(i).providerId == listModel.getId()) {
                    mProducts.addAll(mProvider.get(i).products);
                }
            }
            for (Product product : mProducts) {
                ProductListModel productListModel = new ProductListModel(product);
                mListModel.add(productListModel);
            }
        }
        if (listModel.getUpdateType().equals(ListModel.TYPE.Companion.getPRODUCT())) {
            for (int i = 0; i < mProducts.size(); i++) {
                if (mProducts.get(i).productId == listModel.getId()) {
                    mProductType.addAll(mProducts.get(i).types);
                }
            }
            for (ProductType productType : mProductType) {
                ProductTypeListModel productTypeListModel = new ProductTypeListModel(productType);
                mListModel.add(productTypeListModel);
            }
        }

        adapter.notifyDataSetChanged();

        Toast.makeText(this, listModel.getUpdateType(), Toast.LENGTH_SHORT).show();
    }
}
