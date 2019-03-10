package org.wit.product.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.product.models.ProductMemStore

class MainApp : Application(), AnkoLogger {
    //val products = ArrayList<ProductModel>()
    val products = ProductMemStore()


    override fun onCreate() {
        super.onCreate()
        info("Product started")
       // products.add(ProductModel("Product One", "About product one..."))
        //products.add(ProductModel("Product Two", "About product two..."))
        //products.add(ProductModel("Product Three", "About product three..."))
    }
}