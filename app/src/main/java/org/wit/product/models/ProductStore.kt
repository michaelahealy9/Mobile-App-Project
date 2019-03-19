package org.wit.product.models

interface ProductStore{
    fun findAll(): List<ProductModel>
    fun create(product:ProductModel)
    fun update(product: ProductModel)
    fun delete(product:ProductModel)
}