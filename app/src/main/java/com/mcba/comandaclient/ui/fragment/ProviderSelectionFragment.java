package com.mcba.comandaclient.ui.fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.mcba.comandaclient.R;
import com.mcba.comandaclient.model.ItemFullName;
import com.mcba.comandaclient.model.Product;
import com.mcba.comandaclient.model.ProductType;
import com.mcba.comandaclient.model.Provider;
import com.mcba.comandaclient.model.ProviderList;
import com.mcba.comandaclient.presenter.ProductListPresenter;
import com.mcba.comandaclient.presenter.ProductListPresenterImpl;
import com.mcba.comandaclient.ui.ProductsListView;
import com.mcba.comandaclient.ui.ProviderSelectionView;
import com.mcba.comandaclient.ui.adapter.ProviderSelectionAdapter;
import com.mcba.comandaclient.utils.Constants;
import com.mcba.comandaclient.utils.StorageProvider;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

/**
 * Created by mac on 21/06/2017.
 */

public class ProviderSelectionFragment extends BaseNavigationFragment<ProviderSelectionFragment.ProviderSelectionFragmentCallbacks> implements ProviderSelectionView, ProductsListView, ProviderSelectionAdapter.AdapterCallbacks {

    public static final String PRODUCT_ID = "productId";

    private RecyclerView mRecyclerview;
    private ProviderSelectionAdapter mAdapter;
    private ProductListPresenter mPresenter;
    private TextView mTxtPosProvider;
    private TextView mTxtPosProduct;
    private int mProductId;


    public static ProviderSelectionFragment newInstance(int productId) {

        Bundle args = new Bundle();
        args.putInt(PRODUCT_ID, productId);
        ProviderSelectionFragment fragment = new ProviderSelectionFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.provider_selection_fragment;
    }

    @Override
    protected void setViewReferences() {

        mRecyclerview = (RecyclerView) findViewById(R.id.recycler_provider_selection);
        mTxtPosProvider = (TextView) findViewById(R.id.txt_pos_provider);
        mTxtPosProduct = (TextView) findViewById(R.id.txt_pos_product);

    }

    @Override
    protected void setupFragment(Bundle savedInstanceState) {

        mPresenter = new ProductListPresenterImpl(this);
        mPresenter.attachView();

        mAdapter = new ProviderSelectionAdapter(getActivity(), this);
        mRecyclerview.setAdapter(mAdapter);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        mTxtPosProvider.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_green));
        mTxtPosProduct.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_green));

        mProductId = getArguments().getInt(PRODUCT_ID);

        mPresenter.parseProviders(mProductId);
    }

    @Override
    public void onItemPress(Provider provider) {

        StorageProvider.savePreferences(Constants.PROVIDER_ID, provider.providerId);
        mCallbacks.onGoToSelectProductType(provider.providerId, mProductId);
    }

    @Override
    public void showDataResponse(RealmList<ProviderList> providers, RealmList<Product> products) {

    }

    @Override
    public void showProvidersResponse(List<Provider> providers) {

        mAdapter.setItems(providers);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProductName(ItemFullName name) {

    }

    @Override
    public void showProviderList(int productId) {

    }

    @Override
    public void showTypesResponse(List<ProductType> types) {

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

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }




    public interface ProviderSelectionFragmentCallbacks {
        void onGoToSelectProductType(int providerId, int productId);
    }

    @Override
    public ProviderSelectionFragment.ProviderSelectionFragmentCallbacks getDummyCallbacks() {
        return new ProviderSelectionFragment.ProviderSelectionFragmentCallbacks() {
            @Override
            public void onGoToSelectProductType(int providerId, int productId) {

            }
        };
    }
}
