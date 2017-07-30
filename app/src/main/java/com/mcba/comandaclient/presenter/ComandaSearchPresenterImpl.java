package com.mcba.comandaclient.presenter;

import com.mcba.comandaclient.interactor.ComandaSearchInteractorCallbacks;
import com.mcba.comandaclient.interactor.ComandaSearchInteractorImpl;
import com.mcba.comandaclient.ui.ComandaSearchView;

import java.lang.ref.WeakReference;

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


}
