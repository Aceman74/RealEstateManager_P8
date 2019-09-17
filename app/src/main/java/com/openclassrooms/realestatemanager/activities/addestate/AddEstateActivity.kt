/*
 * *
 *  * Created by Lionel Joffray on 17/09/19 23:02
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 17/09/19 23:01
 *
 */

package com.openclassrooms.realestatemanager.activities.addestate

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.NumberPicker
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.esafirm.imagepicker.features.ImagePicker
import com.esafirm.imagepicker.features.ReturnMode
import com.esafirm.imagepicker.model.Image
import com.evernote.android.state.State
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.navigation.NavigationView
import com.openclassrooms.realestatemanager.BuildConfig
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.activities.main.MainActivity
import com.openclassrooms.realestatemanager.activities.settings.SettingsActivity
import com.openclassrooms.realestatemanager.extensions.formatAddress
import com.openclassrooms.realestatemanager.extensions.hideKeyboard
import com.openclassrooms.realestatemanager.extensions.priceAddSpace
import com.openclassrooms.realestatemanager.extensions.priceRemoveSpace
import com.openclassrooms.realestatemanager.injections.Injection
import com.openclassrooms.realestatemanager.models.Estate
import com.openclassrooms.realestatemanager.models.User
import com.openclassrooms.realestatemanager.models.places.nearby_search.Nearby
import com.openclassrooms.realestatemanager.models.places.nearby_search.Result
import com.openclassrooms.realestatemanager.utils.NumberPickerDialog
import com.openclassrooms.realestatemanager.utils.Utils
import com.openclassrooms.realestatemanager.utils.base.BaseActivity
import com.openclassrooms.realestatemanager.utils.rxbus.RxBus
import com.openclassrooms.realestatemanager.utils.rxbus.RxEvent
import com.openclassrooms.realestatemanager.viewmodels.EstateViewModel
import com.openclassrooms.realpicturemanager.activities.viewmodels.PictureViewModel
import com.openclassrooms.realusermanager.activities.viewmodels.UserViewModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_add_estate.*
import kotlinx.android.synthetic.main.fragment_add_images.*
import kotlinx.android.synthetic.main.fragment_address_map.*
import kotlinx.android.synthetic.main.fragment_description.*
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.math.absoluteValue


class AddEstateActivity(override val activityLayout: Int = R.layout.activity_add_estate) : BaseActivity(), AddEstateContract.AddEstateViewInterface, NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, NumberPicker.OnValueChangeListener, OnMapReadyCallback {

