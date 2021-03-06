package org.wit.product.activities

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.wit.product.R
import org.wit.product.models.Location


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerDragListener, GoogleMap.OnMarkerClickListener {

    private lateinit var map: GoogleMap
    var location = Location()
//var variables are mutable and can be changed
    //lateinit means the varibale will be initiased later on

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        location = intent.extras.getParcelable<Location>("location")
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        val loc = LatLng(location.lat, location.lng)
        val options = MarkerOptions()
            .title("Location")
            .snippet("GPS : " + loc.toString())
            .draggable(true)
            .position(loc)
        map.addMarker(options)
        map.setOnMarkerClickListener(this)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, location.zoom))
        map.setOnMarkerDragListener(this)

    }

    override fun onMarkerDragStart(marker: Marker) {
    }

    override fun onMarkerDrag(marker: Marker) {
    }

    override fun onMarkerDragEnd(marker: Marker) {
        location.lat = marker.position.latitude
        location.lng = marker.position.longitude
        location.zoom = map.cameraPosition.zoom

        Log.i("Location OnDrag", location.lat.toString()+ location.lng.toString())
    }

    override fun onBackPressed() {
        val resultIntent = Intent()
        resultIntent.putExtra("location", location)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
        super.onBackPressed()
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val loc = LatLng(location.lat, location.lng)
        marker.setSnippet("GPS : " + loc.toString())
        return false
    }

}