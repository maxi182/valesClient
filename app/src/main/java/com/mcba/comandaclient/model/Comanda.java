package com.mcba.comandaclient.model;


import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mac on 26/06/2017.
 */

public class Comanda extends RealmObject {

    @PrimaryKey
    public int comandaId;
    public String date;
    public long timestamp;
    public double cantBultos;
    public double mTotal;
    public double mSenia;
    public boolean isPrinted;
    public int mClientId;
    public String mClientName;
    public RealmList<ComandaItem> comandaItemList;

}
