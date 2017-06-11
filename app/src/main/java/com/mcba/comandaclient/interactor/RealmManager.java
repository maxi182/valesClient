package com.mcba.comandaclient.interactor;

import android.util.Log;

import io.realm.Realm;

import static android.content.ContentValues.TAG;

/**
 * Created by maximilianoferraiuolo on 14/11/2016.
 */

public abstract class RealmManager {

    protected Realm mRealm;


    protected void initRealm() {
        if (mRealm == null || mRealm.isClosed()) {
            mRealm = Realm.getDefaultInstance();
            Log.d(TAG, "mRealm (" + mRealm + ") INIT");
        }
    }

    protected void closeRealm() {
        if (mRealm != null) {
            mRealm.close();
            mRealm = null;
            Log.d(TAG, "mRealm (" + mRealm + ") CLOSED");
        }
    }
}
