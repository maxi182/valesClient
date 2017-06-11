package com.mcba.comandaclient.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mac on 09/06/2017.
 */

public class ProductList extends RealmObject {

    @PrimaryKey
    public int id;
    @SerializedName("products")
    public RealmList<Product> products;
}
