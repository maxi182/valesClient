package com.mcba.comandaclient.interactor;

/**
 * Created by mac on 27/09/2017.
 */

public interface SettingsInteractorCallbacks {

    void updateProducts(RequestCallback requestCallback);

    void attachView();

    void detachView();

    interface RequestCallback {

        void onUpdateProductsCompleted(boolean isSuccess);

    }
}