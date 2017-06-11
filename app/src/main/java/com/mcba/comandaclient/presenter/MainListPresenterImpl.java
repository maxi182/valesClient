package com.mcba.comandaclient.presenter;


import com.mcba.comandaclient.interactor.ProductInteractorCallbacks;
import com.mcba.comandaclient.interactor.ProductInteractorImpl;
import com.mcba.comandaclient.model.Product;
import com.mcba.comandaclient.ui.ProductsListView;

import java.lang.ref.WeakReference;

import io.realm.RealmList;

/**
 * Created by mac on 25/05/2017.
 */

public class MainListPresenterImpl implements MainListPresenter, ProductInteractorCallbacks.RequestCallback {


    private ProductInteractorCallbacks mProductInteractorCallback;
    private WeakReference<ProductsListView> productsListView;


    public MainListPresenterImpl(ProductsListView productsListView) {
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

    private ProductsListView getView() {
        return (productsListView != null) ? productsListView.get() : null;
    }

    @Override
    public void onFetchDataSuccess(RealmList<Product> data) {
        if (productsListView != null) {
            getView().showProductListResponse(data);
            getView().hideProgress();
        }
    }

    @Override
    public void onFetchDataFailed(String error) {

    }

    @Override
    public void onStoreCompleted(boolean isSuccess) {

    }

    @Override
    public void onFavChanged(int pos) {

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
