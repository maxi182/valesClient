package com.mcba.comandaclient.presenter;

import com.mcba.comandaclient.interactor.SettingsInteractorCallbacks;
import com.mcba.comandaclient.interactor.SettingsInteractorImpl;
import com.mcba.comandaclient.ui.SettingsView;

import java.lang.ref.WeakReference;

/**
 * Created by mac on 27/09/2017.
 */

public class SettingsPresenterImpl implements SettingsPresenter, SettingsInteractorCallbacks.RequestCallback {


    private SettingsInteractorCallbacks mSettingsInteractorCallback;
    private WeakReference<SettingsView> settingsView;


    public SettingsPresenterImpl(SettingsView settingsView) {
        this.mSettingsInteractorCallback = new SettingsInteractorImpl();
        this.settingsView = new WeakReference<>(settingsView);
    }

    @Override
    public void onUpdatePress() {

        mSettingsInteractorCallback.updateProducts(this);

    }

    @Override
    public void onAddItemPress() {
        mSettingsInteractorCallback.addItem(this);
    }

    @Override
    public void onUpdateProductsCompleted(boolean isSuccess) {

        if (settingsView != null) {
            getView().updateProducts(isSuccess);
        }

    }

    private SettingsView getView() {
        return (settingsView != null) ? settingsView.get() : null;
    }

    @Override
    public void detachView() {
        if (settingsView != null) {
            settingsView.clear();
            settingsView = null;
        }
        mSettingsInteractorCallback.detachView();
    }


    @Override
    public void attachView() {
        mSettingsInteractorCallback.attachView();
    }

}
