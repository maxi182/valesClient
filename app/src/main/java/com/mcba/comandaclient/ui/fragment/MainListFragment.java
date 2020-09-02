package com.mcba.comandaclient.ui.fragment;


import android.app.Dialog;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.epson.epos2.printer.PrinterStatusInfo;
import com.mcba.comandaclient.R;
import com.mcba.comandaclient.helper.IPrintCallbacks;
import com.mcba.comandaclient.helper.PrintComandaHelper;
import com.mcba.comandaclient.model.Client;
import com.mcba.comandaclient.model.Comanda;
import com.mcba.comandaclient.model.ComandaItem;
import com.mcba.comandaclient.model.ItemFullName;
import com.mcba.comandaclient.model.Product;
import com.mcba.comandaclient.presenter.ComandaListPresenter;
import com.mcba.comandaclient.presenter.ComandaListPresenterImpl;
import com.mcba.comandaclient.ui.ComandaListView;
import com.mcba.comandaclient.ui.adapter.MainListAdapter;
import com.mcba.comandaclient.ui.fragment.dialog.IDialogCallbacks;
import com.mcba.comandaclient.ui.fragment.dialog.ItemOptionDialogFragment;
import com.mcba.comandaclient.ui.fragment.dialog.PrintDialogFragment;
import com.mcba.comandaclient.utils.Constants;
import com.mcba.comandaclient.utils.StorageProvider;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

/**
 * Created by mac on 25/05/2017.
 */

public class MainListFragment extends BaseNavigationFragment<MainListFragment.MainListFragmentCallbacks> implements ComandaListView, MainListAdapter.AdapterCallbacks, View.OnClickListener, IPrintCallbacks, IDialogCallbacks {

    public static final String COMANDA_ID = "comandaId";
    public static final String PRUDUCT_ID = "productId";
    public static final String ISNEWCOMANDA = "isnewComanda";
    public static final String RESTORECOMANDA = "storeComanda";
    public static final String PROVIDER_ID = "providerId";
    public static final String PACKAGE_PRICE = "packagePrice";
    public static final String TYPE_ID = "typeId";
    public static final String LASTITEM_ID = "lastItemId";
    public static final String ITEM_FULL_NAME = "item_full_name";
    public static final String PRICE = "price";
    public static final String CANT = "cant";

    private RecyclerView mRecyclerview;
    private MainListAdapter mAdapter;
    private ComandaListPresenter mPresenter;
    private RealmList<Product> mProducts;
    private TextView mBtnAddItem;
    private TextView mTxtComandaId;
    private TextView mBtnFinish;
    private TextView mTxtTotalComanda;
    private TextView mTxtSenia;
    private TextView mCantBultos;
    private ItemFullName mItemFullName;
    private int mComandaId;
    private int mClientId;
    private String mClientName;


    private PrintDialogFragment dialog = new PrintDialogFragment();

    private List<ComandaItem> mComandaItemList = new ArrayList<>();

    private Comanda mComanda;

    public static MainListFragment newInstance(int nextComandaId, boolean restore) {

        Bundle args = new Bundle();
        args.putInt(COMANDA_ID, nextComandaId);
        args.putBoolean(ISNEWCOMANDA, true);
        args.putBoolean(RESTORECOMANDA, restore);
        MainListFragment fragment = new MainListFragment();
        fragment.setArguments(args);
        return fragment;

    }

    public static MainListFragment newInstance(int productId, int providerId, int typeId, double price, int cant, int currentComandaId, int lastItemId, double packagePrice, ItemFullName itemFullName) {

        Bundle args = new Bundle();
        args.putInt(PRUDUCT_ID, productId);
        args.putInt(PROVIDER_ID, providerId);
        args.putInt(COMANDA_ID, currentComandaId);
        args.putInt(TYPE_ID, typeId);
        args.putDouble(PRICE, price);
        args.putDouble(PACKAGE_PRICE, packagePrice);
        args.putInt(CANT, cant);
        args.putInt(LASTITEM_ID, lastItemId);
        args.putBoolean(ISNEWCOMANDA, false);
        args.putParcelable(ITEM_FULL_NAME, itemFullName);


        MainListFragment fragment = new MainListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.main_list_fragment;
    }

