package org.wit.product.main

import android.app.Application
import com.google.firebase.database.FirebaseDatabase
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.product.models.ProductJSONStore
import org.wit.product.models.ProductStore

class MainApp : Application(), AnkoLogger {

    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("message")



    lateinit var products: ProductStore

    override fun onCreate() {

        myRef.setValue("Testing Firebase!")
        super.onCreate()
        products = ProductJSONStore(applicationContext)
        info("Product started")
        //products.add(ProductModel("Product One", "About product one..."))
        //products.add(ProductModel("Product Two", "About product two..."))
        //products.add(ProductModel("Product Three", "About product three..."))
    }
}