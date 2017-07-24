package com.mcba.comandaclient.ui;

import com.mcba.comandaclient.model.Comanda;
import com.mcba.comandaclient.model.ComandaItem;

import io.realm.RealmList;

/**
 * Created by mac on 08/07/2017.
 */

public interface ComandaListView {

    void showComanda(Comanda comanda);

    void showItemsComanda(RealmList<ComandaItem> items);

    void showTotales(double total, double totalSenia, double cant);

    void showLastComandaId(int id);

    void onStoreItemSuccess(boolean isSuccess);

    void onStoreItemFail();

    void onFetchItemFail();

}
