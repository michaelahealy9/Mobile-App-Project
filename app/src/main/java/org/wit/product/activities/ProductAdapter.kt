package org.wit.product.activities

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.card_product.view.*
import org.wit.product.R
import org.wit.product.models.ProductModel
import org.wit.product.helpers.readImageFromPath



// adapter binds all info from my product model to the recycler view

class ProductAdapter (private var products: List<ProductModel>,
                      private val listener: ProductListener
) : RecyclerView.Adapter<ProductAdapter.MainHolder>() {

    interface ProductListener {
        fun onProductClick(product: ProductModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(LayoutInflater.from(parent?.context).inflate(R.layout.card_product, parent, false))
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val product = products[holder.adapterPosition]
        holder.bind(product, listener)

    }

    fun filterList(filteredProductList: ArrayList<ProductModel>){
        this.products= filteredProductList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = products.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var args = Bundle()
        fun bind(product: ProductModel,  listener : ProductListener) {
            itemView.productTitle.text = product.title
            itemView.description.text = product.description
            itemView.imageIcon.setImageBitmap(
                readImageFromPath(
                    itemView.context,
                    product.image
                )
            )


            itemView.setOnClickListener { listener.onProductClick(product)
                getPosition = adapterPosition}
        }

        companion object {
            var getPosition: Int = 0
        }
    }
}

//https://medium.com/@ansujain/kotlin-how-to-create-static-members-for-class-543d0f126f7c
//https://android.jlelse.eu/filterable-recyclerview-in-android-the-how-to-a9ade9cd26