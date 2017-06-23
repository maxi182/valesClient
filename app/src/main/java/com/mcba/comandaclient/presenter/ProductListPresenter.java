package com.mcba.comandaclient.presenter;

import com.mcba.comandaclient.model.ProviderList;

import io.realm.RealmList;

/**
 * Created by mac on 25/05/2017.
 */

public interface ProductListPresenter extends IBasePresenter {

    void getProducts();

    void parseProviders(RealmList<ProviderList> providers, int productId);
}
