package com.mcba.comandaclient.interactor;

import com.mcba.comandaclient.model.Product;
import com.mcba.comandaclient.model.Provider;
import com.mcba.comandaclient.model.ProviderList;

import java.util.List;

import io.realm.RealmList;

/**
 * Created by mac on 06/06/2017.
 */

public interface ProductInteractorCallbacks {

    void fetchProducts(RequestCallback callback);

    void parseProviders(RequestCallback callback, RealmList<ProviderList> providers, int productId);

    void attachView();

    void detachView();

    interface RequestCallback {

        void onFetchDataSuccess(RealmList<ProviderList> providers, RealmList<Product> products);

        void onProvidersParsed(List<Provider> providers);

        void onFetchDataFailed(String error);

        void onStoreCompleted(boolean isSuccess);

        void onItemPress();

    }
}
