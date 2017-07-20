package com.mcba.comandaclient.ui;

import com.mcba.comandaclient.model.ItemFullName;
import com.mcba.comandaclient.model.Product;
import com.mcba.comandaclient.model.ProductType;
import com.mcba.comandaclient.model.ProviderList;

import java.util.List;

import io.realm.RealmList;

/**
 * Created by mac on 28/06/2017.
 */

public interface CantPriceView {

    void showProductName(ItemFullName name);

    void showDataResponse(RealmList<ProviderList> providers, RealmList<Product> products);

    void showPackagingResponse(boolean isFree, double value);

    void showLastItemId(int id);

    void showTypesResponse(List<ProductType> types);

    void updateQtyText(int value);

    void updatePriceText(double value);


}
