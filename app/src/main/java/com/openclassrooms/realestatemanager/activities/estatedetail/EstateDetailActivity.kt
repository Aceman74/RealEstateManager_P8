/*
 * *
 *  * Created by Lionel Joffray on 19/09/19 21:47
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 19/09/19 21:47
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
import com.openclassrooms.realestatemanager.activities.addestate.AddEstateActivity
import com.openclassrooms.realestatemanager.activities.main.MainActivity
import com.openclassrooms.realestatemanager.activities.settings.SettingsActivity
import com.openclassrooms.realestatemanager.adapters.detailestate.EstateDetailAdapter
import com.openclassrooms.realestatemanager.extensions.formatAddress
import com.openclassrooms.realestatemanager.extensions.priceAddSpace
import com.openclassrooms.realestatemanager.extensions.priceRemoveSpace
import com.openclassrooms.realestatemanager.injections.Injection
import com.openclassrooms.realestatemanager.models.EstateAndPictures
import com.openclassrooms.realestatemanager.utils.Utils
import com.openclassrooms.realestatemanager.utils.base.BaseActivity
import com.openclassrooms.realestatemanager.viewmodels.EstateViewModel
import com.openclassrooms.realestatemanager.viewmodels.PictureViewModel
import kotlinx.android.synthetic.main.activity_detail_estate.*
import kotlinx.android.synthetic.main.fragment_address_map.*
import kotlinx.android.synthetic.main.fragment_description.*
import timber.log.Timber
import kotlin.math.absoluteValue


/**
 * This activity is for seeing the details of an existing Estate.
 * Extends:
 * @see BaseActivity for setting the view
 * @see EstateDetailContract contract for MVP
 * @NavigationView
 * @OnMapReadyCallback for locating Estate on a Static Map
 *
 */
