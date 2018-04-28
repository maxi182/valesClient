package com.mcba.comandaclient.presenter;

import com.mcba.comandaclient.interactor.ComandaInteractorCallbacks;
import com.mcba.comandaclient.interactor.ComandaInteractorImpl;
import com.mcba.comandaclient.model.Client;
import com.mcba.comandaclient.model.Comanda;
import com.mcba.comandaclient.model.ComandaItem;
import com.mcba.comandaclient.model.ComandaList;
import com.mcba.comandaclient.model.ComandaProductItem;
import com.mcba.comandaclient.model.ItemFullName;
import com.mcba.comandaclient.model.Packaging;
import com.mcba.comandaclient.ui.ComandaListView;
import com.mcba.comandaclient.utils.Utils;

import java.lang.ref.WeakReference;
import java.util.List;

import io.realm.RealmList;

/**
 * Created by mac on 08/07/2017.
 */

public class ComandaListPresenterImpl implements ComandaListPresenter, ComandaInteractorCallbacks.RequestCallback {

    private ComandaInteractorCallbacks mComandaInteractorCallbacks;
    private WeakReference<ComandaListView> comandaListView;


    public ComandaListPresenterImpl(ComandaListView comandaListView) {
        this.mComandaInteractorCallbacks = new ComandaInteractorImpl();
        this.comandaListView = new WeakReference<>(comandaListView);
    }

    @Override
    public void storeComandas(ComandaList comandas) {

        mComandaInteractorCallbacks.storeComandas(this, comandas);

    }

    @Override
    public void prepareComandaForPrint(Comanda comanda) {

        StringBuilder textItems = new StringBuilder();
        StringBuilder textSubTotales = new StringBuilder();
        StringBuilder textTotal = new StringBuilder();
        StringBuilder textItemsCopy = new StringBuilder();

        int itemLen = comanda.comandaItemList.size();
        for (int i = 0; i < itemLen; i++) {
            ComandaItem comandaItem = comanda.comandaItemList.get(i);

            String product = comandaItem.mProductItem.productName + " "
                    + comandaItem.mProductItem.providerName + " "
                    + comandaItem.mProductItem.typeName;

            textItems.append(String.valueOf(Utils.setDecimalFormat(comandaItem.mCant)) + " "
                    + Utils.padBlanks(product, 30) + " "
                    + String.valueOf(comandaItem.mPrice) + " "
                    + String.valueOf(comandaItem.mTotal) + "\n");

            textItemsCopy.append(String.valueOf(Utils.setDecimalFormat(comandaItem.mCant)) + " "
                    + Utils.padBlanks(product, 35) + " "
                    + "\n");

            if (!comandaItem.mProductItem.packaging.isFree) {
                String vacio = "Vacio con seña";
                textItems.append(String.valueOf(Utils.setDecimalFormat(comandaItem.mCant)) + " "
                        + Utils.padBlanks(vacio, 33) + comandaItem.mProductItem.packaging.value + " "
                        + String.valueOf(comandaItem.mProductItem.packaging.value * comandaItem.mCant + "\n"));
            }
        }

        textSubTotales.append("------------------------------\n");
        textSubTotales.append("SUBTOTAL" + Utils.padLeft(String.valueOf(comanda.mTotal - comanda.mSenia), 22) + "\n");
        textSubTotales.append("SEÑA" + Utils.padLeft(String.valueOf(comanda.mSenia), 26) + "\n");

        textTotal.append("TOTAL" + Utils.padLeft(String.valueOf(comanda.mTotal), 11) + "\n");

        if (comandaListView != null) {
            getView().onFetchComandaItemsForPrint(textItems, textSubTotales, textTotal, textItemsCopy);
        }
    }

    @Override
    public void storeComanda(int mComandaId, int lastItemId, int clientId, int cant, double price, int productId, int providerId, ItemFullName itemFullName, double packagePrice, List<ComandaItem> mComandaItemList, String clientName, boolean isPrinted) {

        double total = 0;
        double totalSenia = 0;
        double bultos = 0;
        Comanda comanda = new Comanda();
        comanda.comandaId = mComandaId;
        comanda.isPrinted = isPrinted;
        Client client = new Client();
        client.mName = clientName;
        client.clientId = clientId;
        comanda.mCliente = client;

        ComandaItem comandaItem = new ComandaItem();
        comandaItem.itemId = lastItemId + 1;
        comandaItem.comandaId = mComandaId;
        comandaItem.mCant = cant;
        comandaItem.mPrice = price;
        comandaItem.mTotal = comandaItem.mPrice * comandaItem.mCant;

        ComandaProductItem comandaProductItem = new ComandaProductItem();
        comandaProductItem.productItemId = comandaItem.itemId;
        comandaProductItem.productId = productId;
        comandaProductItem.providerId = providerId;
        comandaProductItem.productName = itemFullName.productName;
        comandaProductItem.providerName = itemFullName.providerName;
        comandaProductItem.typeName = itemFullName.productTypeName;
        comandaProductItem.packaging = new Packaging();
        comandaProductItem.packaging.value = packagePrice;
        comandaProductItem.packaging.isFree = comandaProductItem.packaging.value > 0 ? false : true;

        comandaItem.mProductItem = comandaProductItem;

        comanda.comandaItemList = new RealmList<>();

        if (mComandaItemList != null && !mComandaItemList.isEmpty()) {
            comanda.comandaItemList.addAll(mComandaItemList);
        }

        comanda.comandaItemList.add(comandaItem);

        for (ComandaItem item : comanda.comandaItemList) {
            total = total + item.mTotal;
            bultos = bultos + item.mCant;
            if (!item.mProductItem.packaging.isFree) {
                totalSenia = totalSenia + (item.mProductItem.packaging.value * item.mCant);
            }
        }


        comanda.cantBultos = bultos;
        comanda.mSenia = totalSenia;
        comanda.mTotal = total + totalSenia;
        comanda.timestamp = Utils.getTimeStamp();

        mComandaInteractorCallbacks.storeComanda(this, comanda);
    }

