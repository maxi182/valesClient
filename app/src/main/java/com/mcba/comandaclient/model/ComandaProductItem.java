package com.mcba.comandaclient.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mac on 03/07/2017.
 */

public class ComandaProductItem extends RealmObject {

    @PrimaryKey
    public int productItemId;
    public int productId;
    public int providerId;
    public int productTypeId;
    public Packaging packaging;
}
