package com.mcba.comandaclient.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mac on 05/06/2017.
 */

public class Provider extends RealmObject {

    @SerializedName("id")
    @PrimaryKey
    public int providerId;
    public String name;
    public RealmList<Product> products;

}
