package org.wit.product.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_product.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.wit.product.R
import org.wit.product.main.MainApp
import org.wit.product.models.ProductModel

class ProductActivity : AppCompatActivity(), AnkoLogger {

    var product = ProductModel()
    lateinit var app:MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        app = application as MainApp
//present toolbar, support it
        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)

        if (intent.hasExtra("product_edit")) {
            product = intent.extras.getParcelable<ProductModel>("product_edit")
            productTitle.setText(product.title)
            description.setText(product.description)
        }

        btnAdd.setOnClickListener() {
            product.title = productTitle.text.toString()
            product.description = description.text.toString()
            if (product.title.isNotEmpty()) {
               // app.products.add(product.copy())
                app.products.create(product.copy())
                info("add Button Pressed: $productTitle")
                //app.products.forEach{info("add Button Pressed:${it.title}")}
               // app.products.findAll().forEach{info("add Button Pressed:  ${it}")}
                setResult(AppCompatActivity.RESULT_OK)
                finish()
            }
            else {
                toast("Please enter a title for this product")
            }

        }
    }
//inflate the menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_product, menu)
        return super.onCreateOptionsMenu(menu)
    }
//handle the event
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

