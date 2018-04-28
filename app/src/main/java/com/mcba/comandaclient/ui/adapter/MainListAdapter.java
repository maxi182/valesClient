package com.mcba.comandaclient.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
    private boolean isDetail;


    public MainListAdapter(Context context, AdapterCallbacks callbacks, boolean isDetail) {
        this.mContext = context;
        this.mCallbacks = callbacks;
        this.isDetail = isDetail;
        this.mList = Collections.emptyList();

    }

    public interface AdapterCallbacks {
        void onItemPress(ComandaItem comandaItem);

    }

    public void setItems(List<ComandaItem> products) {
        this.mList = products;
    }


    @Override
    public MainListAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(isDetail ? R.layout.item_detail : R.layout.item_item, parent, false);
        return new MainListAdapterHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MainListAdapterHolder holder, final int position) {
        final ComandaItem comandaItem = mList.get(position);


        holder.text_qty.setText(String.valueOf(Utils.setDecimalFormat(comandaItem.mCant)));
        holder.text_detail.setText(comandaItem.mProductItem.productName + " " + comandaItem.mProductItem.providerName + " " + comandaItem.mProductItem.typeName);
        holder.text_price.setText(String.valueOf(comandaItem.mPrice));
        holder.text_total.setText(String.valueOf(comandaItem.mTotal));

        if (!comandaItem.mProductItem.packaging.isFree) {
            holder.linear_vacios.setVisibility(View.GONE);
            holder.text_price.setText(String.valueOf(comandaItem.mProductItem.packaging.value));
            holder.text_senia_qty.setText(String.valueOf(Utils.setDecimalFormat(comandaItem.mCant)));
            holder.text_total.setText(String.valueOf(comandaItem.mCant * comandaItem.mProductItem.packaging.value));
        } else {
            holder.linear_vacios.setVisibility(View.GONE);

        }
        holder.include_header.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                mCallbacks.onItemPress(comandaItem);

                return true;
            }
        });

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
        public TextView text_senia_qty;
        public TextView text_senia_price;
        public TextView text_senia_total;
        public LinearLayout linear_vacios;
        public LinearLayout include_header;


        public MainListAdapterHolder(View itemView) {
            super(itemView);
            text_qty = (TextView) itemView.findViewById(R.id.txt_qty);
            text_price = (TextView) itemView.findViewById(R.id.txt_price);
            text_detail = (TextView) itemView.findViewById(R.id.txt_detail);
            text_total = (TextView) itemView.findViewById(R.id.txt_total);
            text_senia_qty = (TextView) itemView.findViewById(R.id.txt_senia_qty);
            text_senia_price = (TextView) itemView.findViewById(R.id.txt_senia_price);
            text_senia_total = (TextView) itemView.findViewById(R.id.txt_senia_total);
            linear_vacios = (LinearLayout) itemView.findViewById(R.id.linear_vacios);
            include_header = (LinearLayout) itemView.findViewById(R.id.include_header);
        }
    }

}
