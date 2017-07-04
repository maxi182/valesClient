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
    public RealmList<ComandaItem> comandaItemList;

}
