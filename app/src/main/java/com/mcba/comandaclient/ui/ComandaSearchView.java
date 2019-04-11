package com.mcba.comandaclient.ui;

import android.content.Intent;

import com.mcba.comandaclient.model.Comanda;
import com.mcba.comandaclient.model.ComandaItem;

import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by mac on 24/07/2017.
 */

public interface ComandaSearchView {

    void onComandasFetched(RealmList<Comanda> listComandas);

    void onItemsFetched(RealmList<ComandaItem> items, int id, double cantBultos, double total, double senia, long timestamp);

    void onComandasByIdFetched(RealmResults<Comanda> listComandas);

    void onSendEmail(Intent emaildata);

}
