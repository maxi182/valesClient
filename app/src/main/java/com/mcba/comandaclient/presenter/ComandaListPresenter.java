package com.mcba.comandaclient.presenter;

import com.mcba.comandaclient.model.Comanda;
import com.mcba.comandaclient.model.ComandaItem;
import com.mcba.comandaclient.model.ComandaList;
import com.mcba.comandaclient.model.ItemFullName;

import java.util.List;

/**
 * Created by mac on 08/07/2017.
 */

public interface ComandaListPresenter extends IBasePresenter {

    void storeComandas(ComandaList comandas);
    void prepareComandaForPrint(Comanda comanda);
    void storeComanda(int mComandaId, int lastItemId, int cant, double price, int productId,
                      int providerId, ItemFullName itemFullName, double packagePrice, List<ComandaItem> mComandaItemList);
    void fetchComandaById(int id);
    void fetchItemsComanda(int id);
    void fetchLastComanda();

}
