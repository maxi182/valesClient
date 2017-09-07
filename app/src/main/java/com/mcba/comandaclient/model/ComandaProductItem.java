package com.mcba.comandaclient.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mac on 03/07/2017.
 */

public class ComandaProductItem extends RealmObject implements Parcelable {

    @PrimaryKey
    public int productItemId;
    public int productId;
    public String productName;
    public int providerId;
    public String providerName;
    public int productTypeId;
    public String typeName;
    public Packaging packaging;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.productItemId);
        dest.writeInt(this.productId);
        dest.writeString(this.productName);
        dest.writeInt(this.providerId);
        dest.writeString(this.providerName);
        dest.writeInt(this.productTypeId);
        dest.writeString(this.typeName);
        dest.writeParcelable(this.packaging, flags);
    }

    public ComandaProductItem() {
    }

    protected ComandaProductItem(Parcel in) {
        this.productItemId = in.readInt();
        this.productId = in.readInt();
        this.productName = in.readString();
        this.providerId = in.readInt();
        this.providerName = in.readString();
        this.productTypeId = in.readInt();
        this.typeName = in.readString();
        this.packaging = in.readParcelable(Packaging.class.getClassLoader());
    }

    public static final Parcelable.Creator<ComandaProductItem> CREATOR = new Parcelable.Creator<ComandaProductItem>() {
        @Override
        public ComandaProductItem createFromParcel(Parcel source) {
            return new ComandaProductItem(source);
        }

        @Override
        public ComandaProductItem[] newArray(int size) {
            return new ComandaProductItem[size];
        }
    };
}
