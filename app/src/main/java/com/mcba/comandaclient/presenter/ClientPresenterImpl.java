package com.mcba.comandaclient.presenter;


import com.mcba.comandaclient.interactor.ClientInteractorCallbacks;
import com.mcba.comandaclient.interactor.ClientInteractorImpl;
import com.mcba.comandaclient.model.Client;
import com.mcba.comandaclient.ui.ClientView;

import java.lang.ref.WeakReference;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by mac on 14/01/2018.
 */

public class ClientPresenterImpl implements ClientPresenter, ClientInteractorCallbacks.RequestCallback {


    private ClientInteractorCallbacks mClientInteractorCallback;
    private WeakReference<ClientView> clientView;


    public ClientPresenterImpl(ClientView clientView) {
        this.mClientInteractorCallback = new ClientInteractorImpl();
        this.clientView = new WeakReference<>(clientView);
    }


    @Override
    public void fetchClients() {
        mClientInteractorCallback.fetchClients(this);

    }

    @Override
    public void storeClient(String name) {
        mClientInteractorCallback.storeClient(this, name);
    }

    @Override
    public void validateExistingUser(String name, List<Client> clientList) {

        boolean exist = false;
        for (int i = 0; i < clientList.size(); i++) {
            if (clientList.get(i).mName.equals(name)) {
                exist = true;
            }
        }
        getView().clientExists(exist, name);
    }

    @Override
    public void onStoreCompleted(boolean isSuccess) {
        if (clientView != null) {
            getView().storeSuccess(isSuccess);
        }
    }

    @Override
    public void onClientsFetched(RealmList<Client> clients) {

        getView().showClientList(clients);

    }

    private ClientView getView() {
        return (clientView != null) ? clientView.get() : null;
    }


    @Override
    public void detachView() {
        if (clientView != null) {
            clientView.clear();
            clientView = null;
        }
        mClientInteractorCallback.detachView();
    }

    @Override
    public void attachView() {

        mClientInteractorCallback.attachView();

    }

}
