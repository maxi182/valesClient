package com.mcba.comandaclient.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mcba.comandaclient.R;
import com.mcba.comandaclient.model.Comanda;
import com.mcba.comandaclient.model.ComandaItem;
import com.mcba.comandaclient.model.Product;
import com.mcba.comandaclient.utils.Utils;

import java.util.Collections;
import java.util.List;

/**
 * Created by mac on 01/06/2017.
 */

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.MainListAdapterHolder> {

    private Context mContext;
    private List<ComandaItem> mList;
    private AdapterCallbacks mCallbacks;


    public MainListAdapter(Context context, AdapterCallbacks callbacks) {
        this.mContext = context;
        this.mCallbacks = callbacks;
        this.mList = Collections.emptyList();

    }

    public interface AdapterCallbacks {
        void onItemPress(Product product);

    }

    public void setItems(List<ComandaItem> products) {
        this.mList = products;
    }


    @Override
    public MainListAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_item, parent, false);
        return new MainListAdapterHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MainListAdapterHolder holder, final int position) {
        final ComandaItem comandaItem = mList.get(position);

        holder.text_qty.setText(String.valueOf(Utils.setDecimalFormat(comandaItem.mCant)));
        holder.text_detail.setText(comandaItem.mProductItem.productName + " " + comandaItem.mProductItem.providerName + " " + comandaItem.mProductItem.typeName);
        holder.text_price.setText(String.valueOf(comandaItem.mPrice));
        holder.text_total.setText(String.valueOf(comandaItem.mTotal));

    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public static class MainListAdapterHolder extends RecyclerView.ViewHolder {
        public TextView text_qty;
        public TextView text_price;
        public TextView text_detail;
        public TextView text_total;


        public MainListAdapterHolder(View itemView) {
            super(itemView);
            text_qty = (TextView) itemView.findViewById(R.id.txt_qty);
            text_price = (TextView) itemView.findViewById(R.id.txt_price);
            text_detail = (TextView) itemView.findViewById(R.id.txt_detail);
            text_total = (TextView) itemView.findViewById(R.id.txt_total);

        }
    }

}
