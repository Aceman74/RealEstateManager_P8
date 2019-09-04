/*
 * *
 *  * Created by Lionel Joffray on 04/09/19 19:35
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 04/09/19 19:21
 *
 */

package com.openclassrooms.realestatemanager.activities.estatedetail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.navigation.NavigationView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.Utils
import com.openclassrooms.realestatemanager.activities.addestate.AddEstateActivity
import com.openclassrooms.realestatemanager.activities.login.EstateDetailContract
import com.openclassrooms.realestatemanager.activities.main.MainActivity
import com.openclassrooms.realestatemanager.adapters.SlideshowAdapter
import com.openclassrooms.realestatemanager.adapters.estatelist.EstateDetailAdapter
import com.openclassrooms.realestatemanager.injections.Injection
import com.openclassrooms.realestatemanager.models.EstateAndPictures
import com.openclassrooms.realestatemanager.utils.base.BaseActivity
import com.openclassrooms.realestatemanager.viewmodels.EstateViewModel
import com.openclassrooms.realpicturemanager.activities.viewmodels.PictureViewModel
import kotlinx.android.synthetic.main.activity_detail_estate.*
import kotlinx.android.synthetic.main.fragment_address_map.*
import kotlinx.android.synthetic.main.fragment_description.*
import timber.log.Timber
import kotlin.math.absoluteValue


class EstateDetailActivity(override val activityLayout: Int = R.layout.activity_detail_estate) : BaseActivity(), EstateDetailContract.EstateViewInterface, NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {


    lateinit var mRecyclerView: RecyclerView
    lateinit var mAdapter: SlideshowAdapter
    lateinit var estateViewModel: EstateViewModel
    lateinit var pictureViewModel: PictureViewModel
    var estateId = -1
    private lateinit var mMap: GoogleMap
    lateinit var marker: Marker
    lateinit var observePicture: List<EstateAndPictures>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(findViewById(R.id.detail_estate_toolbar))
        configureDrawerLayout(detail_estate_drawer_layout, detail_estate_toolbar)
        configureItemListeners()
        estateId = intent.getIntExtra("estateId", -1)
        configureDetails()
        configureMaps()
        configureRecyclerView()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isMapToolbarEnabled = false
        val newYork = LatLng(40.734402, -73.949882)
        marker = mMap.addMarker(MarkerOptions().position(newYork))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(newYork))
        mMap.cameraPosition.zoom.absoluteValue
    }

    private fun configureMaps() {

        val mapFragment = supportFragmentManager.findFragmentById(R.id.address_map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    private fun configureDetails() {
        if (estateId != -1) {
            val mViewModelFactory = Injection.provideViewModelFactory(this)
            this.estateViewModel = ViewModelProviders.of(this, mViewModelFactory).get(EstateViewModel::class.java)
            estateViewModel.getEstateById(estateId.toLong()).observe(this, Observer {

                desc_estate_type_txt.text = Utils.ListOfString.listOfType()[it[0].type]
                desc_estate_neighborhood_txt.text = Utils.ListOfString.listOfNeighborhood()[it[0].neighborhood]
                desc_estate_price_txt.text = it[0].price
                fragment_desc_desc_txt.text = it[0].description
                desc_sqft_choice_txt.text = it[0].sqft.toString()
                desc_rooms_choice_txt.text = it[0].rooms.toString()
                desc_bathrooms_choice_txt.text = it[0].bathrooms.toString()
                desc_bedrooms_choice_txt.text = it[0].bedrooms.toString()
                desc_available_choice_txt.text = Utils.ListOfString.listOfAvailable()[it[0].available]
                desc_agent_choice_txt.text = it[0].agent
                desc_date_added_choice_txt.text = it[0].addDate
                address_edit_txt.visibility = View.GONE
                address_txt_view.text = Utils.formatAddress(it[0].fullAddress)
                marker.position = LatLng(it[0].latitude!!, it[0].longitude!!)
                marker.title = "That's Here !"
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.position, 14f))
                if (it[0].editDate != null)
                    desc_last_mod_date_choice_txt.text = it[0].editDate
            })
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        //  Navigation Drawer item settings
        val id = item.itemId

        when (id) {
            R.id.drawer_first -> {
                val intent = Intent(baseContext, AddEstateActivity::class.java)
                startActivity(intent)
                Timber.i("Click Create")
            }
            R.id.drawer_second -> {
                val intent = Intent(baseContext, MainActivity::class.java)
                startActivity(intent)
                Timber.i("Click Main")
            }
            R.id.drawer_third -> {
                val intent = Intent(baseContext, EstateDetailActivity::class.java)
                startActivity(intent)
                Timber.i("Click Detail")
            }
            else -> {
            }
        }
        return true
    }

    /**
     * Override on back pressed when drawer layout is open.
     */
    override fun onBackPressed() {
        if (detail_estate_drawer_layout.isDrawerOpen(GravityCompat.START)) {
            detail_estate_drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    /**
     * On Toolbar item selected.
     */
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.toolbar_edit -> {
            Toast.makeText(this, "Edit action", Toast.LENGTH_LONG).show()
            val intent = Intent(this, AddEstateActivity::class.java)
            intent.putExtra("estateId", estateId)
            startActivity(intent)
            true
        }
        android.R.id.home -> {
            Toast.makeText(this, "Home action", Toast.LENGTH_LONG).show()
            true
        }
        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail_estate_toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    fun configureItemListeners() {
        detail_estate_nav_view.setNavigationItemSelectedListener(this)
    }

    /**
     * Initialize the recyclerview for history.
     */
    fun configureRecyclerView() {
        mRecyclerView = slideshow_recyclerview
        mRecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL))
        val mViewModelFactory = Injection.provideViewModelFactory(applicationContext)
        this.estateViewModel = ViewModelProviders.of(this, mViewModelFactory).get(EstateViewModel::class.java)
        this.pictureViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PictureViewModel::class.java)
        estateViewModel.getEstatePictures(estateId.toLong()).observe(this, Observer {
            observePicture = it
            mRecyclerView.adapter = EstateDetailAdapter(observePicture) {
                Timber.tag("RV click").i("$it")
                // lauchDetailActivity(it)
            }
        })
        mRecyclerView.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
    }
}
