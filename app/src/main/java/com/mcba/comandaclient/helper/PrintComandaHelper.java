package com.mcba.comandaclient.helper;


import android.app.Activity;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.epson.epos2.Epos2Exception;
import com.epson.epos2.printer.Printer;
import com.epson.epos2.printer.PrinterStatusInfo;
import com.epson.epos2.printer.ReceiveListener;
import com.mcba.comandaclient.R;
import com.mcba.comandaclient.model.Comanda;
import com.mcba.comandaclient.model.ItemFullName;
import com.mcba.comandaclient.utils.Utils;

/**
 * Created by mac on 27/07/2017.
 */

public class PrintComandaHelper implements Handler.Callback, ReceiveListener {

    private static final String TARGET_PRINTER = "BT:00:01:90:C6:82:BE";

    private IPrintCallbacks mPrintCallbacks;
    private int mComandaId;
    private boolean mResult;
    private Printer mPrinter = null;
    private Activity mContext;
    private StringBuilder mComandaItems;

    private Handler mHandler = new Handler(this);

    public PrintComandaHelper(Activity context, StringBuilder comandaItems, int comandaId, IPrintCallbacks printCallbacks) {
        this.mContext = context;
        this.mPrintCallbacks = printCallbacks;
        this.mComandaItems = comandaItems;
        this.mComandaId = comandaId;

    }

    public void print() {
        mPrintCallbacks.showProgress();
        tryToPrintAsync();
    }

