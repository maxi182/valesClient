package com.mcba.comandaclient.presenter;

import com.mcba.comandaclient.interactor.LoginInteractorCallbacks;
import com.mcba.comandaclient.interactor.LoginInteractorImpl;
import com.mcba.comandaclient.interactor.ProductInteractorImpl;
import com.mcba.comandaclient.ui.LoginView;
import com.mcba.comandaclient.ui.ProductsListView;
import com.mcba.comandaclient.ui.SettingsView;

import java.lang.ref.WeakReference;

/**
 * Created by mac on 01/01/2018.
 */

public class LoginPresenterImpl implements LoginPresenter, LoginInteractorCallbacks.RequestCallback {


    private LoginInteractorCallbacks mLoginIntedactorCallbacks;
    private WeakReference<LoginView> loginView;


    public LoginPresenterImpl(LoginView loginView) {
        this.mLoginIntedactorCallbacks = new LoginInteractorImpl();
        this.loginView = new WeakReference<>(loginView);
    }


    @Override
    public void fetchLogin() {

        mLoginIntedactorCallbacks.fetchLogin(this);

    }

    @Override
    public void detachView() {

    }

    @Override
    public void attachView() {

    }

    @Override
    public void onFetchLoginSuccess(String user, String pwd, String access) {

        if (loginView != null) {
            getView().userFetched(user, pwd, access);
        }
    }

    private LoginView getView() {
        return (loginView != null) ? loginView.get() : null;
    }


    @Override
    public void onFetchLoginFail() {

    }


}
