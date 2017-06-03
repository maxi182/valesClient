package com.mcba.comandaclient.ui;

import android.content.Context;
import android.content.Intent;

public class SplashScreenActivity extends BaseSplashScreenActivity {

    public static Intent getNewIntent(Context context) {
        Intent intent = new Intent(context, SplashScreenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }
    @Override
    protected Intent proceedLoading() {

        return MainActivity.getNewIntent(this);

    }
}
