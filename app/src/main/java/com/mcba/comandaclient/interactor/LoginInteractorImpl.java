package com.mcba.comandaclient.interactor;

import com.mcba.comandaclient.api.RestClient;
import com.mcba.comandaclient.model.ProviderList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mac on 01/01/2018.
 */

public class LoginInteractorImpl implements LoginInteractorCallbacks{


    @Override
    public void fetchLogin(final RequestCallback callback) {

        RestClient.getApiService().getProducts().enqueue(new Callback<ProviderList>() {

            @Override
            public void onResponse(Call<ProviderList> call, Response<ProviderList> response) {

                if (response.body() != null) {

                    callback.onFetchLoginSuccess("", "", "");
                    // storeLocal(callback, response.body());


                } else {

                    // callback.(response.message());

                }
            }

            @Override
            public void onFailure(Call<ProviderList> call, Throwable t) {

                callback.onFetchLoginFail();

            }
        });
    }

    @Override
    public void attachView() {

    }

    @Override
    public void detachView() {

    }
}
