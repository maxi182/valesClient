package com.mcba.comandaclient.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by mac on 30/05/2017.
 */

public class Utils {

    public static boolean isNetworkConnectionAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo == null) {
            return false;
        }
        return networkInfo.isConnectedOrConnecting();
    }


    public static String getCurrentDate(String format) {

        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat(format);
        String formattedDate = df.format(c.getTime());

        return formattedDate;
    }

    public static Date stringToDate(String date) {

        Date dateConverted = null;
        SimpleDateFormat format = new SimpleDateFormat("ddMMyy");
        try {
            dateConverted = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateConverted;
    }


    public static long getCurrentDateInMillis(String format, String date) {
        long timeInMilliseconds = 0;
        SimpleDateFormat df = new SimpleDateFormat(format);
        try {
            Date mDate = df.parse(date.replace(" ", ""));
            timeInMilliseconds = mDate.getTime();
            System.out.println("Date in milli :: " + timeInMilliseconds);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return timeInMilliseconds;
    }

    public static String getFormattedStrinDate(String strCurrentDate,String myformat)
    {
        SimpleDateFormat format = new SimpleDateFormat(myformat);
        Date newDate = null;
        try {
            newDate = format.parse(strCurrentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        format = new SimpleDateFormat("MMM dd,yyyy hh:mm a");
        String date = format.format(newDate);

        return date;
    }
    public static String setDecimalFormat(double value) {

        DecimalFormat decimalFormat = new DecimalFormat("#");
        return decimalFormat.format(value);

    }

    public static long getTimeStamp() {

        return System.currentTimeMillis();
    }

    public static String getTimeFromTimeStamp(long timestamp, String format) {

        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String time = formatter.format(new Date(timestamp));
        return time;
    }

    public static String padBlanks(String text, int maxChars) {

        StringBuilder sb = new StringBuilder();
        int rest = maxChars - text.length();
        for (int i = 1; i < rest; i++) {
            sb.append(" ");
        }
        return text.concat(sb.toString());

    }

    public static String padLeft(String text, int maxChars) {

        StringBuilder sb = new StringBuilder();
        int rest = maxChars - text.length();
        for (int i = 1; i < rest; i++) {
            sb.append(" ");
        }
        sb.append(text);
        return sb.toString();

    }

    public static boolean qtyOfDots(String str) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '.') {
                count++;
            }
        }
        return count > 1 ? true : false;
    }
}
