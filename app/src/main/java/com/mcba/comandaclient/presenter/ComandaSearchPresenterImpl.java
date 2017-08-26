package com.mcba.comandaclient.presenter;

import com.mcba.comandaclient.interactor.ComandaSearchInteractorCallbacks;
import com.mcba.comandaclient.interactor.ComandaSearchInteractorImpl;
import com.mcba.comandaclient.model.Comanda;
import com.mcba.comandaclient.model.ComandaItem;
import com.mcba.comandaclient.ui.ComandaListView;
import com.mcba.comandaclient.ui.ComandaSearchView;

import java.lang.ref.WeakReference;

import io.realm.RealmList;

/**
 * Created by mac on 24/07/2017.
 */

public class ComandaSearchPresenterImpl implements ComandaSearchPresenter, ComandaSearchInteractorCallbacks.RequestCallback {

    private ComandaSearchInteractorCallbacks mComandaSearchInteractorCallbacks;
    private WeakReference<ComandaSearchView> comandaSearchView;


    public ComandaSearchPresenterImpl(ComandaSearchView comandaSearchView) {
        this.mComandaSearchInteractorCallbacks = new ComandaSearchInteractorImpl();
        this.comandaSearchView = new WeakReference<>(comandaSearchView);
    }

    @Override
    public void fetchComandas() {

        mComandaSearchInteractorCallbacks.fetchComandas(this);

    }

    @Override
    public void fetchItems(int id) {
        mComandaSearchInteractorCallbacks.fetchItemsComanda(this, id);
    }

    private ComandaSearchView getView() {
        return (comandaSearchView != null) ? comandaSearchView.get() : null;
    }

    @Override
    public void detachView() {
        if (comandaSearchView != null) {
            comandaSearchView.clear();
            comandaSearchView = null;
        }
        mComandaSearchInteractorCallbacks.detachView();
    }

    @Override
    public void attachView() {
        mComandaSearchInteractorCallbacks.attachView();
    }


    @Override
    public void onFetchComandasSuccess(RealmList<Comanda> comandas) {
        if (comandaSearchView != null) {
            getView().onComandasFetched(comandas);
        }

    }

    @Override
    public void onFetchComandaItems(RealmList<ComandaItem> items) {
        if (comandaSearchView != null) {
            getView().onItemsFetched(items);
        }

    }
}
