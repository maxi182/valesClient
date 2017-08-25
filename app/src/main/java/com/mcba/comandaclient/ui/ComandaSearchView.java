package com.mcba.comandaclient.ui;

import com.mcba.comandaclient.model.Comanda;

import io.realm.RealmList;

/**
 * Created by mac on 24/07/2017.
 */

public interface ComandaSearchView {

    void onComandasFetched(RealmList<Comanda> listComandas);

}
