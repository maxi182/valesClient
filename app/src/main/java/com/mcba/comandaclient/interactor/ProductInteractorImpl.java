package com.mcba.comandaclient.interactor;

import android.util.Log;

import com.mcba.comandaclient.api.RestClient;
import com.mcba.comandaclient.model.ComandaItem;
import com.mcba.comandaclient.model.ItemFullName;
import com.mcba.comandaclient.model.Product;
import com.mcba.comandaclient.model.ProductType;
import com.mcba.comandaclient.model.Provider;
import com.mcba.comandaclient.model.ProviderList;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmList;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mac on 06/06/2017.
 */

public class ProductInteractorImpl extends RealmManager implements ProductInteractorCallbacks, CantPriceInteractorCallbacks {

    private RealmAsyncTask mTransaction;


    public void fetchDataFromServer(final RequestCallback callback) {

        RestClient.getApiService().getProducts().enqueue(new Callback<ProviderList>() {

            @Override
            public void onResponse(Call<ProviderList> call, Response<ProviderList> response) {

                if (response.body() != null) {

                    storeLocal(callback, response.body());


                } else {

                    callback.onFetchDataFailed(response.message());

                }
            }

            @Override
            public void onFailure(Call<ProviderList> call, Throwable t) {

                callback.onFetchDataFailed(t.getMessage());

            }
        });
    }

    public void storeLocal(final RequestCallback callback, final ProviderList data) {

        mTransaction = mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {

                bgRealm.copyToRealmOrUpdate(data);

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                callback.onStoreCompleted(true);
                callback.onFetchDataSuccess(getRealmProviderList(), getRealmProductList());
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                callback.onStoreCompleted(false);
            }
        });

    }


    private boolean isRealmDBLoaded() {

        return (getRealmProviderData() != null && !getRealmProviderData().isEmpty()) ? true : false;

    }


    private boolean isRealmDBComandaItemLoaded() {

        return (getRealmComandaItemData() != null && !getRealmComandaItemData().isEmpty()) ? true : false;

    }

    private ItemFullName getRealmProductById(int productId, int providerId, int typeId) {

        Product productName = mRealm.where(Product.class).equalTo("productId", productId).findFirst();
        Provider providerName = mRealm.where(Provider.class).equalTo("providerId", providerId).findFirst();
        ProductType typeName = mRealm.where(ProductType.class).equalTo("productTypeId", typeId).findFirst();

        return new ItemFullName(productName.name, providerName.name, typeName.name);

    }

    private RealmResults<ProviderList> getRealmProviderData() {

        return mRealm.where(ProviderList.class).findAll();

    }

    private RealmResults<ComandaItem> getRealmComandaItemData() {

        return mRealm.where(ComandaItem.class).findAll();

    }

    private RealmResults<Product> getRealmProductData() {

        return mRealm.where(Product.class).findAll();

    }

    private RealmList<ProviderList> getRealmProviderList() {
        RealmList<ProviderList> list = new RealmList<>();
        list.addAll(getRealmProviderData());

        return list;
    }

    private RealmList<Product> getRealmProductList() {
        RealmList<Product> list = new RealmList<>();
        list.addAll(getRealmProductData());

        return list;
    }

    public void fetchDataLocal(final RequestCallback callback) {
        callback.onFetchDataSuccess(getRealmProviderList(), getRealmProductList());

    }

    @Override
    public void fetchProducts(RequestCallback callback) {

        if (isRealmDBLoaded()) {
            fetchDataLocal(callback);
        } else {
            fetchDataFromServer(callback);
        }
    }


    @Override
    public void parsePackaging(CantPriceRequestCallback callback, int providerId, int productId, int typeId) {

        RealmResults<Provider> results = mRealm.where(Provider.class).equalTo("products.productId", productId).findAll();
        Product product = results.where().equalTo("providerId", providerId).findFirst().products.where().equalTo("productId", productId).findFirst();
        ProductType type = product.types.where().equalTo("productTypeId", typeId).findFirst();

        callback.onPacakgeParsed(type.packaging.isFree, type.packaging.value);

    }

    @Override
    public void getLastItemId(CantPriceRequestCallback callback) {

        if (isRealmDBComandaItemLoaded()) {
            int lastItem = mRealm.where(ComandaItem.class).max("itemId").intValue();
            callback.onLastItemIdFetched(lastItem);
        } else {
            callback.onLastItemIdFetched(0);

        }
    }

    @Override
    public void parseProviders(RequestCallback callback, int productId) {

        RealmResults<Provider> results = mRealm.where(Provider.class).equalTo("products.productId", productId).findAll();
        callback.onProvidersParsed(results);
    }

    @Override
    public void parseProductsTypeByProvider(RequestCallback callback, int providerId, int productId) {

        RealmResults<Provider> results = mRealm.where(Provider.class).equalTo("products.productId", productId).findAll();
        Product product = results.where().equalTo("providerId", providerId).findFirst().products.where().equalTo("productId", productId).findFirst();
        RealmList<ProductType> types = product.types;

        callback.onTypesParsed(types);

    }

    @Override
    public void getProductNameById(RequestCallback callback, int productId, int providerId, int typeId) {
        callback.onProductNameFetched(getRealmProductById(productId, providerId, typeId));
    }

    @Override
    public void attachView() {

        Log.d("LOG", "Attach View");
        initRealm();

    }

    @Override
    public void detachView() {

        Log.d("LOG", "detachView");

        if (mTransaction != null && !mTransaction.isCancelled()) {
            mTransaction.cancel();
        }
        closeRealm();

    }


}
