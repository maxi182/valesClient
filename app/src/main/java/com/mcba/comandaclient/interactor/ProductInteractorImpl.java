package com.mcba.comandaclient.interactor;

import android.util.Log;

import com.mcba.comandaclient.api.RestClient;
import com.mcba.comandaclient.model.Product;
import com.mcba.comandaclient.model.ProductType;
import com.mcba.comandaclient.model.Provider;
import com.mcba.comandaclient.model.ProviderList;

import java.util.ArrayList;
import java.util.List;

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

    private void storeLocal(final RequestCallback callback, final ProviderList data) {

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

    private String getRealmProductById() {

        RealmResults<Product> products = mRealm.where(Product.class).equalTo("productId", 20).findAll();

        return products.get(0).name;


    }

    private RealmResults<ProviderList> getRealmProviderData() {

        return mRealm.where(ProviderList.class).findAll();

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
    public void parseProviders(RequestCallback callback, RealmList<ProviderList> providers, int productId) {

        List<Provider> prov = new ArrayList<>();

        for (int i = 0; i < providers.get(0).providers.size(); i++) {

            for (int j = 0; j < providers.get(0).providers.get(i).products.size(); j++) {

                if (providers.get(0).providers.get(i).products.get(j).productId == productId) {
                    prov.add(providers.get(0).providers.get(i));
                }
            }
        }

        callback.onProvidersParsed(prov);
    }

    @Override
    public void parseProductsTypeByProvider(RequestCallback callback, RealmList<ProviderList> providers, RealmList<Product> products, int providerId, int productId) {

        List<ProductType> types = new ArrayList<>();

        RealmList<Provider> mProviders = providers.get(0).providers;

        for (int i = 0; i < mProviders.size(); i++) {

            if (mProviders.get(i).providerId == providerId) {

                for (int j = 0; j < mProviders.get(i).products.size(); j++) {

                    if (mProviders.get(i).products.get(j).productId == productId) {

                        types.addAll(mProviders.get(i).products.get(j).types);
                    }
                }
            }
        }
        callback.onTypesParsed(types);

    }

    @Override
    public void getProductNameById(RequestCallback callback) {
        callback.onProductNameFetched(getRealmProductById());

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
