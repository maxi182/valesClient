package com.mcba.comandaclient.interactor;

import com.mcba.comandaclient.model.Client;

import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by mac on 14/01/2018.
 */

public interface ClientInteractorCallbacks {

    void fetchClients(RequestCallback requestCallback);

    void storeClient(RequestCallback requestCallback, String name);

    void filterClietsByName(RequestCallback requestCallback, String name);

    void attachView();

    void detachView();

    interface RequestCallback {

        void onStoreCompleted(boolean isSuccess);

        void onClientsFetched(RealmList<Client> clients);

    }
}
