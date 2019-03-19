package org.wit.product.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import org.wit.product.R
import org.wit.product.main.MainApp
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import kotlinx.android.synthetic.main.activity_product_list.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult
import org.wit.product.models.ProductModel

class ProductListActivity : AppCompatActivity(), ProductAdapter.ProductListener {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        //recyclerView.adapter = ProductAdapter(app.products.findAll(),this)
        loadProducts()

        toolbarMain.title = title
        setSupportActionBar(toolbarMain)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_add -> startActivityForResult<ProductActivity>(0)//item add moves to product activity
            R.id.item_home -> startActivityForResult<HomeActivity>(0)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onProductClick(product: ProductModel){
        startActivityForResult(intentFor<ProductActivity>().putExtra("product_edit",product),0)
    }
    //we are passing the selected product to the activity and this is enabled by the parcelable mechanism we just turned on

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loadProducts()
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun loadProducts(){
        showProducts(app.products.findAll())
    }

    fun showProducts(products:List<ProductModel>){
        recyclerView.adapter = ProductAdapter(products,this)
        recyclerView.adapter?.notifyDataSetChanged()
    }
}

