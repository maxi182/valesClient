package com.mcba.comandaclient.ui.fragment;

import android.os.Bundle;

import com.mcba.comandaclient.presenter.ComandaListPresenter;
import com.mcba.comandaclient.presenter.ComandaListPresenterImpl;
import com.mcba.comandaclient.presenter.LoginPresenter;
import com.mcba.comandaclient.presenter.LoginPresenterImpl;
import com.mcba.comandaclient.ui.ComandaListView;
import com.mcba.comandaclient.ui.LoginView;

import java.util.concurrent.locks.Lock;

/**
 * Created by mac on 01/01/2018.
 */

public class LoginFragment extends BaseNavigationFragment<LoginFragment.LoginFragmentCallbacks> implements LoginView {


    private LoginPresenter mPresenter;

    @Override
    protected int getLayoutResource() {
        return 0;
    }

    @Override
    protected void setViewReferences() {


    }

    @Override
    protected void setupFragment(Bundle savedInstanceState) {
        mPresenter = new LoginPresenterImpl(this);

        mPresenter.attachView();

        mPresenter.fetchLogin();
    }

    @Override
    public void userFetched(String user, String pwd, String type) {

    }

    public interface LoginFragmentCallbacks {
        void onGoToMainListFromEntryFragment(int nextComandaId);
    }

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public LoginFragment.LoginFragmentCallbacks getDummyCallbacks() {
        return new LoginFragment.LoginFragmentCallbacks() {
            @Override
            public void onGoToMainListFromEntryFragment(int nextComandaId) {

            }
        };
    }


}
