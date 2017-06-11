package com.mcba.comandaclient.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mac on 05/06/2017.
 */

public class ProductType extends RealmObject {

    @SerializedName("id")
    @PrimaryKey
    public int productTypeId;
    public String name;
    public Packaging packaging;
}
