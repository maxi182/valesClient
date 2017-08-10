package com.mcba.comandaclient.ui.fragment.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.epson.epos2.Epos2CallbackCode;
import com.mcba.comandaclient.R;

/**
 * Created by mac on 29/07/2017.
 */

public class PrintDialogFragment extends DialogFragment {

    private IDialogCallbacks mCallbacks;
    private LinearLayout mLinearMessage;
    private LinearLayout mLinearPriting;
    private TextView mTxtMessage;
    private TextView mBtnOk;
    private String mMessage;
    private ImageView mVectorImage;
    private boolean mShowMessage;


    public PrintDialogFragment() {

    }

    public void initDialog(IDialogCallbacks iDialogCallbacks, boolean showMessage, String message) {
        this.mCallbacks = iDialogCallbacks;
        this.mShowMessage = showMessage;
        this.mMessage = message;
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

        mTxtMessage = (TextView) dialog.findViewById(R.id.txt_alert_message);
        mBtnOk = (TextView) dialog.findViewById(R.id.btn_dialog_ok);
        mLinearPriting = (LinearLayout) dialog.findViewById(R.id.linear_printing);
        mLinearMessage = (LinearLayout) dialog.findViewById(R.id.linear_message);
        mVectorImage = (ImageView) dialog.findViewById(R.id.img_dialog);
        mLinearPriting.setVisibility(mShowMessage ? View.GONE : View.VISIBLE);
        mLinearMessage.setVisibility(mShowMessage ? View.VISIBLE : View.GONE);

        if (mMessage != null && mMessage.contains("PRINT_SUCCESS")) {
            mVectorImage.setImageResource(R.drawable.ic_check_circle);
            mTxtMessage.setText(dialog.getContext().getString(R.string.print_success));
        } else {
            mTxtMessage.setText(mMessage);
            mVectorImage.setImageResource(R.drawable.ic_error_outline);
        }
        mBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallbacks.onOkPress(dialog);
            }
        });


        return dialog;

    }


}
