package com.mcba.comandaclient.presenter;

/**
 * Created by mac on 24/07/2017.
 */

public interface ComandaSearchPresenter extends IBasePresenter {

    void fetchComandas();

    void fetchItems(int id, double cantBultos, double total, double senia, long timestamp);
}
