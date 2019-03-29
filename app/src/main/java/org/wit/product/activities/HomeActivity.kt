package org.wit.product.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_home.*
import org.wit.product.R

class HomeActivity : AppCompatActivity() {

    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("message")



    override fun onCreate(savedInstanceState: Bundle?) {
        myRef.setValue("Testing Firebase")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        button.setOnClickListener {
            startActivity(Intent(this,ProductListActivity::class.java))
        }
    }
}

//https://firebase.google.com/docs/database/android/start
