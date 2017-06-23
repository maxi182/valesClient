package com.mcba.comandaclient.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mcba.comandaclient.R;
import com.mcba.comandaclient.model.Provider;

import java.util.Collections;
import java.util.List;

/**
 * Created by mac on 22/06/2017.
 */

public class ProviderSelectionAdapter extends RecyclerView.Adapter<ProviderSelectionAdapter.ProviderListAdapterHolder> {

    private Context mContext;
    private List<Provider> mList;
    private AdapterCallbacks mCallbacks;

    public ProviderSelectionAdapter(Context context, AdapterCallbacks callbacks) {
        this.mContext = context;
        this.mCallbacks = callbacks;
        this.mList = Collections.emptyList();

    }

    public interface AdapterCallbacks {
        void onItemPress(Provider provider);

    }

    public void setItems(List<Provider> providers) {
        this.mList = providers;
    }


    @Override
    public ProviderSelectionAdapter.ProviderListAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new ProviderSelectionAdapter.ProviderListAdapterHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProviderSelectionAdapter.ProviderListAdapterHolder holder, final int position) {
        final Provider provider = mList.get(position);

        holder.text_name.setText(provider.name);
        holder.item_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mCallbacks.onItemPress(mList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public static class ProviderListAdapterHolder extends RecyclerView.ViewHolder {
        public TextView text_name;
        public RelativeLayout item_container;

        public ProviderListAdapterHolder(View itemView) {
            super(itemView);
            item_container = (RelativeLayout) itemView.findViewById(R.id.item_container);
            text_name = (TextView) itemView.findViewById(R.id.txt_name);

        }
    }


}
