package com.mcba.comandaclient.presenter;

import com.mcba.comandaclient.interactor.AddProviderInteractorCallbacks;
import com.mcba.comandaclient.interactor.AddProviderInteractorImpl;
import com.mcba.comandaclient.model.NameId;
import com.mcba.comandaclient.model.Provider;
import com.mcba.comandaclient.ui.AddProviderView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

/**
 * Created by mac on 05/05/2018.
 */

public class AddProviderPresenterImpl implements AddProviderPresenter, AddProviderInteractorCallbacks.RequestCallback {


    private AddProviderInteractorCallbacks mAddProviderInteractorCallback;
    private WeakReference<AddProviderView> addProviderView;


    public AddProviderPresenterImpl(AddProviderView addProviderView) {
        this.mAddProviderInteractorCallback = new AddProviderInteractorImpl();
        this.addProviderView = new WeakReference<>(addProviderView);
    }


    @Override
    public void fetchProviders() {

        mAddProviderInteractorCallback.fetchProviders(this);

    }

    @Override
    public void fetchProducts() {

        List<NameId> products = new ArrayList<>();
        products.add(new NameId(1, "Bandeja"));
        products.add(new NameId(2, "Jaulon"));
        products.add(new NameId(3, "Torito"));
        if (addProviderView != null) {
            getView().onProductsFetched(products);
        }

    }

    @Override
    public void fetchTypes() {

    }

    private AddProviderView getView() {
        return (addProviderView != null) ? addProviderView.get() : null;
    }

    @Override
    public void detachView() {
        if (addProviderView != null) {
            addProviderView.clear();
            addProviderView = null;
        }
        mAddProviderInteractorCallback.detachView();
    }

    @Override
    public void attachView() {

        mAddProviderInteractorCallback.attachView();

    }

    @Override
    public void onStoreCompleted(boolean isSuccess) {

    }

    @Override
    public void onProvidersFetched(RealmList<Provider> providers) {
        if (addProviderView != null) {
            getView().onProvidersFetched(providers);
        }
    }
}

