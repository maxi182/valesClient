package com.mcba.comandaclient;

import android.app.Application;
import android.os.StrictMode;
import android.util.Log;

import com.mcba.comandaclient.api.RestClient;
import com.mcba.comandaclient.utils.StorageProvider;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by mac on 27/05/2017.
 */

public class ComandaClientApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Realm.init(this);

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(getApplicationContext())
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .migration(new MyMigration())
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);


        StorageProvider.init(this);
        RestClient.init(this);
        initializeCalligraphy();

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }

    private void initializeCalligraphy() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(getString(R.string.font_system_font))
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    private class MyMigration implements RealmMigration {
        @Override
        public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
            Log.w(TAG, "migrate() called with: " + "realm = [" + realm + "]," +
                    "oldVersion = [" + oldVersion + "], newVersion = [" + newVersion + "]");
        }
    }

}
