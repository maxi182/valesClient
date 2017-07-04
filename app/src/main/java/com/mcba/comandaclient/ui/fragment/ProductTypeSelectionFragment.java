package com.mcba.comandaclient.ui.fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.mcba.comandaclient.R;
import com.mcba.comandaclient.model.Product;
import com.mcba.comandaclient.model.ProductType;
import com.mcba.comandaclient.model.Provider;
import com.mcba.comandaclient.model.ProviderList;
import com.mcba.comandaclient.presenter.ProductListPresenter;
import com.mcba.comandaclient.presenter.ProductListPresenterImpl;
import com.mcba.comandaclient.ui.ProductsListView;
import com.mcba.comandaclient.ui.adapter.ProductTypeSelectionAdapter;
import com.mcba.comandaclient.utils.Constants;
import com.mcba.comandaclient.utils.StorageProvider;

import java.util.List;

import io.realm.RealmList;

/**
 * Created by mac on 22/06/2017.
 */

public class ProductTypeSelectionFragment extends BaseNavigationFragment<ProductTypeSelectionFragment.ProductTypeSelectionFragmentCallbacks> implements ProductsListView, ProductTypeSelectionAdapter.AdapterCallbacks {

    public static final String PRODUCT_ID = "productId";
    public static final String PROVIDER_ID = "providerId";

    private ProductListPresenter mPresenter;
    private ProductTypeSelectionAdapter mAdapter;
    private RecyclerView mRecyclerview;
    private TextView mTxtPosProductType;
    private int mProductId;
    private int mProviderId;


    public static ProductTypeSelectionFragment newInstance(int productId, int providerId) {

        Bundle args = new Bundle();
        args.putInt(PRODUCT_ID, productId);
        args.putInt(PROVIDER_ID, providerId);
        ProductTypeSelectionFragment fragment = new ProductTypeSelectionFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.product_type_selection_fragment;
    }

    @Override
    protected void setViewReferences() {

        mRecyclerview = (RecyclerView) findViewById(R.id.recycler_product_type_selection);
        mTxtPosProductType = (TextView) findViewById(R.id.txt_pos_type);

    }

    @Override
    protected void setupFragment(Bundle savedInstanceState) {

        mPresenter = new ProductListPresenterImpl(this);
        mPresenter.attachView();

        mAdapter = new ProductTypeSelectionAdapter(getActivity(), this);
        mRecyclerview.setAdapter(mAdapter);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        mTxtPosProductType.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_green));

        mProductId = getArguments().getInt(PRODUCT_ID);
        mProviderId = getArguments().getInt(PROVIDER_ID);

        mPresenter.getProducts();

    }


    @Override
    public void showDataResponse(RealmList<ProviderList> providers, RealmList<Product> products) {

        mPresenter.parseProductsTypeByProvider(providers, products, mProviderId, mProductId);

    }

    @Override
    public void showProvidersResponse(List<Provider> providers) {

    }

    @Override
    public void showProductName(String name) {

    }

    @Override
    public void showTypesResponse(List<ProductType> types) {

        mAdapter.setItems(types);
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void onItemPress(ProductType type) {

        StorageProvider.savePreferences(Constants.PRODUCTTYPE_ID, type.productTypeId);
        mCallbacks.onGoToSetPriceAndQty(mProviderId, mProductId, type.productTypeId);

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


    public interface ProductTypeSelectionFragmentCallbacks {
        void onGoToSetPriceAndQty(int providerId, int productId, int productTypeId);
    }

    @Override
    public ProductTypeSelectionFragment.ProductTypeSelectionFragmentCallbacks getDummyCallbacks() {
        return new ProductTypeSelectionFragment.ProductTypeSelectionFragmentCallbacks() {
            @Override
            public void onGoToSetPriceAndQty(int providerId, int productId, int productTypeId) {

            }
        };
    }
}
