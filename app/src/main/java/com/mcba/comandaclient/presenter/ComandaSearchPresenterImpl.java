package com.mcba.comandaclient.presenter;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import com.adesigns.csv.creator.CsvCreator;
import com.mcba.comandaclient.interactor.ComandaSearchInteractorCallbacks;
import com.mcba.comandaclient.interactor.ComandaSearchInteractorImpl;
import com.mcba.comandaclient.model.CSVData;
import com.mcba.comandaclient.model.Comanda;
import com.mcba.comandaclient.model.ComandaItem;
import com.mcba.comandaclient.ui.ComandaListView;
import com.mcba.comandaclient.ui.ComandaSearchView;
import com.mcba.comandaclient.utils.Utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by mac on 24/07/2017.
 */

public class ComandaSearchPresenterImpl implements ComandaSearchPresenter, ComandaSearchInteractorCallbacks.RequestCallback {

    private ComandaSearchInteractorCallbacks mComandaSearchInteractorCallbacks;
    private WeakReference<ComandaSearchView> comandaSearchView;


    public ComandaSearchPresenterImpl(ComandaSearchView comandaSearchView) {
        this.mComandaSearchInteractorCallbacks = new ComandaSearchInteractorImpl();
        this.comandaSearchView = new WeakReference<>(comandaSearchView);
    }

    @Override
    public void fetchComandas(String dateFrom) {

        mComandaSearchInteractorCallbacks.fetchComandas(this, dateFrom);

    }

    @Override
    public void fetchItems(int id, double cantBultos, double total, double senia, long timestamp) {
        mComandaSearchInteractorCallbacks.fetchItemsComanda(this, id, cantBultos, total, senia, timestamp);

    }

    @Override
    public void fetchComandasById(int id) {

        mComandaSearchInteractorCallbacks.fetchComandasById(this, id);

    }

    @Override
    public void processCSV(List<Comanda> listadoVales) {
        List<CSVData> pojoList = new LinkedList<CSVData>();
        Date fecha = null;
        double totaldia = 0;

        if (listadoVales != null) {
            if (listadoVales.size() > 0) {
                fecha = Utils.stringToDate(listadoVales.get(0).date);
            }
            CSVData csvdataHeader = new CSVData();

            csvdataHeader.setIdColumn("ID comanda");
            csvdataHeader.setClienteColumn("Cliente");
            csvdataHeader.setTotalColumn("Total");
            pojoList.add(csvdataHeader);

            for (int i = 0; i < listadoVales.size(); i++) {
                CSVData csvData = new CSVData();
                csvData.setIdColumn(String.valueOf(listadoVales.get(i).comandaId));
                csvData.setDateColumn(Utils.stringToDate(listadoVales.get(i).date));
                csvData.setClienteColumn(listadoVales.get(i).mClientName);
                csvData.setTotalColumn(String.valueOf(listadoVales.get(i).mTotal));

                totaldia = totaldia + listadoVales.get(i).mTotal;

                pojoList.add(csvData);
                // csvData.setIntegerColumn(listadoVales.get(i).);
            }
            pojoList.add(new CSVData("TOTAL", fecha, String.valueOf(totaldia)));

            saveFile(pojoList, fecha);
        }

    }

    private void saveFile(List<CSVData> pojoList, Date fecha) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Writer writer = new OutputStreamWriter(outputStream);

        CsvCreator<CSVData> csvCreator = new CsvCreator<CSVData>(writer, CSVData.class);

        try {
            csvCreator.write(pojoList);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        byte[] csvOutput = outputStream.toByteArray();

        File valesFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Vales.csv");
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(valesFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            bos.write(csvOutput);

            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        sendEmail(valesFile, fecha);

    }

    private void sendEmail(File valesFile, Date fecha) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"maxi08@gmail.com"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Listado Vales emitidos dia: " + fecha);
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");

        if (!valesFile.exists() || !valesFile.canRead()) {
            return;
        }

        Uri uri = Uri.fromFile(valesFile);
        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
        if (comandaSearchView != null) {
            getView().onSendEmail(emailIntent);
        }

    }

    @Override
    public void onFetchComandasByIdSuccess(RealmResults<Comanda> comandas) {

        if (comandaSearchView != null) {
            getView().onComandasByIdFetched(comandas);
        }

    }


    private ComandaSearchView getView() {
        return (comandaSearchView != null) ? comandaSearchView.get() : null;
    }

    @Override
    public void detachView() {
        if (comandaSearchView != null) {
            comandaSearchView.clear();
            comandaSearchView = null;
        }
        mComandaSearchInteractorCallbacks.detachView();
    }

    @Override
    public void attachView() {
        mComandaSearchInteractorCallbacks.attachView();
    }


    @Override
    public void onFetchComandasSuccess(RealmList<Comanda> comandas) {
        if (comandaSearchView != null) {
            getView().onComandasFetched(comandas);
        }
    }

    @Override
    public void onFetchComandaItems(RealmList<ComandaItem> items, int id, double cantBultos, double total, double senia, long timestamp) {
        if (comandaSearchView != null) {
            getView().onItemsFetched(items, id, cantBultos, total, senia, timestamp);
        }
    }


}
