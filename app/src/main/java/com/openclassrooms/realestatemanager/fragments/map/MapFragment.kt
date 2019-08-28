package com.openclassrooms.realestatemanager.fragments.map


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

/**
 * A simple [Fragment] subclass.
 */
class MapFragment : Fragment(), MapContract.MapViewInterface, OnMapReadyCallback {
    lateinit var mPlaces: PlacesClient
    private var API_KEY: String = BuildConfig.google_maps_key
    override fun onMapReady(p0: GoogleMap?) {
    }

    companion object {

        fun newInstance(): MapFragment {
            return MapFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        initializeMapsAndPlaces()
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    fun initializeMapsAndPlaces() {

        Places.initialize(requireContext(), API_KEY)

// Create a new Places client instance
        mPlaces = Places.createClient(requireContext())
    }

}
