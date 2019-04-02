package org.wit.product.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import org.wit.product.R
import org.wit.product.main.MainApp
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_product_list.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import org.wit.product.models.ProductModel
import java.text.FieldPosition

class ProductListActivity : AppCompatActivity(), ProductAdapter.ProductListener {
    override fun onProductClick(product: ProductModel) {
        startActivityForResult(intentFor<ProductActivity>().putExtra("product_edit", product),0)
    }

    lateinit var app: MainApp
    var database: FirebaseDatabase? = null
    var productsRef: DatabaseReference? = null

    var layoutManager: RecyclerView.LayoutManager? = null
    var productAdapter:ProductAdapter? = null
    var position: Int?= null
    var args: Bundle = Bundle()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)
        app = application as MainApp
        database = FirebaseDatabase.getInstance()
        productsRef = database!!.getReference("products")
        MainApp.productsList = ArrayList<ProductModel>()
        position = args.getInt("Position")
        Log.i("POS", position.toString())

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        toolbarMain.title = title
        setSupportActionBar(toolbarMain)
        getProductsFromDatabase()
        search.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(search: Editable?) {
                searchRecyclerView(search.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })
    }

    override fun onResume() {
        super.onResume()


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_add -> startActivityForResult<ProductActivity>(200)//item add moves to product activity
            R.id.item_home -> startActivityForResult<HomeActivity>(0)
            R.id.item_map -> startActivity<ProductMapsActivity>()
        }
        return super.onOptionsItemSelected(item)
    }

    fun getProductsFromDatabase(){
        database!!.reference.child("products").addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    MainApp.productsList!!.clear()
                    for (prods in dataSnapshot.children) {
                        val  myProducts = prods.getValue(ProductModel::class.java)
                        Log.i("KEYS", prods.key)
                        MainApp.productsList!!.add(myProducts!!)
                        layoutManager = LinearLayoutManager(applicationContext)
                        recyclerView.layoutManager = layoutManager
                        productAdapter = ProductAdapter(MainApp.productsList!!, this@ProductListActivity)
                        recyclerView.adapter = productAdapter
                        recyclerView.adapter?.notifyDataSetChanged()



                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                toast("Failed to retrieve information from database.").show()
            }
        }
        )
    }

    fun searchRecyclerView(search: String){
        var productListNew: ArrayList<ProductModel> = ArrayList()
        for (searchProduct in MainApp.productsList!!){
            if (searchProduct.title.toLowerCase().contains(search.toLowerCase())){
                productListNew.add(searchProduct)
                productAdapter!!.filterList(productListNew)
            }
        }
    }
}






//https://firebase.google.com/docs/database/android/read-and-write