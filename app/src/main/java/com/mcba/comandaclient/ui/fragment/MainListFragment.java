package com.mcba.comandaclient.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mcba.comandaclient.R;
import com.mcba.comandaclient.model.Product;
import com.mcba.comandaclient.presenter.MainListPresenter;
import com.mcba.comandaclient.presenter.MainListPresenterImpl;
import com.mcba.comandaclient.ui.ProductsListView;
import com.mcba.comandaclient.ui.adapter.MainListAdapter;

import java.util.ArrayList;

import io.realm.RealmList;

/**
 * Created by mac on 25/05/2017.
 */

public class MainListFragment extends BaseNavigationFragment<MainListFragment.MainListFragmentCallbacks> implements ProductsListView, MainListAdapter.AdapterCallbacks {

    private RecyclerView mRecyclerview;
    private MainListAdapter mAdapter;
    private MainListPresenter mPresenter;


    public static MainListFragment newInstance() {

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

    }


    @Override
    protected void setupFragment(Bundle savedInstanceState) {

        mAdapter = new MainListAdapter(getActivity(), this);
        mRecyclerview.setAdapter(mAdapter);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        mPresenter = new MainListPresenterImpl(this);
        mPresenter.attachView();


        mPresenter.getProducts();

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
        produtlist.add(product);
        produtlist.add(product);
        produtlist.add(product);
        produtlist.add(product);

        produtlist.add(product);
        produtlist.add(product);
        mAdapter.setItems(produtlist);

     }

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
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
    public void onItemPress(int pos) {

    }

    @Override
    public void showProductListResponse(RealmList<Product> data) {

    }

    @Override
    public void showDetaiResponse(RealmList<Product> data) {

    }

    @Override
    public void onResponseFailed() {

    }

    @Override
    public void realmStoreCompleted() {

    }

    @Override
    public void realmStoreFailed() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onItemPress() {

    }

    public interface MainListFragmentCallbacks {
        void onGoToSelectProduct();
    }
}
