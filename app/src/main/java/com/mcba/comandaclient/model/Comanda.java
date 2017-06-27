package com.mcba.comandaclient.model;

import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mac on 26/06/2017.
 */

public class Comanda extends RealmObject {

    @PrimaryKey
    public int comandaId;
    public List<ComandaItem> comandaItemList;

}
