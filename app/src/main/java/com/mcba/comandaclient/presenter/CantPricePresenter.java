package com.mcba.comandaclient.presenter;


/**
 * Created by mac on 30/06/2017.
 */

public interface CantPricePresenter extends IBasePresenter {

    void getProducts();

    void getItemNameById(int productId, int providerId, int typeId);

    void getPackaging(int providerId, int productId, int typeId);

    void setQtyText(int value, boolean operation);

    void setPriceText(double currentValue, double addValue, boolean operation);

}
