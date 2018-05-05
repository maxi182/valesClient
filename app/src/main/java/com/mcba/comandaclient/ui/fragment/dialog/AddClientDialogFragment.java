package com.mcba.comandaclient.ui.fragment.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.mcba.comandaclient.R;

/**
 * Created by mac on 13/04/2018.
 */

public class AddClientDialogFragment extends DialogFragment {

    private IAddClientDialogCallbacks mCallbacks;
    private AppCompatEditText mNameEditText;
    private Context mContext;
    private TextView mAccept;
    private TextView mCancel;
    private TextView mClientIdTextview;
    private int mClientId;


    public AddClientDialogFragment() {

    }

    public void initDialog(Context context, IAddClientDialogCallbacks iDialogCallbacks, int clientId) {
        this.mCallbacks = iDialogCallbacks;
        this.mContext = context;
        this.mClientId = clientId;
    }


    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);

        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.dialog_fragment_add_client);
        dialog.setCanceledOnTouchOutside(false);

        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.WHITE));

        dialog.show();

        mNameEditText = (AppCompatEditText) dialog.findViewById(R.id.name_edit_text);
        mAccept = (TextView) dialog.findViewById(R.id.accept_textview);
        mCancel = (TextView) dialog.findViewById(R.id.cancel_textview);
        mClientIdTextview = (TextView) dialog.findViewById(R.id.client_id);

        mClientIdTextview.setText("Cod Cliente: " + String.valueOf(mClientId));

        mAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mNameEditText.length() > 0) {
                    mCallbacks.onDialogClientAccept(mNameEditText.getText().toString().toUpperCase(), mClientId, dialog);
                    // dialog.dismiss();
                } else {
                    Toast.makeText(mContext, "Debe ingresar nombre cliente", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        return dialog;

    }


}
