package com.mcba.comandaclient.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mcba.comandaclient.R;
import com.mcba.comandaclient.model.Client;
import com.mcba.comandaclient.model.Comanda;

import java.util.Collections;
import java.util.List;

/**
 * Created by mac on 15/01/2018.
 */

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ClientAdapterHolder> {

    private Context mContext;
    private List<Client> mList;
    private AdapterCallbacks mCallbacks;

    public ClientAdapter(Context context, AdapterCallbacks callbacks) {
        this.mContext = context;
        this.mCallbacks = callbacks;
        this.mList = Collections.emptyList();

    }

    public interface AdapterCallbacks {
        void onItemPress(Client client);
    }

    @Override
    public ClientAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.client_item, parent, false);
        return new ClientAdapter.ClientAdapterHolder(itemView);

    }

    @Override
    public void onBindViewHolder(ClientAdapterHolder holder, int position) {
        final Client client = mList.get(position);
        holder.mName.setText(client.mName);
        holder.mCode.setText(String.valueOf(client.clientId));


    }

    public void setItems(List<Client> clientList) {
        this.mList = clientList;
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }


    public static class ClientAdapterHolder extends RecyclerView.ViewHolder {
        public FrameLayout mContainer;
        public TextView mName;
        public TextView mCode;

        public ClientAdapterHolder(View itemView) {
            super(itemView);

            mContainer = (FrameLayout) itemView.findViewById(R.id.frame_container);
            mName = (TextView) itemView.findViewById(R.id.name_textview);
            mCode = (TextView) itemView.findViewById(R.id.txt_id);
        }
    }

}
