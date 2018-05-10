package com.mcba.comandaclient.interactor;

import android.util.Log;

import com.mcba.comandaclient.model.Provider;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmAsyncTask;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by mac on 05/05/2018.
 */

public class AddProviderInteractorImpl extends RealmManager implements AddProviderInteractorCallbacks {


    private RealmAsyncTask mTransaction;


    @Override
    public void fetchProviders(RequestCallback callback) {

        RealmResults<Provider> results = mRealm.where(Provider.class).findAll();
        RealmList<Provider> list = new RealmList<>();
        list.addAll(results);
        callback.onProvidersFetched(list);

    }

    @Override
    public void fetchProducts(RequestCallback requestCallback) {

    }

    @Override
    public void fetchTypes(RequestCallback requestCallback) {

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
