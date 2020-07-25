package com.mcba.comandaclient.ui.fragment.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by mac on 02/05/2018.
 */

public class TimePickerDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private static final String SINCE = "since";
    private static final String TO = "to";

    private ITimePickerCallbacks mCallbacks;


    public void initDialog(ITimePickerCallbacks iTimePickerCallbacks) {
        this.mCallbacks = iTimePickerCallbacks;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

        return datePickerDialog;

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

        String monthWithCero = month < 10 ? "0".concat(String.valueOf(month + 1)) : String.valueOf(month + 1);
        String dayWithCero = day < 10 ? "0".concat(String.valueOf(day)) : String.valueOf(day);
        mCallbacks.onDateSelected(String.valueOf(year) + monthWithCero + dayWithCero, dayWithCero + " / " + monthWithCero + " / " + String.valueOf(year).substring(2, 4));
    }
}
