package com.example.thesparkinstituteapp.Courses_Classes.Novodaya

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import com.example.thesparkinstituteapp.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions

class FullMapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap

    // Your institute location
    private val instituteLocation = LatLng(26.793823, 89.023983)

    private lateinit var markerIcon: BitmapDescriptor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_full_map, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.fullMapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Button: Open Google Maps app
        view.findViewById<ImageView>(R.id.btnOpenInMaps).setOnClickListener {
            val uri = Uri.parse("geo:0,0?q=${instituteLocation.latitude},${instituteLocation.longitude}(The Spark Institute)")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setPackage("com.google.android.apps.maps")
            startActivity(intent)
        }

        // Button: Map Type switch
        view.findViewById<ImageView>(R.id.btnMapType).setOnClickListener {
            if (::googleMap.isInitialized) {
                googleMap.mapType = when (googleMap.mapType) {
                    GoogleMap.MAP_TYPE_NORMAL -> GoogleMap.MAP_TYPE_HYBRID
                    GoogleMap.MAP_TYPE_HYBRID -> GoogleMap.MAP_TYPE_TERRAIN
                    else -> GoogleMap.MAP_TYPE_NORMAL
                }
            }
        }

        // Button: Recenter Camera
        view.findViewById<ImageView>(R.id.btnRecenter).setOnClickListener {
            if (::googleMap.isInitialized) {
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(instituteLocation, 16f))
            }
        }
        // add a marker
         markerIcon = getResizedMarker(R.drawable.spark_logo_reduced,105,44)
    }

    // to get resized marker
    private fun getResizedMarker(resource: Int, width: Int, height: Int): BitmapDescriptor {
        val bitmap = BitmapFactory.decodeResource(resources, resource)
        val resized = Bitmap.createScaledBitmap(bitmap, width, height, false)
        return BitmapDescriptorFactory.fromBitmap(resized)
    }


    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        // Enable location features if permission is granted
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            googleMap.isMyLocationEnabled = true
        }


        googleMap.addMarker(
            MarkerOptions()
                .position(instituteLocation)
                .title("The Spark Institute")
                .icon(markerIcon)
        )

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(instituteLocation, 16f))

    }
}
