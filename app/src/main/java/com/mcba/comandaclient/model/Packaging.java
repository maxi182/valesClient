package com.mcba.comandaclient.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

/**
 * Created by mac on 05/06/2017.
 */

public class Packaging extends RealmObject implements Parcelable {

    public boolean isFree;
    public double value;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isFree ? (byte) 1 : (byte) 0);
        dest.writeDouble(this.value);
    }

    public Packaging() {
    }

    protected Packaging(Parcel in) {
        this.isFree = in.readByte() != 0;
        this.value = in.readDouble();
    }

    public static final Parcelable.Creator<Packaging> CREATOR = new Parcelable.Creator<Packaging>() {
        @Override
        public Packaging createFromParcel(Parcel source) {
            return new Packaging(source);
        }

        @Override
        public Packaging[] newArray(int size) {
            return new Packaging[size];
        }
    };
}
