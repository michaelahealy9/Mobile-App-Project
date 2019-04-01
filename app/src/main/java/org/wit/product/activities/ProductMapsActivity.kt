package org.wit.product.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.wit.product.R
import kotlinx.android.synthetic.main.activity_product_maps.*
import kotlinx.android.synthetic.main.content_product_maps.*
import org.wit.product.main.MainApp

class ProductMapsActivity : AppCompatActivity(), GoogleMap.OnMarkerClickListener {


    lateinit var map: GoogleMap

    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_maps)
        setSupportActionBar(toolbarMaps)
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync {
            map = it
            configureMap()
        }

        app = application as MainApp
    }


    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    fun configureMap() {
        map.uiSettings.setZoomControlsEnabled(true)
        app.products.findAll().forEach {
            val loc = LatLng(it.lat, it.lng)
            val options = MarkerOptions().title(it.title).position(loc)
            map.addMarker(options).tag = it.id
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
            map.setOnMarkerClickListener(this)
        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        productTitle.text = marker.title
        return false
    }
}


//fun configure map will iterate through all the products stoes and add a marker at the location of each of them