package com.mcba.comandaclient.ui;

import com.mcba.comandaclient.model.NameId;
import com.mcba.comandaclient.model.Provider;

import java.util.List;

/**
 * Created by mac on 05/05/2018.
 */

public interface AddProviderView {

    void onProvidersFetched(List<Provider> providers);
    void onProductsFetched(List<NameId> products);


}
