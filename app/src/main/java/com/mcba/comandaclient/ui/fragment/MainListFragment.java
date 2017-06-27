package com.mcba.comandaclient.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mcba.comandaclient.R;
import com.mcba.comandaclient.model.Product;
import com.mcba.comandaclient.presenter.ProductListPresenter;
import com.mcba.comandaclient.ui.adapter.MainListAdapter;

import java.util.ArrayList;

import io.realm.RealmList;

/**
 * Created by mac on 25/05/2017.
 */

public class MainListFragment extends BaseNavigationFragment<MainListFragment.MainListFragmentCallbacks> implements MainListAdapter.AdapterCallbacks, View.OnClickListener {

    private RecyclerView mRecyclerview;
    private MainListAdapter mAdapter;
    private ProductListPresenter mPresenter;
    private RealmList<Product> mProducts;
    private TextView mBtnGreen;


    public static MainListFragment newInstance() {

        MainListFragment fragment = new MainListFragment();
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
        mBtnGreen = (TextView) findViewById(R.id.btn_green);

    }

    @Override
    protected void setupFragment(Bundle savedInstanceState) {

        mAdapter = new MainListAdapter(getActivity(), this);
        mRecyclerview.setAdapter(mAdapter);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        mBtnGreen.setOnClickListener(this);

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
            case R.id.btn_green:
                mCallbacks.onGoToSelectProduct();
                break;
            default:
        }
    }

    @Override
    public void onItemPress(Product product) {

    }

    public interface MainListFragmentCallbacks {
        void onGoToSelectProduct();
    }
}