    @Override
    public void storeComanda(Comanda cmd) {

        Comanda comanda = new Comanda();
        comanda.comandaId = cmd.comandaId;
        comanda.isPrinted = true;
        comanda.comandaItemList = new RealmList<>();

        if (cmd.comandaItemList != null && !cmd.comandaItemList.isEmpty()) {
            comanda.comandaItemList.addAll(cmd.comandaItemList);
        }

        comanda.cantBultos = cmd.cantBultos;
        comanda.mSenia = cmd.mSenia;
        comanda.mTotal = cmd.mTotal;
        comanda.timestamp = cmd.timestamp;

        mComandaInteractorCallbacks.storeComanda(this, comanda);

    }

    @Override
    public void updateVacio(Comanda cmd, int itemId) {

        mComandaInteractorCallbacks.updateVacio(this, cmd.comandaId, itemId);

    }


    @Override
    public void deleteComanda(int comandaId) {

        mComandaInteractorCallbacks.deleteComanda(this, comandaId);
    }

    @Override
    public void fetchComandaById(int id) {

        mComandaInteractorCallbacks.fetchComandaById(this, id);

    }

    @Override
    public void fetchItemsComanda(int id) {

        mComandaInteractorCallbacks.fetchComandaItems(this, id);
    }

    @Override
    public void fetchLastComanda() {

        mComandaInteractorCallbacks.getLastComandaId(this);
    }

    @Override
    public void fetchTotales(Comanda comanda) {

        double total = 0;
        double totalSenia = 0;
        double bultos = 0;
        for (ComandaItem item : comanda.comandaItemList) {
            total = total + item.mTotal;
            bultos = bultos + item.mCant;
            if (!item.mProductItem.packaging.isFree) {
                totalSenia = totalSenia + (item.mProductItem.packaging.value * item.mCant);
            }
        }
        if (comandaListView != null) {
            getView().onTotalesFetched(total + totalSenia, totalSenia, bultos);
        }

    }

    @Override
    public void deleteItemComanda(int comandaId, int itemId) {

        mComandaInteractorCallbacks.deleteItemComanda(this, comandaId, itemId);
    }


    @Override
    public void onFetchComandaSuccess(Comanda comanda) {

        if (comandaListView != null) {
            getView().showComanda(comanda);
        }
    }

    @Override
    public void onFetchComandaItems(RealmList<ComandaItem> items) {

        if (comandaListView != null) {
            getView().showItemsComanda(items);
        }

    }


    private ComandaListView getView() {
        return (comandaListView != null) ? comandaListView.get() : null;
    }


    @Override
    public void onFetchComandaFail() {

        if (comandaListView != null) {
            getView().onStoreItemFail();
        }

    }

    @Override
    public void onFetchItemsFail() {
        if (comandaListView != null) {
            getView().onFetchItemFail();
        }
    }

    @Override
    public void onFetchDataFailed(String error) {

    }

    @Override
    public void onFetchLastComandaId(int id) {

        if (comandaListView != null) {
            getView().showLastComandaId(id);
        }

    }

    @Override
    public void onStoreCompleted(boolean isSuccess) {
        if (comandaListView != null) {
            getView().onStoreItemSuccess(isSuccess);
        }
    }

    @Override
    public void onDeleteItemCompleted(boolean isSuccess) {
        if (comandaListView != null) {
            getView().onDeleteItemSuccess(isSuccess);
        }
    }

    @Override
    public void onDeleteComandaCompleted(boolean isSuccess) {

        if (comandaListView != null) {
            getView().onDeleteComandaSuccess(isSuccess);
        }

    }

    @Override
    public void detachView() {

        if (comandaListView != null) {
            comandaListView.clear();
            comandaListView = null;
        }
        mComandaInteractorCallbacks.detachView();

    }

    @Override
    public void attachView() {
        mComandaInteractorCallbacks.attachView();
    }

}
