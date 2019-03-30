package org.wit.product.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import org.jetbrains.anko.startActivityForResult
import org.wit.product.R

class ContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_add -> startActivityForResult<ProductActivity>(0)//item add moves to product activity
            R.id.item_home -> startActivityForResult<HomeActivity>(0)
            R.id.item_contact -> startActivityForResult<ContactActivity>(0)
        }
        return super.onOptionsItemSelected(item)
    }
}
