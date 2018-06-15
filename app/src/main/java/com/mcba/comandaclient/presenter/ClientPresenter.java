package com.mcba.comandaclient.presenter;

import com.mcba.comandaclient.model.Client;

import java.util.List;

/**
 * Created by mac on 14/01/2018.
 */

public interface ClientPresenter extends IBasePresenter {


    void fetchClients();

    void filterClientByName(String name);

    void storeClient(String name);

    void validateExistingUser(String name, List<Client> clients);
}
