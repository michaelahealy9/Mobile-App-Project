package org.wit.product.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_product.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.wit.product.R
import org.wit.product.main.MainApp
import org.wit.product.models.ProductModel
import org.wit.product.helpers.readImage
import org.wit.product.helpers.readImageFromPath
import org.wit.product.helpers.showImagePicker




class ProductActivity : AppCompatActivity(), AnkoLogger {

    var product = ProductModel()
    lateinit var app: MainApp
    var edit = false
    val IMAGE_REQUEST = 1
    var database: FirebaseDatabase? = null
    var productsRef: DatabaseReference? = null
    var myProducts: ProductModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        app = application as MainApp
        database = FirebaseDatabase.getInstance()
        productsRef = database!!.getReference("products")


        if (intent.hasExtra("product_edit")) {
            edit = true
            product = intent.extras.getParcelable<ProductModel>("product_edit")
            productTitle.setText(product.title)
            description.setText(product.description)
            productImage.setImageBitmap(readImageFromPath(this, product.image))
            if(product.image !=null){
                chooseImage.setText(R.string.change_product_image)
            }
            btnAdd.setText(R.string.save_product)
        }

        btnAdd.setOnClickListener() {
            product.title = productTitle.text.toString()
            product.description = description.text.toString()
            if (product.title.isEmpty()) {
                toast(R.string.enter_product_title)
            } else {
                if (edit) {
                    app.products.update(product.copy())
                } else {
                    app.products.create(product.copy())
                }
            }
            info("add Button Pressed: $productTitle")
            //app.products.forEach{info("add Button Pressed:${it.title}")}
            // app.products.findAll().forEach{info("add Button Pressed:  ${it}")}
            setResult(AppCompatActivity.RESULT_OK)


            val mFavId = database!!.getReference("Products").push().key
            myProducts = ProductModel(product.id, productTitle.text.toString(), description.text.toString(), product.image)
            productsRef!!.child(mFavId!!).setValue(myProducts)
            finish()
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
                app.products.delete(product)
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
        }
    }
}