    private void tryToPrintAsync() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                mResult = runPrintReceiptSequence();
                mHandler.sendEmptyMessage(0);

            }
        }).start();
    }

    private boolean runPrintReceiptSequence() {
        if (!initializeObject()) {
            return false;
        }

        if (!createReceiptData()) {
            finalizeObject();
            return false;
        }

        if (!printData()) {
            finalizeObject();
            return false;
        }

        return true;
    }

    private void finalizeObject() {
        if (mPrinter == null) {
            return;
        }

        mPrinter.clearCommandBuffer();

        mPrinter.setReceiveEventListener(null);

        mPrinter = null;
    }

    private boolean printData() {
        if (mPrinter == null) {
            return false;
        }

        if (!connectPrinter()) {
            return false;
        }

        PrinterStatusInfo status = mPrinter.getStatus();

        displayPrinterWariningsOnMainThread(status);

        if (!isPrintable(status)) {
            ShowMsg.showMsg(makeErrorMessage(status), mContext);
            try {
                mPrinter.disconnect();
            } catch (Exception ex) {
                // Do nothing
            }
            return false;
        }

        try {
            mPrinter.sendData(Printer.PARAM_DEFAULT);
        } catch (Exception e) {
            runWarningsOnMainThread(e, "sendData");
            try {
                mPrinter.disconnect();
            } catch (Exception ex) {
                // Do nothing
            }
            return false;
        }

        return true;
    }


    private boolean isPrintable(PrinterStatusInfo status) {
        if (status == null) {
            return false;
        }

        if (status.getConnection() == Printer.FALSE) {
            return false;
        } else if (status.getOnline() == Printer.FALSE) {
            return false;
        } else {
            ;//print available
        }

        return true;
    }

    private boolean connectPrinter() {
        boolean isBeginTransaction = false;

        if (mPrinter == null) {
            return false;
        }

        try {
            mPrinter.connect(TARGET_PRINTER, Printer.PARAM_DEFAULT);
        } catch (Exception e) {
            runWarningsOnMainThread(e, "connect");
            return false;
        }

        try {
            mPrinter.beginTransaction();
            isBeginTransaction = true;
        } catch (Exception e) {
            runWarningsOnMainThread(e, "beginTransaction");
        }

        if (isBeginTransaction == false) {
            try {
                mPrinter.disconnect();
            } catch (Epos2Exception e) {
                // Do nothing
                return false;
            }
        }

        return true;
    }

    private boolean createReceiptData() {
        String method = "";
        StringBuilder textData = new StringBuilder();


        if (mPrinter == null) {
            return false;
        }

        try {
            method = "addTextAlign";
            mPrinter.addTextAlign(Printer.ALIGN_CENTER);

            method = "addFeedLine";
            mPrinter.addFeedLine(1);
            textData.append(mContext.getString(R.string.venue_name));
            textData.append(mContext.getString(R.string.company_name));
            textData.append("\n");
            mPrinter.addTextAlign(Printer.ALIGN_LEFT);
            textData.append("Comprobante Nro: " + String.valueOf(mComandaId) + "\n");
            textData.append(Utils.getCurrentDate("dd/MM/yyyy  HH:mm\n"));
            mPrinter.addTextAlign(Printer.ALIGN_CENTER);

            textData.append("------------------------------\n");
            method = "addText";
            mPrinter.addText(textData.toString());
            textData.delete(0, textData.length());

            method = "addText";
            mPrinter.addText(mComandaItems.toString());

            textData.append("------------------------------\n");
            method = "addText";
            mPrinter.addText(textData.toString());
            textData.delete(0, textData.length());

            textData.append("SUBTOTAL                160.38\n");
            textData.append("TAX                      14.43\n");
            method = "addText";
            mPrinter.addText(textData.toString());
            textData.delete(0, textData.length());

            method = "addTextSize";
            mPrinter.addTextSize(2, 2);
            method = "addText";
            mPrinter.addText("TOTAL    174.81\n");
            method = "addTextSize";
            mPrinter.addTextSize(1, 1);
            method = "addFeedLine";
            mPrinter.addFeedLine(1);

            textData.append("CASH                    200.00\n");
            textData.append("CHANGE                   25.19\n");
            textData.append("------------------------------\n");
            method = "addText";
            mPrinter.addText(textData.toString());
            textData.delete(0, textData.length());

            textData.append("Purchased item total number\n");
            textData.append("Sign Up and Save !\n");
            textData.append("With Preferred Saving Card\n");
            method = "addText";
            mPrinter.addText(textData.toString());
            textData.delete(0, textData.length());
            method = "addFeedLine";
            mPrinter.addFeedLine(2);


            method = "addCut";
            // mPrinter.addCut(Printer.CUT_FEED);
        } catch (Exception e) {
            runWarningsOnMainThread(e, method);

            return false;
        }

        textData = null;

        return true;
    }

    private String makeErrorMessage(PrinterStatusInfo status) {
        String msg = "";

        if (status.getOnline() == Printer.FALSE) {
            msg += mContext.getString(R.string.handlingmsg_err_offline);
        }
        if (status.getConnection() == Printer.FALSE) {
            msg += mContext.getString(R.string.handlingmsg_err_no_response);
        }
        if (status.getCoverOpen() == Printer.TRUE) {
            msg += mContext.getString(R.string.handlingmsg_err_cover_open);
        }
        if (status.getPaper() == Printer.PAPER_EMPTY) {
            msg += mContext.getString(R.string.handlingmsg_err_receipt_end);
        }
        if (status.getPaperFeed() == Printer.TRUE || status.getPanelSwitch() == Printer.SWITCH_ON) {
            msg += mContext.getString(R.string.handlingmsg_err_paper_feed);
        }
        if (status.getErrorStatus() == Printer.MECHANICAL_ERR || status.getErrorStatus() == Printer.AUTOCUTTER_ERR) {
            msg += mContext.getString(R.string.handlingmsg_err_autocutter);
            msg += mContext.getString(R.string.handlingmsg_err_need_recover);
        }
        if (status.getErrorStatus() == Printer.UNRECOVER_ERR) {
            msg += mContext.getString(R.string.handlingmsg_err_unrecover);
        }
        if (status.getErrorStatus() == Printer.AUTORECOVER_ERR) {
            if (status.getAutoRecoverError() == Printer.HEAD_OVERHEAT) {
                msg += mContext.getString(R.string.handlingmsg_err_overheat);
                msg += mContext.getString(R.string.handlingmsg_err_head);
            }
            if (status.getAutoRecoverError() == Printer.MOTOR_OVERHEAT) {
                msg += mContext.getString(R.string.handlingmsg_err_overheat);
                msg += mContext.getString(R.string.handlingmsg_err_motor);
            }
            if (status.getAutoRecoverError() == Printer.BATTERY_OVERHEAT) {
                msg += mContext.getString(R.string.handlingmsg_err_overheat);
                msg += mContext.getString(R.string.handlingmsg_err_battery);
            }
            if (status.getAutoRecoverError() == Printer.WRONG_PAPER) {
                msg += mContext.getString(R.string.handlingmsg_err_wrong_paper);
            }
        }
        if (status.getBatteryLevel() == Printer.BATTERY_LEVEL_0) {
            msg += mContext.getString(R.string.handlingmsg_err_battery_real_end);
        }

        return msg;
    }


    private boolean initializeObject() {
        try {
            mPrinter = new Printer(Printer.TM_M30, Printer.MODEL_ANK, mContext);
        } catch (Exception e) {
            runWarningsOnMainThread(e, "Printer");
            return false;
        }

        mPrinter.setReceiveEventListener(this);

        return true;
    }

    private void runWarningsOnMainThread(final Exception e, final String message) {

        mContext.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mPrintCallbacks.hideProgress();
                ShowMsg.showException(e, message, mContext);
            }
        });
    }

    private void displayPrinterWariningsOnMainThread(final PrinterStatusInfo status) {

        mContext.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Log.i("STATUS_MSG1", status)
                ShowMsg.showMsg(makeErrorMessage(status), mContext);

            }
        });
    }

    private void disconnectPrinter() {
        if (mPrinter == null) {
            return;
        }

        try {
            mPrinter.endTransaction();
        } catch (final Exception e) {
            mContext.runOnUiThread(new Runnable() {
                @Override
                public synchronized void run() {
                    runWarningsOnMainThread(e, "endTransaction");
                }
            });
        }

        try {
            mPrinter.disconnect();
        } catch (final Exception e) {
            mContext.runOnUiThread(new Runnable() {
                @Override
                public synchronized void run() {
                    mPrintCallbacks.hideProgress();

                    ShowMsg.showException(e, "disconnect", mContext);
                }
            });
        }

        finalizeObject();
    }


    @Override
    public boolean handleMessage(Message message) {
        return false;
    }

    @Override
    public void onPtrReceive(final Printer printerObj, final int code, final PrinterStatusInfo status, final String printJobId) {

        mContext.runOnUiThread(new Runnable() {
            @Override
            public synchronized void run() {
                ShowMsg.showResult(code, makeErrorMessage(status), mContext);

                mPrintCallbacks.hideProgress();

                //  dispPrinterWarnings(status);

                //  updateButtonState(true);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        disconnectPrinter();
                    }
                }).start();
            }
        });
    }
}



