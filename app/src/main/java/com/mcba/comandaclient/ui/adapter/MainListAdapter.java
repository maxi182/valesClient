package com.mcba.comandaclient.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mcba.comandaclient.R;
import com.mcba.comandaclient.model.Product;

import java.util.Collections;
import java.util.List;

import io.realm.RealmList;

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
        void onItemPress(int pos);

    }


    public void setItems(List<Product> characters) {
        this.mList = characters;
    }


    @Override
    public MainListAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new MainListAdapterHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MainListAdapterHolder holder, int position) {
        final Product product = mList.get(position);

        holder.text_name.setText(product.name);

    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public static class MainListAdapterHolder extends RecyclerView.ViewHolder {
        public TextView text_name;

        public MainListAdapterHolder(View itemView) {
            super(itemView);

            text_name = (TextView) itemView.findViewById(R.id.txt_name);

        }
    }


}
