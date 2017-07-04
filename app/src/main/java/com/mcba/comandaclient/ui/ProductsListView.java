package com.mcba.comandaclient.ui;

import com.mcba.comandaclient.model.Product;
import com.mcba.comandaclient.model.ProductType;
import com.mcba.comandaclient.model.Provider;
import com.mcba.comandaclient.model.ProviderList;

import java.util.List;

import io.realm.RealmList;

/**
 * Created by mac on 07/06/2017.
 */

public interface ProductsListView {

    void showDataResponse(RealmList<ProviderList> providers, RealmList<Product> products);

    void showProvidersResponse(List<Provider> providers);

    void showProductName(String name);

    void showTypesResponse(List<ProductType> types);

    void onResponseFailed();

    void realmStoreCompleted();

    void realmStoreFailed();

    void showProgress();

    void hideProgress();

    void onItemPress();
}
