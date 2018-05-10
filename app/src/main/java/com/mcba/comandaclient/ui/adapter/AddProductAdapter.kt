package com.mcba.comandaclient.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import com.mcba.comandaclient.R
import com.mcba.comandaclient.model.Provider
import kotlinx.android.synthetic.main.item_list.view.*
import kotlinx.android.synthetic.main.item_add_header.view.*

/**
 * Created by mac on 10/05/2018.
 */
class AddProductAdapter(context : Context) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var partItemList: List<ListModel>

    companion object {
        const val TYPE_ITEM = 0
        const val TYPE_HEADER = 1
    }

    interface ListMoldelViewHolder {
        fun bindViews(listModel: ListModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        // Inflate XML. Last parameter: don't immediately attach new view to the parent view group
        val viewItem = inflater.inflate(R.layout.item_list, parent, false)
        val viewHeader = inflater.inflate(R.layout.item_add_header, parent, false)
        val viewHolder: RecyclerView.ViewHolder = when (viewType) {
            TYPE_ITEM -> ItemViewHolder(viewItem)
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
            ListModel.TYPE.HEADER -> TYPE_HEADER
        // other types...
            else -> TYPE_ITEM
        }
        return type
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as ListMoldelViewHolder).bindViews(partItemList[position])
    }

//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        // Populate ViewHolder with data that corresponds to the position in the list
//        // which we are told to load
//        (holder as PartViewHolder).bind(partItemList[position], clickListener)
//    }

    override fun getItemCount(): Int = partItemList?.size


    class ItemViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView), ListMoldelViewHolder {

        // get the views reference from itemView...

        override fun bindViews(update: ListModel) {
            val data = update as ProviderListModel
             itemView.txt_name.text = data.provider.name
            // bind update values to views
        }
    }

    class HeaderViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView), ListMoldelViewHolder {

        // get the views reference from itemView...

        override fun bindViews(update: ListModel) {
            val data = update as HeaderListModel
             itemView.txt_title.text = data.title
            // bind update values to views
        }
    }

}