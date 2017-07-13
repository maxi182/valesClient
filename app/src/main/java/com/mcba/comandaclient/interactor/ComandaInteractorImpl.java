package com.mcba.comandaclient.interactor;

import android.util.Log;

import com.mcba.comandaclient.model.Comanda;
import com.mcba.comandaclient.model.ComandaList;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmResults;

/**
 * Created by mac on 08/07/2017.
 */

public class ComandaInteractorImpl extends RealmManager implements ComandaInteractorCallbacks {

    private RealmAsyncTask mTransaction;


    @Override
    public void fetchComandas(RequestCallback requestCallback) {

        if (isRealmDBLoaded()) {
            //  requestCallback.onFetchComandaSuccess(getRealmComandaData());
        } else {
            requestCallback.onFetchComandaFail();

        }
    }

    @Override
    public void fetchComandaById(RequestCallback requestCallback, int id) {

        if (isRealmDBLoaded()) {
            requestCallback.onFetchComandaSuccess(getComandaFromDB(id));
        } else {
            requestCallback.onFetchComandaFail();

        }
    }

    private Comanda getComandaFromDB(int id) {

        return mRealm.where(Comanda.class).equalTo("comandaId", id).findFirst();

    }

    @Override
    public void storeComandas(final RequestCallback callback, final ComandaList comandas) {

        mTransaction = mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {

                bgRealm.copyToRealmOrUpdate(comandas);

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                callback.onStoreCompleted(true);
                // callback.onFetchDataSuccess(getRealmProviderList(), getRealmProductList());
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                callback.onStoreCompleted(false);
            }
        });

    }

    @Override
    public void storeComanda(final RequestCallback callback, final Comanda comanda) {


        mTransaction = mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {

                bgRealm.insertOrUpdate(comanda);

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                callback.onStoreCompleted(true);
                // callback.onFetchDataSuccess(getRealmProviderList(), getRealmProductList());
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                callback.onStoreCompleted(false);
            }
        });

    }

    @Override
    public void getLastComandaId(RequestCallback requestCallback) {

        Number nextID = mRealm.where(Comanda.class).max("comandaId");

        if (nextID != null) {
            requestCallback.onFetchLastComandaId(nextID.intValue());
        } else {
            requestCallback.onFetchLastComandaId(0);
        }

    }


    private RealmResults<Comanda> getRealmComandaData() {

        return mRealm.where(Comanda.class).findAll();

    }

//    @Override
//    public int getLastComandaId() {
//
//        Number nextID = mRealm.where(Comanda.class).max("comandaId");
//
//        return nextID.intValue() + 1;
//    }

//    private ComandaList getRealmProductList() {
//        RealmList<Product> list = new RealmList<>();
//        list.addAll(getRealmProductData());
//
//        return list;
//    }


    private boolean isRealmDBLoaded() {

        return (getRealmComandaData() != null && !getRealmComandaData().isEmpty()) ? true : false;

    }


    @Override
    public void attachView() {

        Log.d("LOG", "Attach View");
        initRealm();

    }

    @Override
    public void detachView() {

        Log.d("LOG", "detachView");

        if (mTransaction != null && !mTransaction.isCancelled()) {
            mTransaction.cancel();
        }
        closeRealm();

    }
}