    @Override
    protected void setViewReferences() {

        mRecyclerview = (RecyclerView) findViewById(R.id.recycler_selection);
        mBtnAddItem = (TextView) findViewById(R.id.btn_add_item);
        mTxtComandaId = (TextView) findViewById(R.id.txtcomanda_id);
        mTxtTotalComanda = (TextView) findViewById(R.id.txt_total_comanda);
        mTxtSenia = (TextView) findViewById(R.id.txt_senia);
        mCantBultos = (TextView) findViewById(R.id.txt_cantidad_bultos);
        mBtnFinish = (TextView) findViewById(R.id.btn_finish);

    }

    @Override
    protected void setupFragment(Bundle savedInstanceState) {

        mAdapter = new MainListAdapter(getActivity(), this, false);
        mRecyclerview.setAdapter(mAdapter);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        mPresenter = new ComandaListPresenterImpl(this);
        mPresenter.attachView();

        mBtnAddItem.setOnClickListener(this);
        mBtnFinish.setOnClickListener(this);


        mComandaId = getArguments().getInt(COMANDA_ID);

        mItemFullName = getArguments().getParcelable(ITEM_FULL_NAME);

        StorageProvider.savePreferences(Constants.PRINTSUCCESS, false);

        if (validateRestoreComanda()) { // Se mete aca cuando vuelve de background

            if (!validateIsNewComanda() || isRestoreMain()) { // Si no es nueva comanda.
                mPresenter.fetchItemsComanda(mComandaId);
                mTxtComandaId.setText(String.valueOf(String.format("%05d", mComandaId)));
            } else {
                setInitValues();
                mTxtComandaId.setText(String.valueOf(String.format("%05d", mComandaId)));
            }
            return;
        }

        if (!validateIsNewComanda()) {
            mPresenter.fetchItemsComanda(mComandaId);
        } else {

            mComandaId = mComandaId + 1;
            StorageProvider.savePreferences(Constants.LAST_COMANDA_ID, mComandaId);

            setInitValues();
            mTxtComandaId.setText(String.valueOf(String.format("%05d", mComandaId)));
        }
        //consultar comandas

    }

    private void setInitValues() {

        mTxtTotalComanda.setText("-");
        mTxtSenia.setText("-");
        mCantBultos.setText("-");
        mClientName = "";
        mClientId = -1;
        getBaseActivity().setClientName("");

    }

    @Override
    public void onFetchItemFail() {
        storeComanda();
    }

    private boolean validateIsNewComanda() {

        return getArguments().getBoolean(ISNEWCOMANDA);

    }

    private boolean validateRestoreComanda() {

        return getArguments().getBoolean(RESTORECOMANDA);

    }

    private boolean isRestoreMain() {

        return StorageProvider.getPreferencesBoolean(Constants.RESTOREMAIN);
    }

