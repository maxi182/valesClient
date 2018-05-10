package com.mcba.comandaclient.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;


import com.mcba.comandaclient.R;
import com.mcba.comandaclient.model.Client;
import com.mcba.comandaclient.model.Provider;

import java.util.Collections;
import java.util.List;

/**
 * Created by mac on 07/05/2018.
 */

public class AddProviderAdapter extends RecyclerView.Adapter<AddProviderAdapter.AddProviderHolder> {

    private Context mContext;
    private List<Provider> mList;
    private AdapterCallbacks mCallbacks;

    public AddProviderAdapter(Context context, AdapterCallbacks callbacks) {
        this.mContext = context;
        this.mCallbacks = callbacks;
        this.mList = Collections.emptyList();

    }

    public interface AdapterCallbacks {
        void onItemPress(Provider provider);
    }

    @Override
    public AddProviderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new AddProviderAdapter.AddProviderHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AddProviderHolder holder, int position) {
        final Provider client = mList.get(position);
        holder.mName.setText(client.name);
     }

    public void setItems(List<Provider> list) {
        this.mList = list;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public static class AddProviderHolder extends RecyclerView.ViewHolder {
        public FrameLayout mContainer;
        public TextView mName;


        public AddProviderHolder(View itemView) {
            super(itemView);

            mContainer = (FrameLayout) itemView.findViewById(R.id.frame_container);
            mName = (TextView) itemView.findViewById(R.id.txt_name);
        }
    }
}

