package com.mcba.comandaclient.interactor;

import com.mcba.comandaclient.model.ItemFullName;
import com.mcba.comandaclient.model.Product;
import com.mcba.comandaclient.model.ProductType;
import com.mcba.comandaclient.model.Provider;
import com.mcba.comandaclient.model.ProviderList;

import java.util.List;

import io.realm.RealmList;

/**
 * Created by mac on 06/06/2017.
 */

public interface ProductInteractorCallbacks {

    void fetchProducts(RequestCallback callback);

    void parseProviders(RequestCallback callback, int productId);

    void parsePackaging(CantPriceInteractorCallbacks.CantPriceRequestCallback callback, int providerId, int productId, int typeId);

    void getLastItemId(CantPriceInteractorCallbacks.CantPriceRequestCallback callback);

    void parseProductsTypeByProvider(RequestCallback callback, int providerId, int productId);

    void getProductNameById(RequestCallback callback, int productId, int providerId, int typeId);

    void attachView();

    void detachView();

    interface RequestCallback {

        void onFetchDataSuccess(RealmList<ProviderList> providers, RealmList<Product> products);

        void onProvidersParsed(List<Provider> providers);

        void onProductNameFetched(ItemFullName itemFullName);

        void onTypesParsed(List<ProductType> types);

        void onFetchDataFailed(String error);

        void onStoreCompleted(boolean isSuccess);

        void onItemPress();

    }
}
