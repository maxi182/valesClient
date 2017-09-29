package com.mcba.comandaclient.interactor;

import android.util.Log;

import com.mcba.comandaclient.api.RestClient;
import com.mcba.comandaclient.model.ProviderList;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mac on 27/09/2017.
 */

public class SettingsInteractorImpl extends RealmManager implements SettingsInteractorCallbacks {

    private RealmAsyncTask mTransaction;


    @Override
    public void updateProducts(RequestCallback requestCallback) {

        deleteData();

        //  fetchDataFromServer(requestCallback);


    }

    private void deleteData() {

        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<ProviderList> result = realm.where(ProviderList.class).findAll();
                result.deleteAllFromRealm();
            }
        });
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
