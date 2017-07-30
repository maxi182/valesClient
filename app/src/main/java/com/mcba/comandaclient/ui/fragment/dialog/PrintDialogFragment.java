package com.mcba.comandaclient.ui.fragment.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.mcba.comandaclient.R;

/**
 * Created by mac on 29/07/2017.
 */

public class PrintDialogFragment extends DialogFragment {

    private IDialogCallbacks mCallbacks;
    private LinearLayout mLinearMessage;
    private LinearLayout mLinearPriting;
    private boolean mShowMessage;

    public PrintDialogFragment() {

    }

    public void initDialog(IDialogCallbacks iDialogCallbacks, boolean showMessage) {
        this.mCallbacks = iDialogCallbacks;
        this.mShowMessage = showMessage;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);

        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.dialog_fragment_print);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.WHITE));
        dialog.show();

        mLinearPriting.setVisibility(mShowMessage ? View.GONE : View.VISIBLE);
        mLinearMessage.setVisibility(mShowMessage ? View.VISIBLE : View.GONE);


        return dialog;

    }
}
