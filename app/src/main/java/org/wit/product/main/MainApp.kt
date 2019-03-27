package org.wit.product.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.product.models.ProductJSONStore
import org.wit.product.models.ProductStore

class MainApp : Application(), AnkoLogger {

    lateinit var products: ProductStore

    override fun onCreate() {
        super.onCreate()
        products = ProductJSONStore(applicationContext)
        info("Product started")
        //products.add(ProductModel("Product One", "About product one..."))
        //products.add(ProductModel("Product Two", "About product two..."))
        //products.add(ProductModel("Product Three", "About product three..."))
    }
}