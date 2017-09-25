package com.mcba.comandaclient.interactor;

import com.mcba.comandaclient.model.Comanda;
import com.mcba.comandaclient.model.ComandaItem;

import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by mac on 24/07/2017.
 */

public interface ComandaSearchInteractorCallbacks {

    void fetchComandas(RequestCallback requestCallback);

    void fetchItemsComanda(RequestCallback requestCallback, int id, double cantBultos, double total, double senia, long timestamp);

    void fetchComandasById(RequestCallback requestCallback, int id);

    void attachView();

    void detachView();

    interface RequestCallback {

        void onFetchComandasSuccess(RealmList<Comanda> comandas);

        void onFetchComandaItems(RealmList<ComandaItem> items, int id, double cantBultos, double total, double senia, long timestamp);

        void onFetchComandasByIdSuccess(RealmResults<Comanda> comandas);

    }
}
