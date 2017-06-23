package com.mcba.comandaclient.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.mcba.comandaclient.R;
import com.mcba.comandaclient.model.Product;
import com.mcba.comandaclient.model.Provider;
import com.mcba.comandaclient.model.ProviderList;
import com.mcba.comandaclient.presenter.ProductListPresenter;
import com.mcba.comandaclient.presenter.ProductListPresenterImpl;
import com.mcba.comandaclient.ui.ProductsListView;
import com.mcba.comandaclient.ui.adapter.ProductSelectionAdapter;

import java.util.List;

import io.realm.RealmList;

/**
 * Created by mac on 19/06/2017.
 */

public class ProductSelectionFragment extends BaseNavigationFragment<ProductSelectionFragment.ProductSelectionFragmentCallbacks> implements ProductsListView, ProductSelectionAdapter.AdapterCallbacks {

    private RecyclerView mRecyclerview;
    private ProductSelectionAdapter mAdapter;
    private ProductListPresenter mPresenter;
    private FrameLayout mProgress;


    public static ProductSelectionFragment newInstance() {

        ProductSelectionFragment fragment = new ProductSelectionFragment();
        return fragment;

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.product_selection_fragment;
    }

    @Override
    protected void setViewReferences() {

        mRecyclerview = (RecyclerView) findViewById(R.id.recycler_product_selection);
        mProgress = (FrameLayout) findViewById(R.id.progress);

    }

    @Override
    protected void setupFragment(Bundle savedInstanceState) {

        mPresenter = new ProductListPresenterImpl(this);
        mPresenter.attachView();

        mAdapter = new ProductSelectionAdapter(getActivity(), this);
        mRecyclerview.setAdapter(mAdapter);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        mPresenter.getProducts();

    }

    @Override
    public void showDataResponse(RealmList<ProviderList> providers, RealmList<Product> products) {

        mAdapter.setItems(products);
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void showProvidersResponse(List<Provider> providers) {

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
        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgress.setVisibility(View.GONE);
    }

    @Override
    public void onItemPress() {

    }

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void onItemPress(Product product) {

        mCallbacks.onGoToSelectProvider(product.productId);

    }


    public interface ProductSelectionFragmentCallbacks {
        void onGoToSelectProvider(int pId);
    }

    @Override
    public ProductSelectionFragment.ProductSelectionFragmentCallbacks getDummyCallbacks() {
        return new ProductSelectionFragment.ProductSelectionFragmentCallbacks() {
            @Override
            public void onGoToSelectProvider(int pId) {

            }
        };
    }

}
