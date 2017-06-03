package com.mcba.comandaclient.model;

import io.realm.RealmObject;

/**
 * Created by mac on 29/05/2017.
 */

public class Product extends RealmObject {

    public int productId;
    public String provider;
    public String name;
    public String type;


}
