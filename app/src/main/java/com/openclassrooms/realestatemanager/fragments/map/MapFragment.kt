/*
 * *
 *  * Created by Lionel Joffray on 04/09/19 19:35
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 04/09/19 19:21
 *
 */

package com.openclassrooms.realestatemanager.fragments.map


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.net.PlacesClient
import com.openclassrooms.realestatemanager.BuildConfig
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.Utils
import com.openclassrooms.realestatemanager.injections.Injection
import com.openclassrooms.realestatemanager.viewmodels.EstateViewModel
import kotlinx.android.synthetic.main.custom_info_window.view.*
import kotlin.math.absoluteValue


/**
 * A simple [Fragment] subclass.
 */
class MapFragment : Fragment(), MapContract.MapViewInterface, OnMapReadyCallback, GoogleMap.InfoWindowAdapter {


    lateinit var mPlaces: PlacesClient
    private var API_KEY: String = BuildConfig.google_maps_key
    val ZOOM_LEVEL = 13f
    lateinit var estateViewModel: EstateViewModel

    private lateinit var mMap: GoogleMap

    companion object {

        fun newInstance(): MapFragment {
            return MapFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        configureMapMarkers()
    }

    override fun getInfoContents(marker: Marker): View {
        val view = (childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment).layoutInflater
                .inflate(R.layout.custom_info_window, null)
        Glide.with(this.requireContext())
                .load(marker.tag)
                .centerCrop()
                .into(view.custom_pic)
        view.custom_name.text = marker.title
        view.custom_details.text = marker.snippet
        return view
    }

    override fun getInfoWindow(marker: Marker?): View? {
        return null
    }

    private fun configureMapMarkers() {

        val mViewModelFactory = Injection.provideViewModelFactory(this.requireContext())
        this.estateViewModel = ViewModelProviders.of(this, mViewModelFactory).get(EstateViewModel::class.java)

        estateViewModel.allEstateWithPitures.observe(this, Observer { estate ->
            var i = 0
            while (i < estate.size) {
                mMap.addMarker(MarkerOptions()
                        .snippet(Utils.formatAddress(estate[i].estate.fullAddress))
                        .position(LatLng(estate[i].estate.latitude!!, estate[i].estate.longitude!!))
                        .title("$ " + estate[i].estate.price))
                        .tag = estate[i].pictures[0].picturePath
                i++
            }
        })
    }

    /**
     * Called when the map is ready to add all markers and objects to the map.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isMapToolbarEnabled = false
        mMap.setInfoWindowAdapter(this)
        val newYork = LatLng(40.734402, -73.949882)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(newYork))
        mMap.cameraPosition.zoom.absoluteValue
    }


}
