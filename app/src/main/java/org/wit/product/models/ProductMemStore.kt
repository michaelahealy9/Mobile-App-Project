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

    override fun update(product: ProductModel) {
        var foundProduct: ProductModel? = products.find { p -> p.id == product.id }
        if (foundProduct != null) {
            foundProduct.title = product.title
            foundProduct.description = product.description
            foundProduct.image = product.image
            logAll()
        }
    }

    override fun delete(product:ProductModel){
        products.remove(product)
    }

    fun logAll() {
        products.forEach {info("${it}") }
    }
}