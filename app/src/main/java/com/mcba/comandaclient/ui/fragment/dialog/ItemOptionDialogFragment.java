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
 * Created by mac on 12/08/2017.
 */

public class ItemOptionDialogFragment extends DialogFragment {

    private LinearLayout mBtnDelete;
    private LinearLayout mBtnDeleteVacio;
    private int mItemId;
    private IDialogCallbacks mCallbacks;
    private boolean mShowDeleteSenia;

    public void initDialog(IDialogCallbacks iDialogCallbacks, int itemId, boolean showDeleteSenia) {
        this.mCallbacks = iDialogCallbacks;
        this.mItemId = itemId;
        this.mShowDeleteSenia = !showDeleteSenia;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);

        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.dialog_fragment_options);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.WHITE));

        dialog.show();

        mBtnDelete = (LinearLayout) dialog.findViewById(R.id.btn_dialog_delete);
        mBtnDeleteVacio = (LinearLayout) dialog.findViewById(R.id.btn_dialog_delete_vacio);

        mBtnDeleteVacio.setVisibility(mShowDeleteSenia ? View.VISIBLE : View.GONE);

        mBtnDeleteVacio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mCallbacks.onDeleteVacioPress(dialog, mItemId);

            }
        });

        mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mCallbacks.onDeletePress(dialog, mItemId);
            }
        });

        return dialog;
    }
}
