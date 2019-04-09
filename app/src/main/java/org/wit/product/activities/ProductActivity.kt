package org.wit.product.activities

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_product.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.product.models.Location
import org.wit.product.main.MainApp
import org.wit.product.models.ProductModel
import org.wit.product.helpers.readImage
import org.wit.product.helpers.readImageFromPath
import org.wit.product.helpers.showImagePicker
import java.io.File
import android.support.annotation.NonNull
import com.google.android.gms.tasks.OnFailureListener
import org.wit.product.R
import java.io.ByteArrayOutputStream
import java.util.UUID

class ProductActivity : AppCompatActivity(), AnkoLogger {

    var product = ProductModel()
    lateinit var app: MainApp
    var edit = false
    val IMAGE_REQUEST = 1
    var database: FirebaseDatabase? = null
    var productsRef: DatabaseReference? = null
    var myProducts: ProductModel? = null
    val LOCATION_REQUEST = 2
    var position: Int?= null
    var args: Bundle = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        app = application as MainApp
        database = FirebaseDatabase.getInstance()
        productsRef = database!!.getReference("products")
        position = args.getInt("Position")
        Log.i("POS", position.toString())



        if (intent.hasExtra("product_edit")) {
            edit = true
            if(edit){
                btnAdd.visibility = View.GONE
                btnUpdate.visibility = View.VISIBLE
            }
            product = intent.extras.getParcelable<ProductModel>("product_edit")
            productTitle.setText(product.title)
            description.setText(product.description)
            productImage.setImageBitmap(readImageFromPath(this, product.image))
            if(product.image !=null){
                chooseImage.setText(R.string.change_product_image)
            }
            btnAdd.setText(R.string.save_product)
        }

        btnAdd.setOnClickListener {
            product.title = productTitle.text.toString()
            product.description = description.text.toString()
            if (!product.title.isEmpty()) {
                saveToFirebase()
            }else{
                toast(R.string.enter_product_title)
            }
            info("add Button Pressed: $productTitle")
            setResult(AppCompatActivity.RESULT_OK)
            finish()
        }

        btnUpdate.setOnClickListener {
            if (edit) {
                app.products.update(product.copy())
                myProducts = ProductModel(product.id, productTitle.text.toString(), description.text.toString(), product.image)
                database!!.reference.child("products").child(product.id).setValue(myProducts)
                finish()
            }
        }


//getting the adapter position for the products
        productLocation.setOnClickListener {
            val location = Location()
            Log.i("Locaton Product", location.lat.toString() + location.lng.toString())
            MainApp.productsList!!.get(ProductAdapter.MainHolder.getPosition).lat = location.lat
            MainApp.productsList!!.get(ProductAdapter.MainHolder.getPosition).lng = location.lng
            location.zoom = 12.0f

            Log.i("Locaton Product", "${MainApp.productsList!!.get(0).lat.toString()}")

            startActivityForResult(intentFor<MapsActivity>().putExtra("location", location), LOCATION_REQUEST)
        }
        //present toolbar, support it
        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)

        chooseImage.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST)
        }
    }



    //inflate the menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_product, menu)
        if(edit && menu!==null)menu.getItem(0).setVisible(true)
        return super.onCreateOptionsMenu(menu)
    }

    //handle the event
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_delete -> {
                deleteProduct(product.id)
                finish()
            }
            R.id.item_cancel->{
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    product.image = data.getData().toString()
                    productImage.setImageBitmap(readImage(this, resultCode, data))
                    chooseImage.setText(R.string.change_product_image)
                }
                if (intent.hasExtra("product_edit")) {
                    //... as before
                    productImage.setImageBitmap(readImageFromPath(this, product.image))
                }
            }
            LOCATION_REQUEST -> {
                if (data != null) {
                    val location = data.extras.getParcelable<Location>("location")
                    MainApp.productsList!!.get(ProductAdapter.MainHolder.getPosition).lat = location.lat
                    MainApp.productsList!!.get(ProductAdapter.MainHolder.getPosition).lng = location.lng
                    product.zoom = location.zoom
                }
            }
        }
    }

    //saving to firebase
    fun saveToFirebase(){

        val prods = database!!.getReference("products").push().key
        myProducts = ProductModel(prods!!, productTitle.text.toString(), description.text.toString(), product.image,
            MainApp.productsList!!.get(ProductAdapter.MainHolder.getPosition).lat,
            MainApp.productsList!!.get(ProductAdapter.MainHolder.getPosition).lng)
        productsRef!!.child(prods!!).setValue(myProducts)
    }

//deleting the product from firebase using child and the key
    fun deleteProduct(key:String){
        productsRef!!.child(key).removeValue()
    }


}


//https://firebase.google.com/docs/database/android/read-and-write
//https://firebase.google.com/docs/database/android/read-and-write
//https://stackoverflow.com/questions/36223373/firebase-updatechildren-vs-setvalue
