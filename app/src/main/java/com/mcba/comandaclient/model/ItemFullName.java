package com.mcba.comandaclient.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mac on 07/07/2017.
 */

public class ItemFullName implements Parcelable {

    public String productName;
    public String providerName;
    public String productTypeName;

    public ItemFullName(String productName, String providerName, String productTypeName) {
        this.productName = productName;
        this.providerName = providerName;
        this.productTypeName = productTypeName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.productName);
        dest.writeString(this.providerName);
        dest.writeString(this.productTypeName);
    }

    protected ItemFullName(Parcel in) {
        this.productName = in.readString();
        this.providerName = in.readString();
        this.productTypeName = in.readString();
    }

    public static final Parcelable.Creator<ItemFullName> CREATOR = new Parcelable.Creator<ItemFullName>() {
        @Override
        public ItemFullName createFromParcel(Parcel source) {
            return new ItemFullName(source);
        }

        @Override
        public ItemFullName[] newArray(int size) {
            return new ItemFullName[size];
        }
    };
}