    @Override
    public void showComanda(Comanda comanda) {

        if (comanda != null) {
            StorageProvider.savePreferences(Constants.LAST_COMANDA_ID, comanda.comandaId);

            StorageProvider.savePreferences(Constants.RESTOREMAIN, true);
            mTxtComandaId.setText(String.valueOf(String.format("%05d", comanda.comandaId)));
            mComanda = comanda;


            if (!(comanda.comandaItemList.size() > 0)) {
                StorageProvider.savePreferences(Constants.RESTOREMAIN, false);
            }

            mPresenter.fetchTotales(comanda);

            mAdapter.setItems(comanda.comandaItemList);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showItemsComanda(RealmList<ComandaItem> items) {

        mComandaItemList.addAll(items);
        if (!validateRestoreComanda()) {
            storeComanda();
        } else {
            mPresenter.fetchComandaById(mComandaId);
        }

    }

    @Override
    public void onFetchComandaItemsForPrint(StringBuilder stringBuilderItems, StringBuilder stringBuilderSubTotales, StringBuilder stringBuilderTotal, StringBuilder stringBuilderCopyItems, StringBuilder nota, String clientname) {


        PrintComandaHelper printComandaHelper = new PrintComandaHelper(getActivity(), this, stringBuilderItems, stringBuilderSubTotales, stringBuilderTotal, stringBuilderCopyItems, mComanda.comandaId, nota, clientname, this);
        printComandaHelper.print();

    }

    @Override
    public void showLastComandaId(int id) {
    }

    @Override
    public void onTotalesFetched(double total, double senia, double bultos) {

        mTxtTotalComanda.setText(String.valueOf(total));
        mTxtSenia.setText(String.valueOf(senia));
        mCantBultos.setText(String.valueOf(bultos));
    }

    @Override
    public void onStoreItemSuccess(boolean isSuccess) {

        if (isSuccess) {
            mPresenter.fetchComandaById(mComandaId);
        } else {
            Log.e("StoreItem", "can not save item");
        }
    }

    @Override
    public void onDeleteItemSuccess(boolean isSuccess) {

        if (isSuccess) {
            mPresenter.fetchComandaById(mComandaId);
        }

    }

    @Override
    public void onDeleteComandaSuccess(boolean isSuccess) {

    }

    @Override
    public void onStoreItemFail() {

    }

    private void printComanda() {

        mPresenter.prepareComandaForPrint(mComanda);

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Client client) {
        mClientId = client.clientId;
        mClientName = client.mName;
        StorageProvider.savePreferences(Constants.CLIENT_ID, mClientId);
        StorageProvider.savePreferences(Constants.CLIENT_NAME, mClientName);
        storeComanda();
    }

    private void storeComanda() {

        //todo change client id
//        mPresenter.storeComanda(mComandaId, getArguments().getInt(LASTITEM_ID), 1, getArguments().getInt(CANT),
//                getArguments().getDouble(PRICE), getArguments().getInt(PRUDUCT_ID), getArguments().getInt(PROVIDER_ID), mItemFullName,
//                getArguments().getDouble(PACKAGE_PRICE), mComandaItemList, getBaseActivity().getClientName(), false);


        mClientName = StorageProvider.getPreferencesString(Constants.CLIENT_NAME);
        mClientId = StorageProvider.getPreferencesInt(Constants.CLIENT_ID);

        mPresenter.storeComanda(mComandaId, mClientId, mClientName, getArguments().getInt(LASTITEM_ID), getArguments().getInt(CANT),
                getArguments().getDouble(PRICE), getArguments().getInt(PRUDUCT_ID), getArguments().getInt(PROVIDER_ID), mItemFullName,
                getArguments().getDouble(PACKAGE_PRICE), mComandaItemList, false);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_add_item:
                mCallbacks.onGoToSelectProduct(mComandaId);
                break;
            case R.id.btn_finish:
                if (!validateIsNewComanda() || isRestoreMain()) {

                    if (mAdapter.getItemCount() > 0) {
                        mClientId = StorageProvider.getPreferencesInt(Constants.CLIENT_ID);
                        //todo change this
                        if (mClientId != -1) {
                            // printSuccess();
                            printComanda();
                        } else {
                            Toast.makeText(getActivity(), "Ingreso nombre Cliente", Toast.LENGTH_SHORT).show();
                        }//  printComanda();
                    }
                }
                break;
            default:
        }
    }


    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void onItemPress(ComandaItem comandaItem) {

        ItemOptionDialogFragment itemOptionDialogFragment = new ItemOptionDialogFragment();
        itemOptionDialogFragment.initDialog(this, comandaItem.itemId, comandaItem.mProductItem.packaging.isFree);

        itemOptionDialogFragment.show(getActivity().getFragmentManager(), "");

    }

    @Override
    public void displayPrinterWarning(PrinterStatusInfo status) {

    }

    @Override
    public void showProgress() {
        dialog.initDialog(this, false, null);
        dialog.show(getActivity().getFragmentManager(), "");
    }

    @Override
    public void hideProgress() {
        dialog.dismiss();
    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public void printSuccess() {

        StorageProvider.savePreferences(Constants.PRINT_SUCCESS, true);
        StorageProvider.savePreferences(Constants.CLIENT_ID, -1);
        StorageProvider.savePreferences(Constants.CLIENT_NAME, "");
        mPresenter.storeComanda(mComanda);

    }

    @Override
    public void onOkPress(Dialog dialog, boolean isSuccess) {
        dialog.dismiss();
        if (isSuccess) {
            mCallbacks.onGoToEntryFragment();
        } else {
            return;
        }
    }


    @Override
    public void onDeletePress(Dialog dialog, int itemId) {
        dialog.dismiss();
        mPresenter.deleteItemComanda(mComandaId, itemId);
    }

    @Override
    public void onDeleteVacioPress(Dialog dialog, int itemId) {
        dialog.dismiss();
        mPresenter.updateVacio(mComanda, itemId);
    }


    @Override
    public MainListFragmentCallbacks getDummyCallbacks() {
        return new MainListFragmentCallbacks() {
            @Override
            public void onGoToSelectProduct(int currentComandaId) {

            }

            @Override
            public void onGoToEntryFragment() {

            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    public interface MainListFragmentCallbacks {
        void onGoToSelectProduct(int currentComandaId);

        void onGoToEntryFragment();
    }
}
