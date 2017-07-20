package com.mcba.comandaclient.presenter;

import com.mcba.comandaclient.interactor.ComandaInteractorCallbacks;
import com.mcba.comandaclient.interactor.ComandaInteractorImpl;
import com.mcba.comandaclient.model.Comanda;
import com.mcba.comandaclient.model.ComandaList;
import com.mcba.comandaclient.ui.ComandaListView;

import java.lang.ref.WeakReference;

import io.realm.RealmList;

/**
 * Created by mac on 08/07/2017.
 */

public class ComandaListPresenterImpl implements ComandaListPresenter, ComandaInteractorCallbacks.RequestCallback{

    private ComandaInteractorCallbacks mComandaInteractorCallbacks;
    private WeakReference<ComandaListView> comandaListView;


    public ComandaListPresenterImpl(ComandaListView comandaListView) {
        this.mComandaInteractorCallbacks = new ComandaInteractorImpl();
        this.comandaListView = new WeakReference<>(comandaListView);
    }

    @Override
    public void storeComandas(ComandaList comandas) {

        mComandaInteractorCallbacks.storeComandas(this, comandas);

    }

    @Override
    public void storeComanda(Comanda comanda) {

        mComandaInteractorCallbacks.storeComanda(this, comanda);
    }

    @Override
    public void fetchComandaById(int id) {

        mComandaInteractorCallbacks.fetchComandaById(this, id);

    }

    @Override
    public void fetchLastComanda() {

        mComandaInteractorCallbacks.getLastComandaId(this);
    }

    @Override
    public void onFetchComandaSuccess(Comanda comanda) {

        if (comandaListView != null) {
            getView().showItemsComanda(comanda);
        }
    }

    private ComandaListView getView() {
        return (comandaListView != null) ? comandaListView.get() : null;
    }


    @Override
    public void onFetchComandaFail() {

    }

    @Override
    public void onFetchDataFailed(String error) {

    }

    @Override
    public void onFetchLastComandaId(int id) {

        if (comandaListView != null) {
            getView().showLastComandaId(id);
        }

    }

    @Override
    public void onStoreCompleted(boolean isSuccess) {
        if (comandaListView != null) {
            getView().onStoreItemSuccess(isSuccess);
        }
    }

    @Override
    public void detachView() {

        if (comandaListView != null) {
            comandaListView.clear();
            comandaListView = null;
        }
        mComandaInteractorCallbacks.detachView();

    }

    @Override
    public void attachView() {
        mComandaInteractorCallbacks.attachView();
    }

}
