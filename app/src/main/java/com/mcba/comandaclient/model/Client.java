package com.mcba.comandaclient.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mac on 22/08/2017.
 */

public class Client extends RealmObject {

    @PrimaryKey
    public int clientId;
    public String mName;

}
