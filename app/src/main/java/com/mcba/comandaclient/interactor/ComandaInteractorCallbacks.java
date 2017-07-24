package com.mcba.comandaclient.interactor;

import com.mcba.comandaclient.model.Comanda;
import com.mcba.comandaclient.model.ComandaItem;
import com.mcba.comandaclient.model.ComandaList;

import java.util.List;

import io.realm.RealmList;

/**
 * Created by mac on 08/07/2017.
 */

public interface ComandaInteractorCallbacks {

    void fetchComandaById(RequestCallback requestCallback, int id);

    void fetchComandas(RequestCallback requestCallback);

    void fetchTotales(RequestCallback requestCallback, List<ComandaItem> mComandaItemList);

    void fetchComandaItems(RequestCallback requestCallback, int id);

    void storeComandas(RequestCallback requestCallback, ComandaList comandas);

    void storeComanda(RequestCallback requestCallback, Comanda comandas);

    void getLastComandaId(RequestCallback requestCallback);

    void attachView();

    void detachView();

    interface RequestCallback {

        void onFetchComandaSuccess(Comanda comanda);

        void onFetchComandaItems(RealmList<ComandaItem> items);

        void onFetchTotales(double total, double totalSenia, double cant);

        void onFetchComandaFail();

        void onFetchItemsFail();

        void onFetchDataFailed(String error);

        void onFetchLastComandaId(int id);

        void onStoreCompleted(boolean isSuccess);

    }
}
