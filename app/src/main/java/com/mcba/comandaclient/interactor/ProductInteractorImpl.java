package com.mcba.comandaclient.interactor;

import android.util.Log;

import com.mcba.comandaclient.api.RestClient;
import com.mcba.comandaclient.model.Product;
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

public class ProductInteractorImpl extends RealmManager implements ProductInteractorCallbacks {

    private RealmAsyncTask mTransaction;


    public void fetchProductsFromServer(final RequestCallback callback) {

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
                callback.onFetchDataSuccess(getRealmList());
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                callback.onStoreCompleted(false);
            }
        });

    }


    private boolean isRealmDBLoaded() {

        return (getRealmData() != null && !getRealmData().isEmpty()) ? true : false;

    }

    private RealmResults<Product> getRealmData() {

        return mRealm.where(Product.class).findAll();

    }

    private RealmList<Product> getRealmList() {
        RealmList<Product> list = new RealmList<>();
        list.addAll(getRealmData());

        return list;

    }

    public void fetchProductsLocal(final RequestCallback callback) {
        callback.onFetchDataSuccess(getRealmList());
    }

    @Override
    public void fetchProducts(RequestCallback callback) {

        if (isRealmDBLoaded()) {
            fetchProductsLocal(callback);
        } else {
            fetchProductsFromServer(callback);
        }

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
