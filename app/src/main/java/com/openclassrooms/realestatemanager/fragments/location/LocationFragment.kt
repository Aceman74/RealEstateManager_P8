package com.openclassrooms.realestatemanager.fragments.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.openclassrooms.realestatemanager.BuildConfig
import com.openclassrooms.realestatemanager.R


class LocationFragment : Fragment(), LocationContract.LocationViewInterface, OnMapReadyCallback {
    override fun onMapReady(p0: GoogleMap?) {
    }

    lateinit var mPlaces: PlacesClient
    private var API_KEY: String = BuildConfig.google_maps_key

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

        Places.initialize(requireContext(), API_KEY)

// Create a new Places client instance
        mPlaces = Places.createClient(requireContext())
    }
}