package com.mcba.comandaclient.interactor;

import com.mcba.comandaclient.model.Comanda;

import io.realm.RealmList;

/**
 * Created by mac on 24/07/2017.
 */

public interface ComandaSearchInteractorCallbacks {

    void fetchComandas(RequestCallback requestCallback);

    void attachView();

    void detachView();

    interface RequestCallback {

        void onFetchComandasSuccess(RealmList<Comanda> comandas);

    }
}
