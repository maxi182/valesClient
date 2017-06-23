package com.mcba.comandaclient.ui.fragment;

import android.os.Bundle;

import com.mcba.comandaclient.model.Product;
import com.mcba.comandaclient.model.Provider;
import com.mcba.comandaclient.model.ProviderList;
import com.mcba.comandaclient.ui.ProductsListView;

import java.util.List;

import io.realm.RealmList;

/**
 * Created by mac on 22/06/2017.
 */

public class ProductTypeSelectionFragment extends BaseNavigationFragment<ProductTypeSelectionFragment.ProductTypeSelectionFragmentCallbacks> implements ProductsListView {
    @Override
    public void showDataResponse(RealmList<ProviderList> providers, RealmList<Product> products) {

    }

    @Override
    public void showProvidersResponse(List<Provider> providers) {

    }

    @Override
    public void onResponseFailed() {

    }

    @Override
    public void realmStoreCompleted() {

    }

    @Override
    public void realmStoreFailed() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onItemPress() {

    }

    @Override
    protected int getLayoutResource() {
        return 0;
    }

    @Override
    protected void setViewReferences() {

    }

    @Override
    protected void setupFragment(Bundle savedInstanceState) {

    }


    public interface ProductTypeSelectionFragmentCallbacks {
        void onGoToSelectProductType(int providerId);
    }

    @Override
    public ProductTypeSelectionFragment.ProductTypeSelectionFragmentCallbacks getDummyCallbacks() {
        return new ProductTypeSelectionFragment.ProductTypeSelectionFragmentCallbacks() {
            @Override
            public void onGoToSelectProductType(int providerId) {

            }
        };
    }
}
