package org.wit.product.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_home.*
import org.wit.product.R
import android.support.v4.media.session.MediaControllerCompat.setMediaController
import android.net.Uri
import android.widget.MediaController

import android.widget.VideoView





class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(org.wit.product.R.layout.activity_home)

        val videoView = findViewById(org.wit.product.R.id.video_view) as VideoView
        val videoPath = "android.resource://" + packageName + "/" + R.raw.video;
        val uri = Uri.parse(videoPath)
        videoView.setVideoURI(uri)

        val mediaController = MediaController(this)
        videoView.setMediaController(mediaController)
        mediaController.setAnchorView(videoView)
        button.setOnClickListener {
            startActivity(Intent(this,ProductListActivity::class.java))
        }
    }


}

//https://firebase.google.com/docs/database/android/start
//https://stackoverflow.com/questions/45267041/not-enough-information-to-infer-parameter-t-with-kotlin-and-android
//https://codinginflow.com/tutorials/android/videoview