class EstateDetailActivity(override val activityLayout: Int = R.layout.activity_detail_estate) : BaseActivity(), EstateDetailContract.EstateViewInterface, NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    lateinit var mRecyclerView: RecyclerView
    lateinit var mEstateViewModel: EstateViewModel
    lateinit var mPictureViewModel: PictureViewModel
    private val mPresenter = EstateDetailPresenter()
    var mEstateId = -1
    var mUserId = ""
    var mDevise = "$"
    private lateinit var mMap: GoogleMap
    lateinit var mMarker: Marker
    lateinit var mObservePicture: List<EstateAndPictures>

    /**
     * Setting the presenter, view, preferences for devise, getting and showing the detail from Database,
     * configure static map and pictures for recyclerview.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.attachView(this)
        configureView()
        loadSharedPref()
        configureDetails()
        configureMaps()
        configureRecyclerView()
    }

    /**
     * Set the view, get the estate Id intent from fragmentList.
     */
    override fun configureView() {
        setSupportActionBar(findViewById(R.id.detail_estate_toolbar))
        configureDrawerLayout(detail_estate_drawer_layout, detail_estate_toolbar)
        navigationDrawerHeader(detail_estate_nav_view)
        mEstateId = intent.getIntExtra("mEstateId", -1)
        detail_estate_nav_view.setNavigationItemSelectedListener(this)
    }

    /**
     * Load user preferences.
     */
    override fun loadSharedPref() {
        val shared = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE)
        mDevise = shared.getString("actual_devise", "$")!!
        when (mDevise) {
            "€" -> {
                money_icon.text = "€"
            }
        }
    }

    /**
     * On map ready.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isMapToolbarEnabled = false
        val newYork = LatLng(40.734402, -73.949882)
        mMarker = mMap.addMarker(MarkerOptions().position(newYork))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(newYork))
        mMap.cameraPosition.zoom.absoluteValue
    }

    /**
     * Set the map.
     */
    override fun configureMaps() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.address_map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    /**
     * Update the view with all detail with the ID from Intent, use EstateViewModel to observe the
     * Estate list with LiveData.
     */
    override fun configureDetails() {
        if (mEstateId != -1) {
            val mViewModelFactory = Injection.provideViewModelFactory(this)
            this.mEstateViewModel = ViewModelProviders.of(this, mViewModelFactory).get(EstateViewModel::class.java)
            mEstateViewModel.getEstateById(mEstateId.toLong()).observe(this, Observer {
                mUserId = it[0].userId_fk
                desc_estate_type_txt.text = Utils.ListOfString.listOfType()[it[0].type]
                desc_estate_neighborhood_txt.text = Utils.ListOfString.listOfNeighborhood()[it[0].neighborhood]
                desc_estate_price_txt.text = it[0].price
                if (desc_estate_price_txt.text != getString(R.string.set_price) && money_icon.text == "€") {
                    desc_estate_price_txt.text = String().priceAddSpace(Utils.convertDollarToEuro(String().priceRemoveSpace(it[0].price).toInt()).toString())
                }
                fragment_desc_desc_txt.text = it[0].description
                desc_sqft_choice_txt.text = it[0].sqft.toString()
                desc_rooms_choice_txt.text = it[0].rooms.toString()
                desc_bathrooms_choice_txt.text = it[0].bathrooms.toString()
                desc_bedrooms_choice_txt.text = it[0].bedrooms.toString()
                desc_available_choice_txt.text = Utils.ListOfString.listOfAvailable()[it[0].available]
                desc_agent_choice_txt.text = it[0].agent
                desc_date_added_choice_txt.text = it[0].addDate
                address_edit_txt.visibility = View.GONE

                when (it[0].available) {
                    1 -> {
                        add_estate_state_txt.text = getString(R.string.sold)
                        add_estate_state_txt.setTextColor(resources.getColor(R.color.quantum_googred))
                        detail_estate_date_sold.text = it[0].soldDate
                    }
                }
                address_txt_view.text = String().formatAddress(it[0].fullAddress)
                mMarker.position = LatLng(it[0].latitude!!, it[0].longitude!!)
                mMarker.title = "That's Here !"
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mMarker.position, 14f))
                if (it[0].editDate != null)
                    desc_last_mod_date_choice_txt.text = it[0].editDate
            })
        }
    }

    /**
     * Navigation drawer listeners.
     */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.drawer_first -> {
                val intent = Intent(baseContext, MainActivity::class.java)
                startActivity(intent)
                Timber.i(getString(R.string.main_click))
            }
            R.id.drawer_second -> {
                val intent = Intent(baseContext, SettingsActivity::class.java)
                startActivity(intent)
                Timber.i(getString(R.string.settings_click))
            }
            R.id.drawer_third -> {
                signOutUserFromFirebase()
                Timber.i(getString(R.string.logout))
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
            if (mUserId == currentUser?.uid) {
                Toast.makeText(this, "Edit Estate", Toast.LENGTH_LONG).show()
                val intent = Intent(this, AddEstateActivity::class.java)
                intent.putExtra("mEstateId", mEstateId)
                startActivity(intent)
            } else {
                Utils.snackBarPreset(findViewById(android.R.id.content), "This estate hasn't been added by you.")
            }
            true
        }
        android.R.id.home -> {
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    /**
     * Create toolbar.
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail_estate_toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * Initialize the recyclerview for picture preview.
     */
    override fun configureRecyclerView() {
        mRecyclerView = slideshow_recyclerview
        mRecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL))
        val mViewModelFactory = Injection.provideViewModelFactory(applicationContext)
        this.mEstateViewModel = ViewModelProviders.of(this, mViewModelFactory).get(EstateViewModel::class.java)
        this.mPictureViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PictureViewModel::class.java)
        mRecyclerView.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
        mEstateViewModel.getEstatePictures(mEstateId.toLong()).observe(this, Observer {
            mObservePicture = it
            mRecyclerView.adapter = EstateDetailAdapter(mObservePicture) {
                Timber.tag("RV click").i("$it")
                // launchDetailActivity(it)
            }
            showNearby(it)
        })
    }

    /**
     * This method show the nearby commodities if there is( from DATABASE).
     */
    override fun showNearby(it: List<EstateAndPictures>?) {
        if (it!![0].nearby.isNotEmpty()) {
            var i = 0
            var school = 0
            var police = 0
            var hospital = 0
            while (i < it[0].nearby.size) {

                when {
                    it[0].nearby[i].type == "School" -> {
                        school_nbr_ly.visibility = View.VISIBLE
                        title_nearby.visibility = View.VISIBLE
                        school++
                        address_school_nbr.text = school.toString()
                    }
                    it[0].nearby[i].type == "Police Station" -> {
                        police_station_nbr_ly.visibility = View.VISIBLE
                        title_nearby.visibility = View.VISIBLE
                        police++
                        address_police_nbr.text = school.toString()
                    }
                    it[0].nearby[i].type == "Hospital" -> {
                        hospital_nbr_ly.visibility = View.VISIBLE
                        address_title.visibility = View.VISIBLE
                        hospital++
                        address_hospital_nbr.text = school.toString()
                    }
                }
                i++
            }
        }
    }
}
