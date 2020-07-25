package com.mcba.comandaclient.ui.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import com.mcba.comandaclient.R
import kotlinx.android.synthetic.main.item_list.view.*
import kotlinx.android.synthetic.main.item_add_header.view.*
import java.util.*

/**
 * Created by mac on 10/05/2018.
 */
class AddProductAdapter(context: Context, callback: adapterCallbacks) :
        androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {


    var partItemList: List<ListModel> = ArrayList()
    var mContext: Context = context
    var mAdapterCallbacks: adapterCallbacks = callback


    companion object {
        const val TYPE_PROVIDER = 0
        const val TYPE_HEADER = 1
        const val TYPE_PRODUCT = 2
        const val TYPE_PRODUCTTYPE = 3
    }

    interface adapterCallbacks {

        fun onItemSelected(listModel: ListModel)
    }

    interface ListMoldelViewHolder {
        fun bindViews(listModel: ListModel, context: Context, adapterCallbacks: adapterCallbacks)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        // Inflate XML. Last parameter: don't immediately attach new view to the parent view group
        val viewItem = inflater.inflate(R.layout.item_list, parent, false)
        val viewHeader = inflater.inflate(R.layout.item_add_header, parent, false)
        val viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder = when (viewType) {
            TYPE_PROVIDER -> ItemViewHolder(viewItem)
            TYPE_PRODUCT -> ProductViewHolder(viewItem)
            TYPE_PRODUCTTYPE -> ProductTypeViewHolder(viewItem)

        // other view holders...
            else -> HeaderViewHolder(viewHeader)
        }
        return viewHolder
    }

    fun setItems(listItems: List<ListModel>) {
        partItemList = listItems
        notifyDataSetChanged()

    }

    override fun getItemViewType(position: Int): Int {
        val type = when (partItemList[position].updateType) {
            ListModel.TYPE.PRODUCT -> TYPE_PRODUCT
            ListModel.TYPE.PROVIDER -> TYPE_PROVIDER
            ListModel.TYPE.PRODUCT_TYPE -> TYPE_PRODUCTTYPE

            else -> TYPE_HEADER
        }
        return type
    }

    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        (holder as ListMoldelViewHolder).bindViews(partItemList[position], mContext, mAdapterCallbacks)


    }

    override fun getItemCount(): Int = partItemList?.size


    class ItemViewHolder(itemView: View)
        : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView), ListMoldelViewHolder {

        override fun bindViews(update: ListModel, context: Context, adapterCallbacks: adapterCallbacks) {
            val data = update as ProviderListModel
            itemView?.txt_name?.text = data?.provider?.name

            itemView?.setOnClickListener({
                adapterCallbacks.onItemSelected(data)

            })
        }
    }

    class ProductViewHolder(itemView: View)
        : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView), ListMoldelViewHolder {

        // get the views reference from itemView...

        override fun bindViews(update: ListModel, context: Context, adapterCallbacks: adapterCallbacks) {
            val data = update as ProductListModel
            itemView?.txt_name?.text = data?.product?.name
            // bind update values to views
        }
    }

    class HeaderViewHolder(itemView: View)
        : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView), ListMoldelViewHolder {

        // get the views reference from itemView...

        override fun bindViews(update: ListModel, context: Context, adapterCallbacks: adapterCallbacks) {
            val data = update as HeaderListModel
            itemView?.txt_title?.text = data?.title


            itemView?.new_txt_btn?.setOnClickListener({
                adapterCallbacks.onItemSelected(data)

            })
            // bind update values to views
        }
    }

    class ProductTypeViewHolder(itemView: View)
        : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView), ListMoldelViewHolder {

        // get the views reference from itemView...

        override fun bindViews(update: ListModel, context: Context, adapterCallbacks: adapterCallbacks) {
            val data = update as HeaderListModel
            itemView?.txt_title?.text = data?.title

            itemView?.new_txt_btn?.setOnClickListener({
                adapterCallbacks.onItemSelected(data)

            })
            // bind update values to views
        }
    }

}