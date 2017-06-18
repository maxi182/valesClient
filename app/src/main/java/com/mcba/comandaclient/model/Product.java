package com.mcba.comandaclient.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mac on 29/05/2017.
 */

public class Product extends RealmObject {

    @SerializedName("id")
    @PrimaryKey
    public int productId;
    public String name;
    public RealmList<ProductType> types;

}
