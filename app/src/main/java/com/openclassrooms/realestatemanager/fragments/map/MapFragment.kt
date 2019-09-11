/*
 * *
 *  * Created by Lionel Joffray on 11/09/19 20:37
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 11/09/19 20:37
 *
 */

package com.openclassrooms.realestatemanager.fragments.map


import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import com.openclassrooms.realestatemanager.extensions.formatAddress
import com.openclassrooms.realestatemanager.injections.Injection
import com.openclassrooms.realestatemanager.viewmodels.EstateViewModel
import kotlinx.android.synthetic.main.custom_info_window.view.*
import kotlin.math.absoluteValue


/**
 * A simple [Fragment] subclass.
 */
class MapFragment : Fragment(), MapContract.MapViewInterface, OnMapReadyCallback, GoogleMap.InfoWindowAdapter {

    private val mPresenter = MapPresenter()
    lateinit var mPlaces: PlacesClient
    private var API_KEY: String = BuildConfig.google_maps_key
    lateinit var mEstateViewModel: EstateViewModel
    val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 100

    private lateinit var mMap: GoogleMap

    companion object {

        fun newInstance(): MapFragment {
            return MapFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter.attachView(this)
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

    /**
     * Get the location btn permission
     */
    override fun getLocationPermission() {

        if (ContextCompat.checkSelfPermission(requireContext(),
                        android.Manifest.permission.ACCESS_FINE_LOCATION) === PackageManager.PERMISSION_GRANTED) {
            mMap.isMyLocationEnabled = true
        } else {
            ActivityCompat.requestPermissions(requireActivity(),
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
        }
    }

    override fun getInfoWindow(marker: Marker?): View? {
        return null
    }

    override fun configureMapMarkers() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        val mViewModelFactory = Injection.provideViewModelFactory(this.requireContext())
        this.mEstateViewModel = ViewModelProviders.of(this, mViewModelFactory).get(EstateViewModel::class.java)

        mEstateViewModel.allEstateWithPitures.observe(this, Observer { estate ->
            var i = 0
            while (i < estate.size) {
                mMap.addMarker(MarkerOptions()
                        .snippet(String().formatAddress(estate[i].estate.fullAddress))
                        .position(LatLng(estate[i].estate.latitude!!, estate[i].estate.longitude!!))
                        .title("$ " + estate[i].estate.price))
                        .tag = when (estate[i].pictures.isEmpty()) {
                    true -> "0"
                    false -> estate[i].pictures[0].picturePath
                }
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
        getLocationPermission()
    }


}
