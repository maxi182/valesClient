package com.mcba.comandaclient.interactor;

import com.mcba.comandaclient.model.Comanda;
import com.mcba.comandaclient.model.ComandaItem;
import com.mcba.comandaclient.model.ComandaList;

import io.realm.RealmList;

/**
 * Created by mac on 08/07/2017.
 */

public interface ComandaInteractorCallbacks {

    void fetchComandaById(RequestCallback requestCallback, int id);

    void fetchComandas(RequestCallback requestCallback);

    void fetchComandaItems(RequestCallback requestCallback, int id);

    void storeComandas(RequestCallback requestCallback, ComandaList comandas);

    void storeComanda(RequestCallback requestCallback, Comanda comandas);

    void getLastComandaId(RequestCallback requestCallback);

    void attachView();

    void detachView();

    interface RequestCallback {

        void onFetchComandaSuccess(Comanda comanda);

        void onFetchComandaItems(RealmList<ComandaItem> items);

        void onFetchComandaFail();

        void onFetchItemsFail();

        void onFetchDataFailed(String error);

        void onFetchLastComandaId(int id);

        void onStoreCompleted(boolean isSuccess);

    }
}
