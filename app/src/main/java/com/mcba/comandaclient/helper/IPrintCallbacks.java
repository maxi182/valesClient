package com.mcba.comandaclient.helper;

import com.epson.epos2.printer.PrinterStatusInfo;

/**
 * Created by mac on 27/07/2017.
 */

public interface IPrintCallbacks {

    void displayPrinterWarning(PrinterStatusInfo status);
    void showProgress();
    void hideProgress();

}
