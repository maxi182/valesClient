package com.mcba.comandaclient.interactor;

import com.mcba.comandaclient.model.Product;

import io.realm.RealmList;

/**
 * Created by mac on 06/06/2017.
 */

public interface ProductInteractorCallbacks  {

    void fetchProducts(RequestCallback callback);

    void attachView();

    void detachView();

    interface RequestCallback {

        void onFetchDataSuccess(RealmList<Product> data);

        void onFetchDataFailed(String error);

        void onStoreCompleted(boolean isSuccess);

        void onFavChanged(int pos);

        void onItemPress();

    }
}
