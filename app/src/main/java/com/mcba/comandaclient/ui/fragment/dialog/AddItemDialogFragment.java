package com.mcba.comandaclient.ui.fragment.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatEditText;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.mcba.comandaclient.R;

/**
 * Created by mac on 05/05/2018.
 */

public class AddItemDialogFragment extends DialogFragment {


    private IAddItemCallbacks mCallbacks;
    private AppCompatEditText mNameEditText;
    private Context mContext;
    private TextView mAccept;
    private TextView mCancel;
    private TextView mClientIdTextview;
    private int mDialogToOpen;


    public AddItemDialogFragment() {

    }

    public void initDialog(Context context, IAddItemCallbacks iDialogCallbacks, int dialogToOpen) {
        this.mCallbacks = iDialogCallbacks;
        this.mContext = context;
        this.mDialogToOpen = dialogToOpen;
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
        dialog.setContentView(R.layout.dialog_fragment_add_new);
        dialog.setCanceledOnTouchOutside(false);

        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.WHITE));

        dialog.show();

        mNameEditText = (AppCompatEditText) dialog.findViewById(R.id.name_edit_text);
        mAccept = (TextView) dialog.findViewById(R.id.accept_textview);
        mCancel = (TextView) dialog.findViewById(R.id.cancel_textview);
        mClientIdTextview = (TextView) dialog.findViewById(R.id.client_id);


        mAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mNameEditText.length() > 0) {
                    mCallbacks.onAddItem(mNameEditText.getText().toString().toUpperCase(), mDialogToOpen);
                    // dialog.dismiss();
                } else {
                    Toast.makeText(mContext, "Debe ingresar nombre", Toast.LENGTH_SHORT).show();
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