package com.mcba.comandaclient.presenter;

import com.mcba.comandaclient.model.Comanda;
import com.mcba.comandaclient.model.ComandaList;

/**
 * Created by mac on 08/07/2017.
 */

public interface ComandaListPresenter extends IBasePresenter {

    void storeComandas(ComandaList comandas);
    void storeComanda(Comanda comandas);
    void fetchComandaById(int id);
    void fetchLastComanda();

}
