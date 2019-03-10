package org.wit.product.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class ProductMemStore:ProductStore, AnkoLogger {

    val products = ArrayList<ProductModel>()

    override fun findAll(): List<ProductModel> {
        return products
    }

    override fun create(product: ProductModel) {
        products.add(product)
        logAll()
    }

    fun logAll() {
        products.forEach {info("${it}") }
    }
}