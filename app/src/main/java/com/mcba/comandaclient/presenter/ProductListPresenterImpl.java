package com.mcba.comandaclient.presenter;


import com.mcba.comandaclient.interactor.ProductInteractorCallbacks;
import com.mcba.comandaclient.interactor.ProductInteractorImpl;
import com.mcba.comandaclient.model.ItemFullName;
import com.mcba.comandaclient.model.Product;
import com.mcba.comandaclient.model.ProductType;
import com.mcba.comandaclient.model.Provider;
import com.mcba.comandaclient.model.ProviderList;
import com.mcba.comandaclient.ui.CantPriceView;
import com.mcba.comandaclient.ui.ProductsListView;

import java.lang.ref.WeakReference;
import java.util.List;

import io.realm.RealmList;

/**
 * Created by mac on 25/05/2017.
 */

public class ProductListPresenterImpl implements ProductListPresenter, ProductInteractorCallbacks.RequestCallback {


    private ProductInteractorCallbacks mProductInteractorCallback;
    private WeakReference<ProductsListView> productsListView;


    public ProductListPresenterImpl(ProductsListView productsListView) {
        this.mProductInteractorCallback = new ProductInteractorImpl();
        this.productsListView = new WeakReference<>(productsListView);
    }

    @Override
    public void getProducts() {
        if (productsListView != null) {
            getView().showProgress();
        }
        mProductInteractorCallback.fetchProducts(this);
    }

    @Override
    public void getProductNameById(int productId, int providerId, int typeId) {
        if (productsListView != null) {
            getView().showProgress();
        }
        mProductInteractorCallback.getProductNameById(this, productId, providerId, typeId);
    }

    @Override
    public void parseProviders(int productId) {
        if (productsListView != null) {
            getView().showProgress();
        }
        mProductInteractorCallback.parseProviders(this, productId);

    }

    @Override
    public void parseProductsTypeByProvider(int providerId, int productId) {

        if (productsListView != null) {
            getView().showProgress();
        }
        mProductInteractorCallback.parseProductsTypeByProvider(this, providerId, productId);

    }

    private ProductsListView getView() {
        return (productsListView != null) ? productsListView.get() : null;
    }

    @Override
    public void onFetchDataSuccess(RealmList<ProviderList> providers, RealmList<Product> products) {
        if (productsListView != null) {
            getView().showDataResponse(providers, products);
            getView().hideProgress();
        }
    }

    @Override
    public void onProvidersParsed(List<Provider> providers) {
        if (productsListView != null) {
            getView().showProvidersResponse(providers);
            getView().hideProgress();
        }
    }

    @Override
    public void onProductNameFetched(ItemFullName itemFullName) {
        if (productsListView != null) {
            getView().showProductName(itemFullName);
            getView().hideProgress();
        }
    }


    @Override
    public void onTypesParsed(List<ProductType> types) {
        if (productsListView != null) {
            getView().showTypesResponse(types);
            getView().hideProgress();
        }
    }

    @Override
    public void onFetchDataFailed(String error) {

    }

    @Override
    public void onStoreCompleted(boolean isSuccess) {

        if (productsListView != null) {
            if (isSuccess) {
                getView().realmStoreCompleted();
            } else {
                getView().realmStoreFailed();

            }
        }
    }


    @Override
    public void onItemPress() {

    }

    @Override
    public void detachView() {
        if (productsListView != null) {
            productsListView.clear();
            productsListView = null;
        }
        mProductInteractorCallback.detachView();
    }


    @Override
    public void attachView() {
        mProductInteractorCallback.attachView();
    }


}
