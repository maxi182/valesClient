package com.mcba.comandaclient.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mcba.comandaclient.R;
import com.mcba.comandaclient.model.Comanda;
import com.mcba.comandaclient.model.ComandaItem;
import com.mcba.comandaclient.model.ComandaProductItem;
import com.mcba.comandaclient.model.ItemFullName;
import com.mcba.comandaclient.model.Packaging;
import com.mcba.comandaclient.model.Product;
import com.mcba.comandaclient.presenter.ComandaListPresenter;
import com.mcba.comandaclient.presenter.ComandaListPresenterImpl;
import com.mcba.comandaclient.ui.ComandaListView;
import com.mcba.comandaclient.ui.adapter.MainListAdapter;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

/**
 * Created by mac on 25/05/2017.
 */

public class MainListFragment extends BaseNavigationFragment<MainListFragment.MainListFragmentCallbacks> implements ComandaListView, MainListAdapter.AdapterCallbacks, View.OnClickListener {

    public static final String COMANDA_ID = "comandaId";
    public static final String PRUDUCT_ID = "productId";
    public static final String ISNEWCOMANDA = "isnewComanda";
    public static final String PROVIDER_ID = "providerId";
    public static final String PACKAGE_PRICE = "packagePrice";
    public static final String TYPE_ID = "typeId";
    public static final String LASTITEM_ID = "lastItemId";
    public static final String ITEM_FULL_NAME = "item_full_name";
    public static final String PRICE = "price";
    public static final String CANT = "cant";

    private RecyclerView mRecyclerview;
    private MainListAdapter mAdapter;
    private ComandaListPresenter mPresenter;
    private RealmList<Product> mProducts;
    private TextView mBtnAddItem;
    private TextView mTxtComandaId;
    private TextView mBtnFinish;
    private ItemFullName mItemFullName;
    private int mComandaId;

    private List<ComandaItem> mComandaItemList = new ArrayList<>();


    private Comanda mComanda;

    public static MainListFragment newInstance(int nextComandaId) {

        Bundle args = new Bundle();
        args.putInt(COMANDA_ID, nextComandaId);
        args.putBoolean(ISNEWCOMANDA, true);
        MainListFragment fragment = new MainListFragment();
        fragment.setArguments(args);
        return fragment;

    }

    public static MainListFragment newInstance(int productId, int providerId, int typeId, double price, int cant, int currentComandaId, int lastItemId, double packagePrice, ItemFullName itemFullName) {

        Bundle args = new Bundle();
        args.putInt(PRUDUCT_ID, productId);
        args.putInt(PROVIDER_ID, providerId);
        args.putInt(COMANDA_ID, currentComandaId);
        args.putInt(TYPE_ID, typeId);
        args.putDouble(PRICE, price);
        args.putDouble(PACKAGE_PRICE, packagePrice);
        args.putInt(CANT, cant);
        args.putInt(LASTITEM_ID, lastItemId);
        args.putBoolean(ISNEWCOMANDA, false);
        args.putParcelable(ITEM_FULL_NAME, itemFullName);


        MainListFragment fragment = new MainListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.main_list_fragment;
    }

    @Override
    protected void setViewReferences() {

        mRecyclerview = (RecyclerView) findViewById(R.id.recycler_selection);
        mBtnAddItem = (TextView) findViewById(R.id.btn_add_item);
        mTxtComandaId = (TextView) findViewById(R.id.txtcomanda_id);

    }

    @Override
    protected void setupFragment(Bundle savedInstanceState) {

        mAdapter = new MainListAdapter(getActivity(), this);
        mRecyclerview.setAdapter(mAdapter);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        mPresenter = new ComandaListPresenterImpl(this);
        mPresenter.attachView();

        mBtnAddItem.setOnClickListener(this);

        mComandaId = getArguments().getInt(COMANDA_ID);

        mItemFullName = getArguments().getParcelable(ITEM_FULL_NAME);

        //El fetch se debe llamar cuando viene de cantpricefragment

        if (!validateIsNewComanda()) {
            mPresenter.fetchItemsComanda(mComandaId);
        } else {
            mComandaId = mComandaId + 1;
        }
        //consultar comandas
    }

    @Override
    public void onFetchItemFail() {
        storeComanda();
    }

    private boolean validateIsNewComanda() {

        return getArguments().getBoolean(ISNEWCOMANDA);

    }

    @Override
    public void showComanda(Comanda comanda) {

        if (comanda != null) {
            mTxtComandaId.setText(String.valueOf(comanda.comandaId));
            mComanda = comanda;

            //  mAdapter.setItems(comanda.comandaItemList);
            //  mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showItemsComanda(RealmList<ComandaItem> items) {

        mComandaItemList.addAll(items);
        storeComanda();

    }

    @Override
    public void showLastComandaId(int id) {
    }

    @Override
    public void onStoreItemSuccess(boolean isSuccess) {

        if (isSuccess) {

            mPresenter.fetchComandaById(mComandaId);
            // mCallbacks.onGoToSelectProduct(mComandaId);

        } else {
            Log.e("StoreItem", "can not save item");
        }
    }

    @Override
    public void onStoreItemFail() {


    }


    private void storeComanda() {

        //Enviar todos los ids al interactor para guardar y los datos del nuevo item
        Comanda comanda = new Comanda();
        comanda.comandaId = mComandaId;

        ComandaItem comandaItem = new ComandaItem();
        comandaItem.itemId = getArguments().getInt(LASTITEM_ID) + 1;
        comandaItem.comandaId = mComandaId;
        comandaItem.mCant = getArguments().getInt(CANT);
        comandaItem.mPrice = getArguments().getDouble(PRICE);
        comandaItem.mTotal = comandaItem.mPrice * comandaItem.mCant;

        ComandaProductItem comandaProductItem = new ComandaProductItem();
        comandaProductItem.productItemId = comandaItem.itemId;
        comandaProductItem.productId = getArguments().getInt(PRUDUCT_ID);
        comandaProductItem.providerId = getArguments().getInt(PROVIDER_ID);
        comandaProductItem.productName = mItemFullName.productName;
        comandaProductItem.providerName = mItemFullName.providerName;
        comandaProductItem.typeName = mItemFullName.productTypeName;
        comandaProductItem.packaging = new Packaging();
        comandaProductItem.packaging.value = getArguments().getDouble(PACKAGE_PRICE);
        comandaProductItem.packaging.isFree = comandaProductItem.packaging.value > 0 ? false : true;

        comandaItem.mProductItem = comandaProductItem;

        comanda.comandaItemList = new RealmList<>();

        if (mComandaItemList != null && !mComandaItemList.isEmpty()) {
            comanda.comandaItemList.addAll(mComandaItemList);
        }
        comanda.comandaItemList.add(comandaItem);

        mPresenter.storeComanda(comanda);
    }

    @Override
    public MainListFragmentCallbacks getDummyCallbacks() {
        return new MainListFragmentCallbacks() {
            @Override
            public void onGoToSelectProduct(int currentComandaId) {

            }
        };
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_add_item:
                mCallbacks.onGoToSelectProduct(mComandaId);
                break;
            case R.id.btn_finish:
                // storeComanda();
                break;
            default:
        }
    }

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void onItemPress(Product product) {

    }

    public interface MainListFragmentCallbacks {
        void onGoToSelectProduct(int currentComandaId);
    }
}
