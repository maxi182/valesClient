package com.mcba.comandaclient.interactor;

import com.mcba.comandaclient.model.Provider;

import io.realm.RealmList;

/**
 * Created by mac on 05/05/2018.
 */

public interface AddProviderInteractorCallbacks {


    void fetchProviders(RequestCallback requestCallback);

    void fetchProducts(RequestCallback requestCallback);

    void fetchTypes(RequestCallback requestCallback);


    void attachView();

    void detachView();


    interface RequestCallback {

        void onStoreCompleted(boolean isSuccess);

        void onProvidersFetched(RealmList<Provider> providers);

    }
}
