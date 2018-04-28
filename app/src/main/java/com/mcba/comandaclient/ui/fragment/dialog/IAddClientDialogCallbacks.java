package com.mcba.comandaclient.ui.fragment.dialog;

import android.app.Dialog;

/**
 * Created by mac on 13/04/2018.
 */

public interface IAddClientDialogCallbacks {

    void onDialogClientAccept(String clientName, int id, Dialog dialog);
    void onDialogClientCancel();

}
