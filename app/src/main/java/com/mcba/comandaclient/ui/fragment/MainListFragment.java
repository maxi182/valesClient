package com.mcba.comandaclient.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mcba.comandaclient.R;
import com.mcba.comandaclient.model.Product;
import com.mcba.comandaclient.ui.adapter.MainListAdapter;

import java.util.ArrayList;

/**
 * Created by mac on 25/05/2017.
 */

public class MainListFragment extends BaseNavigationFragment<MainListFragment.MainListFragmentCallbacks> implements MainListAdapter.AdapterCallbacks {

    private RecyclerView mRecyclerview;
    private MainListAdapter mAdapter;


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

        Product product = new Product();
        product.name = "Banana";
        ArrayList<Product> produtlist = new ArrayList<>();
        produtlist.add(product);
        produtlist.add(product);
        produtlist.add(product);
        produtlist.add(product);
        produtlist.add(product);
        produtlist.add(product);
        produtlist.add(product);produtlist.add(product);
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
    public void onItemPress(int pos) {

    }

    public interface MainListFragmentCallbacks {
        void onGoToSelectProduct();
    }
}
