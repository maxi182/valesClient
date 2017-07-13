package com.mcba.comandaclient.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mcba.comandaclient.R;
import com.mcba.comandaclient.model.Comanda;
import com.mcba.comandaclient.model.ComandaItem;
import com.mcba.comandaclient.model.ComandaProductItem;
import com.mcba.comandaclient.model.Product;
import com.mcba.comandaclient.presenter.ComandaListPresenter;
import com.mcba.comandaclient.presenter.ComandaListPresenterImpl;
import com.mcba.comandaclient.ui.ComandaListView;
import com.mcba.comandaclient.ui.adapter.MainListAdapter;

import java.util.ArrayList;

import io.realm.RealmList;

/**
 * Created by mac on 25/05/2017.
 */

public class MainListFragment extends BaseNavigationFragment<MainListFragment.MainListFragmentCallbacks> implements ComandaListView, MainListAdapter.AdapterCallbacks, View.OnClickListener {

    public static final String COMANDA_ID = "comandaId";

    private RecyclerView mRecyclerview;
    private MainListAdapter mAdapter;
    private ComandaListPresenter mPresenter;
    private RealmList<Product> mProducts;
    private TextView mBtnAddItem;
    private TextView mBtnFinish;
    private int mComandaId;


    private Comanda mComanda;

    public static MainListFragment newInstance(int nextComandaId) {


        Bundle args = new Bundle();
        args.putInt(COMANDA_ID, nextComandaId);
        MainListFragment fragment = new MainListFragment();
        fragment.setArguments(args);
        return fragment;

    }

    public static MainListFragment newInstance(int productId, int providerId) {

        MainListFragment fragment = new MainListFragment();
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

    }

    @Override
    protected void setupFragment(Bundle savedInstanceState) {

        mAdapter = new MainListAdapter(getActivity(), this);
        mRecyclerview.setAdapter(mAdapter);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        mPresenter = new ComandaListPresenterImpl(this);
        mPresenter.attachView();

        mComandaId = getArguments().getInt(COMANDA_ID);


        mBtnAddItem.setOnClickListener(this);

        Product product = new Product();
        product.name = "Banana";
        ArrayList<Product> produtlist = new ArrayList<>();
        produtlist.add(product);
        produtlist.add(product);
        produtlist.add(product);
        produtlist.add(product);
        produtlist.add(product);
        produtlist.add(product);

        produtlist.add(product);
        produtlist.add(product);
        mAdapter.setItems(produtlist);

        mPresenter.fetchComandaById(1);

        //consultar comandas

    }


    @Override
    public void showItemsComanda(Comanda comanda) {

        mComanda = comanda;
    }

    @Override
    public void showLastComandaId(int id) {


    }

    private void storeComanda() {

        //Enviar todos los ids al interactor para guardar y los datos del nuevo item
        Comanda comanda = new Comanda();
        comanda.comandaId = 1;

        ComandaItem comandaItem = new ComandaItem();
        comandaItem.itemId = 2;
        comandaItem.mCant = 2;
        comandaItem.mPrice = 200;
        comandaItem.mTotal = 400;


        ComandaProductItem comandaProductItem = new ComandaProductItem();
        comandaProductItem.productId = 1;
        comandaItem.mProductItem = comandaProductItem;

        comanda.comandaItemList = new RealmList<>();

        if (mComanda != null) {
            comanda.comandaItemList.addAll(mComanda.comandaItemList);
        }
        comanda.comandaItemList.add(comandaItem);

        mPresenter.storeComanda(comanda);
    }

    @Override
    public MainListFragmentCallbacks getDummyCallbacks() {
        return new MainListFragmentCallbacks() {
            @Override
            public void onGoToSelectProduct() {

            }
        };
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_finish:
                mCallbacks.onGoToSelectProduct();
                break;
            case R.id.btn_add_item:
                storeComanda();
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
        void onGoToSelectProduct();
    }
}
