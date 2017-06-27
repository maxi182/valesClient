package com.mcba.comandaclient.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mcba.comandaclient.R;
import com.mcba.comandaclient.model.Product;

import java.util.Collections;
import java.util.List;

/**
 * Created by mac on 01/06/2017.
 */

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.MainListAdapterHolder> {

    private Context mContext;
    private List<Product> mList;
    private AdapterCallbacks mCallbacks;


    public MainListAdapter(Context context, AdapterCallbacks callbacks) {
        this.mContext = context;
        this.mCallbacks = callbacks;
        this.mList = Collections.emptyList();

    }

    public interface AdapterCallbacks {
        void onItemPress(Product product);

    }

    public void setItems(List<Product> products) {
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
        final Product product = mList.get(position);

        holder.text_qty.setText("2");
        holder.text_detail.setText("Manzana Red delicius caja Negra");
        holder.text_price.setText("250");
        holder.text_total.setText("500");
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
