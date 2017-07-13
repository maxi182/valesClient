package com.mcba.comandaclient.presenter;

/**
 * Created by mac on 25/05/2017.
 */

public interface ProductListPresenter extends IBasePresenter {

    void getProducts();

    void getProductNameById(int productId, int providerId, int typeId);

    void parseProviders(int productId);

    void parseProductsTypeByProvider(int providerId, int productId);

}
