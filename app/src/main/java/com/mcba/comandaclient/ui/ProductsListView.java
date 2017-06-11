package com.mcba.comandaclient.ui;

import com.mcba.comandaclient.model.Product;

import io.realm.RealmList;

/**
 * Created by mac on 07/06/2017.
 */

public interface ProductsListView {

    void showProductListResponse(RealmList<Product> data);

    void showDetaiResponse(RealmList<Product> data);

    void onResponseFailed();

    void realmStoreCompleted();

    void realmStoreFailed();

    void showProgress();

    void hideProgress();

    void onItemPress();
}
