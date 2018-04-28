package com.mcba.comandaclient.interactor;

import android.util.Log;

import com.mcba.comandaclient.model.Client;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by mac on 14/01/2018.
 */

public class ClientInteractorImpl extends RealmManager implements ClientInteractorCallbacks {


    private RealmAsyncTask mTransaction;


    @Override
    public void storeClient(final RequestCallback callback, final String name) {
        final Client client = new Client(lastClientId(), name);
        mTransaction = mRealm.executeTransactionAsync(new Realm.Transaction() {

            @Override
            public void execute(Realm bgRealm) {

                bgRealm.insertOrUpdate(client);


            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                callback.onStoreCompleted(true);
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                callback.onStoreCompleted(false);

            }
        });

    }

    private int lastClientId() {

        Number nextID = mRealm.where(Client.class).max("clientId");

        if (nextID != null) {
            return nextID.intValue() + 1;
        } else {
            return 0;
        }
    }

    @Override
    public void fetchClients(final RequestCallback requestCallback) {

        RealmList<Client> listClients = new RealmList<>();

        final RealmResults<Client> results = mRealm.where(Client.class).findAllSorted("clientId", Sort.DESCENDING);
        listClients.addAll(results);
        requestCallback.onClientsFetched(listClients);

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
