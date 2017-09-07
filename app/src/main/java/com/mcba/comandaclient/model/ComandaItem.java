package com.mcba.comandaclient.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mac on 26/06/2017.
 */

public class ComandaItem extends RealmObject implements Parcelable {

    @PrimaryKey
    public int itemId;
    public int comandaId;
    public ComandaProductItem mProductItem;
    public double mCant;
    public double mPrice;
    public double mTotal;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.itemId);
        dest.writeInt(this.comandaId);
        dest.writeParcelable(this.mProductItem, flags);
        dest.writeDouble(this.mCant);
        dest.writeDouble(this.mPrice);
        dest.writeDouble(this.mTotal);
    }

    public ComandaItem() {
    }

    protected ComandaItem(Parcel in) {
        this.itemId = in.readInt();
        this.comandaId = in.readInt();
        this.mProductItem = in.readParcelable(ComandaProductItem.class.getClassLoader());
        this.mCant = in.readDouble();
        this.mPrice = in.readDouble();
        this.mTotal = in.readDouble();
    }

    public static final Parcelable.Creator<ComandaItem> CREATOR = new Parcelable.Creator<ComandaItem>() {
        @Override
        public ComandaItem createFromParcel(Parcel source) {
            return new ComandaItem(source);
        }

        @Override
        public ComandaItem[] newArray(int size) {
            return new ComandaItem[size];
        }
    };
}
