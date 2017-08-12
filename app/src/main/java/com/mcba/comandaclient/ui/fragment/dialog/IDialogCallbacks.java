package com.mcba.comandaclient.ui.fragment.dialog;

import android.app.Dialog;

/**
 * Created by mac on 29/07/2017.
 */

public interface IDialogCallbacks {

    void dismissDialog();
    void onOkPress(Dialog dialog, boolean isSuccess);
}
