package com.mcba.comandaclient.ui.adapter

import com.mcba.comandaclient.model.NameId
import com.mcba.comandaclient.model.Product
import com.mcba.comandaclient.model.ProductType
import com.mcba.comandaclient.model.Provider

/**
 * Created by mac on 10/05/2018.
 */
abstract class ListModel(
        val updateType: String,
        val id: Int) {

    class TYPE {
        companion object {
            val HEADER = "header"
            val PROVIDER = "provider"
            val PRODUCT = "product"
            val PRODUCT_TYPE ="product_type"
        }
    }
}

data class ProviderListModel(
        val provider: Provider

) : ListModel(ListModel.TYPE.PROVIDER, provider.providerId)

data class HeaderListModel(
        val title: String,
        val type: String
) : ListModel(ListModel.TYPE.HEADER, -1)

data class ProductListModel(
        val product: Product
) : ListModel(ListModel.TYPE.PRODUCT, product.productId)

data class ProductTypeListModel(
        val productType: ProductType
) : ListModel(ListModel.TYPE.PRODUCT_TYPE, productType.productTypeId)
