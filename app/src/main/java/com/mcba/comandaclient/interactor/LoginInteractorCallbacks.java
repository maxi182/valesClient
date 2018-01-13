package com.mcba.comandaclient.interactor;

/**
 * Created by mac on 01/01/2018.
 */

public interface LoginInteractorCallbacks  {

    void fetchLogin(RequestCallback callback);

    void attachView();

    void detachView();

    interface RequestCallback {

        void onFetchLoginSuccess(String user, String pwd, String access);
        void onFetchLoginFail();
    }
}
