package com.mcba.comandaclient.ui.adapter

import com.mcba.comandaclient.model.Provider

/**
 * Created by mac on 10/05/2018.
 */
abstract class ListModel(
        val updateType: String) {

    class TYPE {
        companion object {
            val HEADER = "header"
            val ITEM = "item"
        }
    }
}

data class ProviderListModel(
        val provider: Provider

) : ListModel(ListModel.TYPE.ITEM)

data class HeaderListModel(
        val title: String
) : ListModel(ListModel.TYPE.HEADER)