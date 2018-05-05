package com.mcba.comandaclient.interactor;

import android.util.Log;

import com.mcba.comandaclient.model.Comanda;
import com.mcba.comandaclient.model.ComandaItem;

import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by mac on 24/07/2017.
 */

public class ComandaSearchInteractorImpl extends RealmManager implements ComandaSearchInteractorCallbacks {


    @Override
    public void fetchComandas(RequestCallback requestCallback, String dateFrom) {
        RealmList<Comanda> listComandas = new RealmList<>();

        RealmResults<Comanda> results = mRealm.where(Comanda.class).equalTo("isPrinted", true).findAll().where().equalTo("date", dateFrom).findAll().sort("comandaId", Sort.DESCENDING);
        listComandas.addAll(results);
        requestCallback.onFetchComandasSuccess(listComandas);
    }

    @Override
    public void fetchItemsComanda(RequestCallback requestCallback, int id, double cantBultos, double total, double senia, long timestamp) {
        RealmList<ComandaItem> items = mRealm.where(Comanda.class).equalTo("comandaId", id).findFirst().comandaItemList;
        requestCallback.onFetchComandaItems(items, id, cantBultos, total, senia, timestamp);
    }

    @Override
    public void fetchComandasById(RequestCallback requestCallback, int id) {

        RealmResults<Comanda> comandas = mRealm.where(Comanda.class).equalTo("comandaId", id).findAll();
        requestCallback.onFetchComandasByIdSuccess(comandas);

    }

    @Override
    public void attachView() {

        Log.d("LOG", "Attach View");
        initRealm();

    }

    @Override
    public void detachView() {

        Log.d("LOG", "detachView");

        closeRealm();

    }

}
