package org.wit.product.activities

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.card_product.view.*
import org.wit.product.R
import org.wit.product.models.ProductModel
import org.wit.product.helpers.readImageFromPath

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

    override fun getItemCount(): Int = products.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var deleteItem = itemView.findViewById<Button>(R.id.deleteBtn)
        fun bind(product: ProductModel,  listener : ProductListener) {
            itemView.productTitle.text = product.title
            itemView.description.text = product.description
            deleteItem.setOnClickListener(){

            }
            itemView.imageIcon.setImageBitmap(
                readImageFromPath(
                    itemView.context,
                    product.image
                )
            )
            itemView.setOnClickListener { listener.onProductClick(product) }
        }
    }
}