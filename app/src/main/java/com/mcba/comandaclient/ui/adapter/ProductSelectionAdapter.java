package com.mcba.comandaclient.ui.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
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

public class ProductSelectionAdapter extends RecyclerView.Adapter<ProductSelectionAdapter.MainListAdapterHolder> {

    private Context mContext;
    private List<Product> mList;
    private AdapterCallbacks mCallbacks;

    public ProductSelectionAdapter(Context context, AdapterCallbacks callbacks) {
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
                .inflate(R.layout.item_list, parent, false);
        return new MainListAdapterHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MainListAdapterHolder holder, final int position) {
        final Product product = mList.get(position);

        holder.text_name.setText(product.name);
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

    public static class MainListAdapterHolder extends RecyclerView.ViewHolder {
        public TextView text_name;
        public RelativeLayout item_container;

        public MainListAdapterHolder(View itemView) {
            super(itemView);
            item_container = (RelativeLayout) itemView.findViewById(R.id.item_container);
            text_name = (TextView) itemView.findViewById(R.id.txt_name);

        }
    }


}
