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
import com.mcba.comandaclient.utils.Constants;
import com.mcba.comandaclient.utils.Utils;

import java.util.Collections;
import java.util.List;

/**
 * Created by mac on 21/08/2017.
 */

public class ComandaSearchAdapter extends RecyclerView.Adapter<ComandaSearchAdapter.ComandaSearchAdapterHolder> {

    private List<Comanda> mList;
    private AdapterCallbacks mCallbacks;
    private Context mContext;

    public ComandaSearchAdapter(Context context, AdapterCallbacks callbacks) {
        this.mContext = context;
        this.mCallbacks = callbacks;
        this.mList = Collections.emptyList();
    }

    public interface AdapterCallbacks {
        void onItemPress(Comanda comandaItem);
    }

    @Override
    public ComandaSearchAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comanda, parent, false);
        return new ComandaSearchAdapter.ComandaSearchAdapterHolder(itemView);

    }

    public void setItems(List<Comanda> comandas) {
        this.mList = comandas;
    }

    @Override
    public void onBindViewHolder(ComandaSearchAdapterHolder holder, int position) {
        final Comanda comanda = mList.get(position);
        holder.text_num_comanda.setText(String.format("%05d", comanda.comandaId));
        holder.text_date.setText(String.valueOf(Utils.getTimeFromTimeStamp(comanda.timestamp, Constants.DATEFORMAT)));
        holder.text_time.setText(String.valueOf(Utils.getTimeFromTimeStamp(comanda.timestamp, Constants.TIMEFORMAT)));
        holder.mContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallbacks.onItemPress(comanda);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }


    public static class ComandaSearchAdapterHolder extends RecyclerView.ViewHolder {
        public RelativeLayout mContainer;
        public TextView text_num_comanda;
        public TextView text_date;
        public TextView text_time;
        public TextView txt_client;

        public ComandaSearchAdapterHolder(View itemView) {
            super(itemView);
            mContainer = (RelativeLayout) itemView.findViewById(R.id.item_container);
            text_num_comanda = (TextView) itemView.findViewById(R.id.txt_num_comanda);
            text_date = (TextView) itemView.findViewById(R.id.txt_date);
            text_time = (TextView) itemView.findViewById(R.id.txt_time);
            txt_client = (TextView) itemView.findViewById(R.id.txt_client);


        }
    }
}
