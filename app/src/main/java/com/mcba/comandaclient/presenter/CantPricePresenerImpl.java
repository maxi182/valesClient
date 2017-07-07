package com.mcba.comandaclient.presenter;

import com.mcba.comandaclient.interactor.CantPriceInteractorCallbacks;
import com.mcba.comandaclient.interactor.ProductInteractorCallbacks;
import com.mcba.comandaclient.interactor.ProductInteractorImpl;
import com.mcba.comandaclient.model.Product;
import com.mcba.comandaclient.model.ProductType;
import com.mcba.comandaclient.model.Provider;
import com.mcba.comandaclient.model.ProviderList;
import com.mcba.comandaclient.ui.CantPriceView;

import java.lang.ref.WeakReference;
import java.util.List;

import io.realm.RealmList;

/**
 * Created by mac on 30/06/2017.
 */

public class CantPricePresenerImpl implements CantPricePresenter, ProductInteractorCallbacks.RequestCallback, CantPriceInteractorCallbacks.CantPriceRequestCallback {


    private ProductInteractorCallbacks mProductInteractorCallback;
    private WeakReference<CantPriceView> cantPriceView;


    public CantPricePresenerImpl(CantPriceView cantPriceView) {
        this.mProductInteractorCallback = new ProductInteractorImpl();
        this.cantPriceView = new WeakReference<>(cantPriceView);
    }


    @Override
    public void getProducts() {

        mProductInteractorCallback.fetchProducts(this);

    }

    @Override
    public void getPackaging(RealmList<ProviderList> providers, RealmList<Product> products, int providerId, int productId, int typeId) {

        mProductInteractorCallback.parsePackaging(this, providers, products, providerId, productId, typeId);
    }

    @Override
    public void onPacakgeParsed(boolean isFree, double value) {


        if (cantPriceView != null) {

            getView().showPackagingResponse(isFree, value);

        }

    }

    @Override
    public void setQtyText(int currentValue, boolean operation) {

        if (cantPriceView != null) {
            if (currentValue >= 1) {
                if (currentValue == 1 && !operation) {
                    return;
                } else {
                    getView().updateQtyText(operation ? currentValue + 1 : currentValue - 1);
                }
            }
        }
    }

    @Override
    public void setPriceText(double currentValue, double addValue, boolean operation) {
        if (cantPriceView != null) {
            getView().updatePriceText(operation ? currentValue + addValue : currentValue - addValue);

        }
    }

    private CantPriceView getView() {
        return (cantPriceView != null) ? cantPriceView.get() : null;
    }

    @Override
    public void onFetchDataSuccess(RealmList<ProviderList> providers, RealmList<Product> products) {
        if (cantPriceView != null) {
            getView().showDataResponse(providers, products);
        }
    }

    @Override
    public void onProvidersParsed(List<Provider> providers) {

    }

    @Override
    public void onProductNameFetched(String name) {

    }

    @Override
    public void onTypesParsed(List<ProductType> types) {

    }

    @Override
    public void onFetchDataFailed(String error) {

    }

    @Override
    public void onStoreCompleted(boolean isSuccess) {

    }

    @Override
    public void onItemPress() {

    }

    @Override
    public void detachView() {
        if (cantPriceView != null) {
            cantPriceView.clear();
            cantPriceView = null;
        }
        mProductInteractorCallback.detachView();
    }

    @Override
    public void attachView() {

        mProductInteractorCallback.attachView();

    }


}
