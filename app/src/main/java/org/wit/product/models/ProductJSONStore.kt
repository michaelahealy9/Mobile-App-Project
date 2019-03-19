package org.wit.product.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.wit.product.helpers.*
import java.util.*

val JSON_FILE = "products.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<java.util.ArrayList<ProductModel>>() {}.type


fun generateRandomId():Long{
    return Random().nextLong()
}

class ProductJSONStore:ProductStore,AnkoLogger{
    val context : Context
    var products = mutableListOf<ProductModel>()

    constructor(context:Context){
        this.context = context
        if(exists(context,JSON_FILE)){
            deserialize()
        }
    }

    override fun findAll(): MutableList<ProductModel> {
        return products
    }

    override fun create(product: ProductModel) {
        product.id = generateRandomId()
        products.add(product)
        serialize()
    }


    override fun update(product: ProductModel) {
        val productsList=findAll() as ArrayList<ProductModel>
        var foundProduct: ProductModel?=productsList.find { p->p.id==product.id}
        if (foundProduct !=null){
            foundProduct.title = product.title
            foundProduct.description = product.description
            foundProduct.image = product.image
        }
        serialize()
    }

    override fun delete(product:ProductModel){
        products.remove(product)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(products, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        products = Gson().fromJson(jsonString, listType)
    }

}

