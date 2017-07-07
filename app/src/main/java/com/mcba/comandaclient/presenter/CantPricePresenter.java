package com.mcba.comandaclient.presenter;

import com.mcba.comandaclient.model.Product;
import com.mcba.comandaclient.model.ProviderList;

import io.realm.RealmList;

/**
 * Created by mac on 30/06/2017.
 */

public interface CantPricePresenter extends IBasePresenter {

    void getProducts();

    void getPackaging(RealmList<ProviderList> provider, RealmList<Product> products, int providerId, int productId, int typeId);

    void setQtyText(int value, boolean operation);

    void setPriceText(double currentValue, double addValue, boolean operation);

}
