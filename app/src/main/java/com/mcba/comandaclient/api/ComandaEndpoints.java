package com.mcba.comandaclient.api;

import com.mcba.comandaclient.model.ProductList;


import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by maximiliano.ferraiuolo on 23/11/2016.
 */

public interface ComandaEndpoints {

    @GET("/css/products.json")
    Call<ProductList> getProducts();

}
