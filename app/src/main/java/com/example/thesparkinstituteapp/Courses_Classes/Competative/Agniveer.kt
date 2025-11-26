package com.example.thesparkinstituteapp.Courses_Classes.Competative

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.example.thesparkinstituteapp.Courses_Classes.Novodaya.FullMapFragment
import com.example.thesparkinstituteapp.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.thesparkinstituteapp.Courses_Classes.MyTranslationFragment

class Agniveer : MyTranslationFragment(R.layout.fragment_agniveer) , OnMapReadyCallback {

    override val contentTextViewIds: List<Int> = listOf(
        R.id.tv1,
        R.id.tv2,
        R.id.tv3,
        R.id.tv4,
        R.id.tv5,
    )

    // 2. Specify the ID of the container holding your buttons
    override val buttonContainerId: Int = R.id.translation_buttons

    lateinit var smallMapView: MapView
    lateinit var googleMap: GoogleMap

    lateinit var  btnOpenFullMap: ImageView

    val instituteLocation = LatLng(26.793823, 89.023983)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_agniveer, container, false)

        val whatsappButton = view.findViewById<LinearLayout>(R.id.WhatsappBtnAgniveer)

        whatsappButton.setOnClickListener {
            openWhatsApp()
        }

        btnOpenFullMap = view.findViewById(R.id.btnOpenFullMap)

        // Small Google Map
        smallMapView = view.findViewById(R.id.smallMapView)
        smallMapView.onCreate(savedInstanceState)
        smallMapView.getMapAsync(this)

        // Open full map fragment
        btnOpenFullMap.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_2, FullMapFragment())
                .addToBackStack(null)
                .commit()
        }
        return view
    }
    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        with(googleMap.uiSettings) {
            isZoomControlsEnabled = false
            isScrollGesturesEnabled = false
            isZoomGesturesEnabled = false
        }

        googleMap.addMarker(
            MarkerOptions().position(instituteLocation).title("The Spark Institute")
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(instituteLocation, 15f))
    }

    override fun onStart() { super.onStart(); smallMapView.onStart() }
    override fun onResume() { super.onResume(); smallMapView.onResume() }
    override fun onPause() { super.onPause(); smallMapView.onPause() }
    override fun onStop() { super.onStop(); smallMapView.onStop() }
    override fun onDestroy() { super.onDestroy(); smallMapView.onDestroy() }
    override fun onLowMemory() { super.onLowMemory(); smallMapView.onLowMemory() }


    fun openWhatsApp() {
        val phoneNumber = "919832116164"
        val message = "Hello! I want to know more about The Spark Institute."

        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://wa.me/$phoneNumber?text=${Uri.encode(message)}")
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(requireContext(), "WhatsApp not installed!", Toast.LENGTH_SHORT).show()
        }
    }

}