    private val AUTOCOMPLETE_REQUEST_CODE = 101
    private val mPresenter = AddEstatePresenter()
    @State
    var mPriceResult: String = ""
    @State
    var mDescResult: String = ""
    @State
    var mType = -1
    @State
    var mNeighborhood = -1
    @State
    var mSqft = 0
    @State
    var mRooms = 0
    @State
    var mBathrooms = 0
    @State
    var mBedrooms = 0
    @State
    var mAvailable = 0
    @State
    var mDateCreate = ""
    @State
    var mAddress = ""
    var mSoldDate: String? = null
    @State
    var mPickerArray = IntArray(9)
    @State
    var mPicturePathArray = arrayListOf("", "", "", "", "", "", "", "")
    @State
    var mSchool = ArrayList<String>()
    var mPolice: List<Result>? = null
    var mHospital: List<Result>? = null
    @State
    var mIntentEId: Long = -1
    @State
    var mDevise = "$"
    private lateinit var mMap: GoogleMap
    lateinit var mMarker: Marker
    private lateinit var mPickerDisposable: Disposable
    lateinit var mEstateViewModel: EstateViewModel
    lateinit var mUserViewModel: UserViewModel
    lateinit var mPictureViewModel: PictureViewModel
    lateinit var mEstatePhotosDir: File
    var PICK_IMAGE_REQUEST = 50

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.attachView(this)
        configureView()
        configureListeners()
        mEstatePhotosDir = mPresenter.createPhotosFolder(applicationContext)
        configureViewModel()
        configureMaps()
        mIntentEId = intent.getIntExtra("mEstateId", -1).toLong()
        editIntent()
        loadSharedPref()
        address_search_layout.setOnClickListener {
            autocompleteIntent()
        }
        mPickerDisposable = RxBus.listen(RxEvent.PickerDescEvent::class.java).subscribe {
            mDescResult = it.desc
        }
        mPickerDisposable = RxBus.listen(RxEvent.PickerPriceEvent::class.java).subscribe {
            mPriceResult = it.price
        }
    }

    override fun onResume() {
        super.onResume()
        when {
            mPriceResult != "" -> desc_estate_price_txt.text = mPriceResult
            mDescResult != "" -> fragment_desc_desc_txt.text = mDescResult
            mType != -1 -> desc_estate_type_txt.text = Utils.ListOfString.listOfType()[mType]
            mNeighborhood != -1 -> desc_estate_neighborhood_txt.text = Utils.ListOfString.listOfNeighborhood()[mType]
            mSqft != 0 -> desc_sqft_choice_txt.text = mSqft.toString()
            mRooms != 0 -> desc_rooms_choice_txt.text = mRooms.toString()
            mBathrooms != 0 -> desc_bathrooms_choice_txt.text = mBathrooms.toString()
            mBedrooms != 0 -> desc_bedrooms_choice_txt.text = mBedrooms.toString()
            mAvailable != 0 -> desc_available_choice_txt.text = Utils.ListOfString.listOfAvailable()[mAvailable]
            mDateCreate != "" -> desc_date_added_choice_txt.text = mDateCreate
            mAddress != "" -> desc_date_added_choice_txt.text = mAddress
        }
    }

    override fun configureView() {
        setSupportActionBar(findViewById(R.id.add_estate_toolbar))
        configureDrawerLayout(add_estate_drawer_layout, add_estate_toolbar)
        navigationDrawerHeader(add_estate_nav_view)
        Places.initialize(applicationContext, BuildConfig.google_maps_key)
        Places.createClient(this)
        desc_date_added_choice_txt.text = Utils.todayDate
        desc_agent_choice_txt.text = currentUser?.displayName.toString()
    }

    override fun loadSharedPref() {
        val shared = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE)
        mDevise = shared.getString("actual_devise", "$")!!
        when (mDevise) {
            "€" -> {
                money_icon.text = "€"
            }
        }
    }

    override fun editIntent() {
        if (mIntentEId.toInt() != -1) {
            mEstateViewModel.getEstatePictures(mIntentEId).observe(this, Observer {
                val result = it[0]
                desc_estate_type_txt.text = Utils.ListOfString.listOfType()[result.estate.type]
                mPickerArray[0] = result.estate.type
                mType = result.estate.type
                desc_estate_neighborhood_txt.text = Utils.ListOfString.listOfNeighborhood()[result.estate.neighborhood]
                mPickerArray[1] = result.estate.neighborhood
                mNeighborhood = result.estate.neighborhood
                desc_estate_price_txt.text = result.estate.price
                mPriceResult = result.estate.price
                if (desc_estate_price_txt.text != getString(R.string.set_price) && money_icon.text == "€") {
                    desc_estate_price_txt.text = String().priceAddSpace(Utils.convertDollarToEuro(String().priceRemoveSpace(mPriceResult).toInt()).toString())
                    mPriceResult = String().priceAddSpace(Utils.convertDollarToEuro(String().priceRemoveSpace(mPriceResult).toInt()).toString())
                }
                fragment_desc_desc_txt.text = result.estate.description
                mDescResult = result.estate.description
                desc_sqft_choice_txt.text = result.estate.sqft.toString()
                mPickerArray[4] = result.estate.sqft
                mSqft = result.estate.sqft
                desc_rooms_choice_txt.text = result.estate.rooms.toString()
                mPickerArray[5] = result.estate.rooms
                mRooms = result.estate.rooms
                desc_bathrooms_choice_txt.text = result.estate.bathrooms.toString()
                mPickerArray[6] = result.estate.bathrooms
                mBathrooms = result.estate.bathrooms
                desc_bedrooms_choice_txt.text = result.estate.bedrooms.toString()
                mPickerArray[7] = result.estate.bedrooms
                mBedrooms = result.estate.bedrooms
                desc_agent_choice_txt.text = result.estate.agent
                desc_available_choice_txt.text = Utils.ListOfString.listOfAvailable()[result.estate.available]
                mPickerArray[8] = result.estate.available
                mAvailable = result.estate.available
                desc_date_added_choice_txt.text = result.estate.addDate
                mDateCreate = result.estate.addDate
                desc_last_mod_date_choice_txt.text = Utils.todayDate
                address_txt_view.text = String().formatAddress(result.estate.fullAddress)
                mAddress = result.estate.fullAddress
                mSoldDate = result.estate.soldDate
                if (mSoldDate != null && result.estate.available == 1) {
                    detail_estate_date_sold.text = mSoldDate
                    add_estate_state_txt.text = getString(R.string.sold)
                    add_estate_state_txt.setTextColor(resources.getColor(R.color.quantum_googred))
                }
                mMarker.position = LatLng(result.estate.latitude!!, result.estate.longitude!!)
                mMarker.title = "That's Here !"
                var i = 0
                while (i < result.pictures.size) {
                    mPicturePathArray[i] = result.pictures[i].picturePath
                    i++
                }
                first_pic.setPadding(0, 0, 0, 0)
                second_pic.setPadding(0, 0, 0, 0)
                Glide.with(this)
                        .load(mPicturePathArray[0])
                        .into(first_pic)
                Glide.with(this)
                        .load(mPicturePathArray[1])
                        .into(second_pic)
            })
        }
    }

    override fun configureMaps() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.address_map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    /**
     * Called when the map is ready to add all markers and objects to the map.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isMapToolbarEnabled = false
        val newYork = LatLng(40.734402, -73.949882)
        mMarker = mMap.addMarker(MarkerOptions().position(newYork)
                .title("Marker in NewYork"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(newYork))
        mMap.cameraPosition.zoom.absoluteValue
    }

    override fun configureViewModel() {
        val mViewModelFactory = Injection.provideViewModelFactory(this)
        this.mEstateViewModel = ViewModelProviders.of(this, mViewModelFactory).get(EstateViewModel::class.java)
        this.mUserViewModel = ViewModelProviders.of(this, mViewModelFactory).get(UserViewModel::class.java)
        this.mPictureViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PictureViewModel::class.java)
    }

    override fun addToDatabase() {
        if (mIntentEId.toInt() == -1) {
            val user = User(currentUser!!.uid, currentUser!!.displayName.toString(), currentUser!!.email.toString(), currentUser!!.photoUrl.toString(), "TODAY")
            this.mUserViewModel.createUser(user)
            val estate = Estate(null, currentUser!!.uid, mType, mNeighborhood, mPriceResult, mDescResult, mSqft, mRooms, mBathrooms, mBedrooms, mAvailable, currentUser!!.displayName!!, Utils.todayDate, null, mSoldDate, mMarker.position.latitude, mMarker.position.longitude, mAddress)
            mEstateViewModel.createEstate(estate, mPicturePathArray, mEstatePhotosDir, currentUser!!.displayName, mPictureViewModel, mEstateViewModel, mSchool, mPolice, mHospital)
        } else {
            val estate = Estate(mIntentEId, currentUser!!.uid, mType, mNeighborhood, mPriceResult, mDescResult, mSqft, mRooms, mBathrooms, mBedrooms, mAvailable, currentUser!!.displayName!!, mDateCreate, Utils.todayDate, mSoldDate, mMarker.position.latitude, mMarker.position.longitude, mAddress)
            this.mEstateViewModel.updateEstate(estate, mIntentEId, mPicturePathArray, mEstatePhotosDir, currentUser!!.displayName, mPictureViewModel)
        }
    }

    override fun updateNearbySchool(details: Nearby) {
        var i = 0
        while (i < details.results!!.size) {
            val name = details.results!![i].name!!
            if (name.contains("Preschool") || name.contains("Elementary")
                    || name.contains("Middle") || name.contains("High")
                    || name.contains("College")) {
                mSchool.add(name)
                Timber.tag("School").i(name)
            }
            i++
        }
        if (mSchool.size > 0) {
            school_nbr_ly.visibility = View.VISIBLE
            title_nearby.visibility = View.VISIBLE
            address_school_nbr.text = mSchool.size.toString()
        }
    }

    override fun updateNearbyPolice(details: Nearby) {
        mPolice = details.results!!
        if (mPolice!!.isNotEmpty()) {
            police_station_nbr_ly.visibility = View.VISIBLE
            title_nearby.visibility = View.VISIBLE
            address_police_nbr.text = mPolice!!.size.toString()
        }
    }

    override fun updateNearbyHospital(details: Nearby) {
        mHospital = details.results!!
        if (mHospital!!.isNotEmpty()) {
            hospital_nbr_ly.visibility = View.VISIBLE
            title_nearby.visibility = View.VISIBLE
            address_hospital_nbr.text = mHospital!!.size.toString()
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            desc_estate_type_img, desc_estate_type_txt -> {
                showNumberPicker(1, mPickerArray[0], null)
                Timber.i("Click Type")
            }
            desc_neighbourhood_img, desc_estate_neighborhood_txt -> {
                showNumberPicker(2, mPickerArray[1], null)
                Timber.i("Click Neighbourhood")
            }
            desc_price_img, desc_estate_price_txt -> {
                showNumberPicker(3, mPickerArray[0], String().priceRemoveSpace(mPriceResult))
                Timber.i("Click Price")
            }
            desc_description_img, fragment_desc_desc_txt -> {
                showNumberPicker(4, mPickerArray[0], mDescResult)
                Timber.i("Click Description")
            }
            desc_sqft_img, surface_tv -> {
                showNumberPicker(5, mPickerArray[4], null)
                Timber.i("Click Sqft")
            }
            desc_rooms_img, rooms_tv -> {
                showNumberPicker(6, mPickerArray[5], null)
                Timber.i("Click Rooms")
            }
            desc_bathrooms_img, bathrooms_tv -> {
                showNumberPicker(7, mPickerArray[6], null)
                Timber.i("Click Bathrooms")
            }
            desc_bedrooms_img, bedrooms_tv -> {
                showNumberPicker(8, mPickerArray[7], null)
                Timber.i("Click Bedrooms")
            }
            desc_available_img, available_tv -> {
                showNumberPicker(9, mPickerArray[8], null)
                Timber.i("Click Available")
            }
            first_pic -> {
                PICK_IMAGE_REQUEST = 1
                imagePicker(PICK_IMAGE_REQUEST)
            }
            second_pic -> {
                PICK_IMAGE_REQUEST = 2
                imagePicker(PICK_IMAGE_REQUEST)
            }
        }
    }

    override fun imagePicker(pickImageRequest: Int) {
        when (pickImageRequest) {
            1 -> {
                ImagePicker.create(this)
                        .returnMode(ReturnMode.ALL)
                        .single()
                        .start()
            }
            2 -> {
                ImagePicker.create(this)
                        .multi()
                        .limit(7)
                        .start()
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            val images: List<Image> = ImagePicker.getImages(data)
            val image: Image = ImagePicker.getFirstImageOrNull(data)
            if (resultCode == Activity.RESULT_OK && images[0].path != null) {
                lateinit var imageView: ImageView
                try {
                    Timber.tag("URI $PICK_IMAGE_REQUEST").i(images[0].path)
                    when (PICK_IMAGE_REQUEST) {
                        1 -> {
                            imageView = first_pic
                            imageView.setPadding(0, 0, 0, 0)
                            mPicturePathArray[0] = images[0].path
                        }
                        2 -> {
                            var i = 0
                            if (images.size > 1) {
                                add_image_fading_lo.visibility = View.VISIBLE
                                add_image_x_more.text = images.size.toString() + " more"
                            } else add_image_fading_lo.visibility = View.GONE
                            imageView = second_pic
                            imageView.setPadding(0, 0, 0, 0)
                            while (i < images.size) {
                                mPicturePathArray[i + 1] = images[i].path
                                i++
                            }
                        }
                    }
                    Glide.with(this)
                            .load(image.path)
                            .centerCrop()
                            .into(imageView)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

        }
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                val place = Autocomplete.getPlaceFromIntent(data!!)
                address_edit_txt.text = place.name
                address_txt_view.text = String().formatAddress(place.address!!)
                mAddress = place.address!!
                mMarker.title = place.name
                mMarker.position = place.latLng
                val locat = place.latLng?.latitude.toString() + "," + place.latLng?.longitude.toString()
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.latLng, 14f))
                mPresenter.nearbySchool(locat, "school", 500)
                mPresenter.nearbyPolice(locat, "police", 500)

            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // Handle the error.
                val status = Autocomplete.getStatusFromIntent(data!!)
                Timber.i(status.statusMessage)
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    /**
     * The Google Autocomplete intent method.
     */
    override fun autocompleteIntent() {
        val fields = listOf(Place.Field.LAT_LNG, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.ID)

        val intent = Autocomplete.IntentBuilder(
                AutocompleteActivityMode.OVERLAY, fields)
                .setTypeFilter(TypeFilter.ADDRESS)
                .setCountry("us")
                .build(applicationContext)
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
    }
    override fun onDestroy() {
        super.onDestroy()
        if (!mPickerDisposable.isDisposed) mPickerDisposable.dispose()
    }

    override fun onValueChange(picker: NumberPicker?, oldVal: Int, newVal: Int) {
        val i: Int = picker?.tag as Int

        when (i) {
            1 -> {
                desc_estate_type_txt.text = picker.displayedValues[newVal]
                mPickerArray[0] = newVal
                mType = newVal
            }
            2 -> {
                desc_estate_neighborhood_txt.text = picker.displayedValues[newVal]
                mPickerArray[1] = newVal
                mNeighborhood = newVal
            }
            3 -> {
                desc_estate_price_txt.text = mPriceResult
            }
            4 -> {
                fragment_desc_desc_txt.text = mDescResult
            }
            5 -> {
                desc_sqft_choice_txt.text = newVal.toString()
                mPickerArray[4] = newVal
                mSqft = newVal
            }
            6 -> {
                desc_rooms_choice_txt.text = newVal.toString()
                mPickerArray[5] = newVal
                mRooms = newVal
            }
            7 -> {
                desc_bathrooms_choice_txt.text = newVal.toString()
                mPickerArray[6] = newVal
                mBathrooms = newVal
            }
            8 -> {
                desc_bedrooms_choice_txt.text = newVal.toString()
                mPickerArray[7] = newVal
                mBedrooms = newVal
            }

            9 -> {
                desc_available_choice_txt.text = picker.displayedValues[newVal]
                mPickerArray[8] = newVal
                mAvailable = newVal
                when (newVal) {
                    0 -> {
                        add_estate_state_txt.text = getString(R.string.for_sale)
                        add_estate_state_txt.setTextColor(resources.getColor(R.color.quantum_lightgreen))
                        detail_estate_date_sold.text = ""
                    }
                    1 -> {
                        add_estate_state_txt.text = getString(R.string.sold)
                        add_estate_state_txt.setTextColor(resources.getColor(R.color.quantum_googred))
                        detail_estate_date_sold.text = Utils.todayDate
                        mSoldDate = Utils.todayDate
                    }
                }
            }

        }
    }

    override fun showNumberPicker(i: Int, mOldVal: Int?, string: String?) {
        val newFragment = NumberPickerDialog(i, mOldVal, string)
        newFragment.setValueChangeListener(this)
        newFragment.show(supportFragmentManager, "time picker")
    }

    override fun configureListeners() {
        add_estate_nav_view.setNavigationItemSelectedListener(this)

        desc_estate_type_img.visibility = ImageView.VISIBLE
        desc_neighbourhood_img.visibility = ImageView.VISIBLE
        desc_price_img.visibility = ImageView.VISIBLE
        desc_description_img.visibility = ImageView.VISIBLE
        desc_sqft_img.visibility = ImageView.VISIBLE
        desc_rooms_img.visibility = ImageView.VISIBLE
        desc_bathrooms_img.visibility = ImageView.VISIBLE
        desc_bedrooms_img.visibility = ImageView.VISIBLE
        desc_available_img.visibility = ImageView.VISIBLE
        address_edit_img.visibility = ImageView.VISIBLE
        add_estate_required.visibility = View.VISIBLE

        desc_estate_type_img.setOnClickListener(this)
        desc_estate_type_txt.setOnClickListener(this)
        desc_neighbourhood_img.setOnClickListener(this)
        desc_estate_neighborhood_txt.setOnClickListener(this)
        desc_price_img.setOnClickListener(this)
        desc_estate_price_txt.setOnClickListener(this)
        desc_description_img.setOnClickListener(this)
        fragment_desc_desc_txt.setOnClickListener(this)
        desc_sqft_img.setOnClickListener(this)
        surface_tv.setOnClickListener(this)
        desc_rooms_img.setOnClickListener(this)
        rooms_tv.setOnClickListener(this)
        desc_bathrooms_img.setOnClickListener(this)
        bathrooms_tv.setOnClickListener(this)
        desc_bedrooms_img.setOnClickListener(this)
        bedrooms_tv.setOnClickListener(this)
        desc_available_img.setOnClickListener(this)
        available_tv.setOnClickListener(this)
        first_pic.setOnClickListener(this)
        second_pic.setOnClickListener(this)
        address_search_layout.setOnClickListener(this)
        add_estate_drawer_layout.setOnClickListener { it.hideKeyboard() }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        //  Navigation Drawer item settings

        when (item.itemId) {
            R.id.drawer_first -> {
                val intent = Intent(baseContext, MainActivity::class.java)
                startActivity(intent)
                Timber.i("Click Main")
            }
            R.id.drawer_second -> {
                val intent = Intent(baseContext, SettingsActivity::class.java)
                startActivity(intent)
                Timber.i("Click Setting")
            }
            R.id.drawer_third -> {
                signOutUserFromFirebase()
                Timber.i("Logout")
            }
            else -> {
            }
        }
        return true
    }

    /**
     * On Toolbar item selected.
     */
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.add_estate_validate -> {
            checkIfNoNull()
            true
        }
        R.id.add_estate_cancel -> {
            Toast.makeText(this, getString(R.string.cancel), Toast.LENGTH_LONG).show()
            onBackPressed()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun checkIfNoNull() {
        when {
            mPicturePathArray[0] == "" -> Utils.snackBarPreset(findViewById(android.R.id.content), "You have to select a Main Picture")
            mType == -1 -> Utils.snackBarPreset(findViewById(android.R.id.content), "You have to select a Type")
            mNeighborhood == -1 -> Utils.snackBarPreset(findViewById(android.R.id.content), "You have to select a Neighborhood ")
            mPriceResult == "" -> Utils.snackBarPreset(findViewById(android.R.id.content), "You have to set a Price")
            mAddress == "" -> Utils.snackBarPreset(findViewById(android.R.id.content), "You have to enter an Address")
            else -> {
                addToDatabase()

                Utils.snackBarPreset(findViewById(android.R.id.content), getString(R.string.estate_saved))
                Executors.newSingleThreadScheduledExecutor().schedule({
                    val intent = Intent(baseContext, MainActivity::class.java)
                    startActivity(intent)
                }, 2, TimeUnit.SECONDS)
            }
        }
    }

    /**
     * Override on back pressed when drawer layout is open.
     */
    override fun onBackPressed() {
        if (add_estate_drawer_layout.isDrawerOpen(GravityCompat.START)) {
            add_estate_drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.add_estate_toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

}
