package com.openclassrooms.realestatemanager.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.openclassrooms.realestatemanager.BuildConfig

import com.openclassrooms.realestatemanager.R
import kotlinx.android.synthetic.main.fragment_location.*

class LocationFragment : Fragment(), OnMapReadyCallback {
    lateinit var mMap:GoogleMap
    lateinit var mMapsFragment:SupportMapFragment
    lateinit var mPlaces: PlacesClient
    private var API_KEY:String = BuildConfig.google_maps_key

    companion object {

        fun newInstance(): LocationFragment {
            return LocationFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        initializeMapsAndPlaces()
        return inflater.inflate(R.layout.fragment_location, container, false)
    }

    fun initializeMapsAndPlaces() {

        mMapsFragment = childFragmentManager.findFragmentById(R.id.location_map) as SupportMapFragment
        mMapsFragment.getMapAsync(this)
        // Setup Places Client
        if (!Places.isInitialized()) {
            context?.let { Places.initialize(it, API_KEY) }
            mPlaces = context?.let { Places.createClient(it) }!!
        }
    }
    override fun onMapReady(p0: GoogleMap?) {

    }

}
