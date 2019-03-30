package org.wit.product.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import org.wit.product.R
import org.wit.product.main.MainApp
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_product_list.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import org.wit.product.models.ProductModel

class ProductListActivity : AppCompatActivity(), ProductAdapter.ProductListener {
    override fun onProductClick(product: ProductModel) {
        startActivityForResult(intentFor<ProductActivity>().putExtra("product_edit",product),0)
       //Toast.makeText(applicationContext, "Hello", Toast.LENGTH_LONG).show()

    }

    lateinit var app: MainApp
    var database: FirebaseDatabase? = null
    var productsRef: DatabaseReference? = null
    var productsList: ArrayList<ProductModel>? = null
    var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)
        app = application as MainApp
        database = FirebaseDatabase.getInstance()
        productsRef = database!!.getReference("products")
        productsList = ArrayList<ProductModel>()

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        toolbarMain.title = title
        setSupportActionBar(toolbarMain)
        getProductsFromDatabase()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_add -> startActivityForResult<ProductActivity>(0)//item add moves to product activity
            R.id.item_home -> startActivityForResult<HomeActivity>(0)
            R.id.item_contact -> startActivityForResult<ContactActivity>(0)
        }
        return super.onOptionsItemSelected(item)
    }

//    override fun onProductClick(product: ProductModel){
//        startActivityForResult(intentFor<ProductActivity>().putExtra("product_edit",product),0)
//    }
//    //we are passing the selected product to the activity and this is enabled by the parcelable mechanism we just turned on
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        //loadProducts()
//        super.onActivityResult(requestCode, resultCode, data)
//    }

//    private fun loadProducts(){
//        showProducts(app.products.findAll())
//    }
//
//    fun showProducts(products:List<ProductModel>){
//        recyclerView.adapter = ProductAdapter(products,this)
//        recyclerView.adapter?.notifyDataSetChanged()

//    }

    fun getProductsFromDatabase(){
        database!!.reference.child("products").addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    productsList!!.clear()
                    for (prods in dataSnapshot.children) {
                        val  myProducts = prods.getValue(ProductModel::class.java)
                        productsList!!.add(myProducts!!)
                        layoutManager = LinearLayoutManager(applicationContext)
                        recyclerView.layoutManager = layoutManager
                        recyclerView.adapter = ProductAdapter(productsList!!, this@ProductListActivity)
                        recyclerView.adapter?.notifyDataSetChanged()
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                toast("Failed to retrive information from database.").show()
            }
        }
        )
    }
}



//https://firebase.google.com/docs/database/android/read-and-write