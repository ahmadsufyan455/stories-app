package com.zerodev.dicostories.view.map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.zerodev.dicostories.R
import com.zerodev.dicostories.databinding.ActivityMapBinding

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMapBinding
    private lateinit var mapViewModel: MapViewModel
    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.user_location)

        token = intent.getStringExtra("TOKEN").toString()

        mapViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MapViewModel::class.java]

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))
        mapViewModel.getStoriesWithLocation(token).observe(this) { stories ->
            if (stories != null) {
                for (i in stories.indices) {
                    val lat = stories[i].lat
                    val lon = stories[i].lon
                    if (lat != null && lon != null) {
                        val positions = LatLng(lat, lon)
                        googleMap.addMarker(
                            MarkerOptions().position(positions).title(stories[i].name)
                                .snippet(stories[i].description)
                        )
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(positions, 5f))
                    }
                }
            }
        }
    }
}