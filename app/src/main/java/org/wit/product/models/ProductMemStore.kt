package org.wit.product.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var lastId = 0L

internal fun  getId(): Long{
    return lastId++
}


class ProductMemStore:ProductStore, AnkoLogger {

    val products = ArrayList<ProductModel>()

    override fun findAll(): List<ProductModel> {
        return products
    }

    override fun create(product: ProductModel) {
        product.id=getId()
        products.add(product)
        logAll()
    }

    override fun update(product: ProductModel) {
        var foundProduct: ProductModel? = products.find { p -> p.id == product.id }
        if (foundProduct != null) {
            foundProduct.title = product.title
            foundProduct.description = product.description
            foundProduct.image = product.image
            logAll()
        }
    }

    fun logAll() {
        products.forEach {info("${it}") }
    }
}