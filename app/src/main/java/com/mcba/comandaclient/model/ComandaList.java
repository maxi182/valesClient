package com.mcba.comandaclient.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mac on 08/07/2017.
 */

public class ComandaList extends RealmObject {

    @PrimaryKey
    public int id;
    @SerializedName("comandas")
    public RealmList<Comanda> comandas;
}
