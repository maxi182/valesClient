package com.mcba.comandaclient.model;

/**
 * Created by mac on 07/07/2017.
 */

public class ItemFullName {

    public String productName;
    public String providerName;
    public String productTypeName;

    public ItemFullName(String productName, String providerName, String productTypeName) {
        this.productName = productName;
        this.providerName = providerName;
        this.productTypeName = productTypeName;
    }
}
