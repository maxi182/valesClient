package com.mcba.comandaclient.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mac on 26/06/2017.
 */

public class ComandaItem extends RealmObject {

    @PrimaryKey
    public int itemId;
    public int comandaId;
    public ComandaProductItem mProductItem;
    public double mCant;
    public double mPrice;
    public double mTotal;

}
