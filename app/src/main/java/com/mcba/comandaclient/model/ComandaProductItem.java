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
    public String productName;
    public int providerId;
    public String providerName;
    public int productTypeId;
    public String typeName;
    public Packaging packaging;
}
