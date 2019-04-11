package com.mcba.comandaclient.presenter;

import com.mcba.comandaclient.model.Comanda;

import java.util.List;

/**
 * Created by mac on 24/07/2017.
 */

public interface ComandaSearchPresenter extends IBasePresenter {

    void fetchComandas(String dateFrom);

    void fetchItems(int id, double cantBultos, double total, double senia, long timestamp);

    void fetchComandasById(int id);

    void processCSV(List<Comanda> listado);
}
