package com.mcba.comandaclient;

import android.app.Application;

import com.mcba.comandaclient.api.RestClient;

import io.realm.Realm;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by mac on 27/05/2017.
 */

public class ComandaClientApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        RestClient.init(this);
        Realm.init(this);

        initializeCalligraphy();
    }

    private void initializeCalligraphy() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(getString(R.string.font_system_font))
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

}
