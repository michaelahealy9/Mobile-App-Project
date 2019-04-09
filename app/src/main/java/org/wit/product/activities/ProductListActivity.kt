package org.wit.product.activities


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import org.wit.product.R
import org.wit.product.main.MainApp
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_product_list.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import org.wit.product.models.ProductModel


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
        searchBtn.setOnClickListener{
            searchRecyclerView(search.text.toString())
        }
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

    //get data from database and add them to list to populate recyclerview
    fun getProductsFromDatabase(){
        database!!.reference.child("products").addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    MainApp.productsList!!.clear() //clear because if not the data will be duplicated
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
    //search for product when search button is clicked
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


//https://stackoverflow.com/questions/28296708/get-clicked-item-and-its-position-in-recyclerview
//https://firebase.google.com/docs/database/android/read-and-write