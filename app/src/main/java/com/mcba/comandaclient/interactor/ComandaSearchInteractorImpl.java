package com.mcba.comandaclient.interactor;

import android.util.Log;

import com.mcba.comandaclient.model.Comanda;

import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by mac on 24/07/2017.
 */

public class ComandaSearchInteractorImpl extends RealmManager implements ComandaSearchInteractorCallbacks {


    @Override
    public void fetchComandas(RequestCallback requestCallback) {
        RealmList<Comanda> listComandas = new RealmList<>();

        RealmResults<Comanda> results = mRealm.where(Comanda.class).equalTo("isPrinted", true).findAll().sort("comandaId", Sort.DESCENDING);
        listComandas.addAll(results);
        requestCallback.onFetchComandasSuccess(listComandas);
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
