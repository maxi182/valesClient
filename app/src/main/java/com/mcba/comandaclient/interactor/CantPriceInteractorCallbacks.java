package com.mcba.comandaclient.interactor;



/**
 * Created by mac on 30/06/2017.
 */

public interface CantPriceInteractorCallbacks {


    interface CantPriceRequestCallback {

        void onPacakgeParsed(boolean isFree, double value);
        void onLastItemIdFetched(int id);
    }

}
