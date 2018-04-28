package com.mcba.comandaclient.ui;

import com.mcba.comandaclient.model.Client;

import java.util.List;

/**
 * Created by mac on 14/01/2018.
 */

public interface ClientView {

    void showClientList(List<Client> clientList);
    void storeSuccess(boolean isSuccess);
    void clientExists(boolean exist, String name);
}
