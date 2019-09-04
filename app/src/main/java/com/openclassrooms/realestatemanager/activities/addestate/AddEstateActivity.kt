/*
 * *
 *  * Created by Lionel Joffray on 04/09/19 19:35
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 04/09/19 19:34
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
import com.openclassrooms.realestatemanager.Utils
import com.openclassrooms.realestatemanager.activities.estatedetail.EstateDetailActivity
import com.openclassrooms.realestatemanager.activities.login.AddEstateContract
import com.openclassrooms.realestatemanager.activities.main.MainActivity
import com.openclassrooms.realestatemanager.fragments.numberpicker.NumberPickerDialog
import com.openclassrooms.realestatemanager.injections.Injection
import com.openclassrooms.realestatemanager.models.Estate
import com.openclassrooms.realestatemanager.models.Picture
import com.openclassrooms.realestatemanager.models.User
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
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.nio.channels.FileChannel
import kotlin.math.absoluteValue


class AddEstateActivity(override val activityLayout: Int = R.layout.activity_add_estate) : BaseActivity(), AddEstateContract.AddEstateViewInterface, NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, NumberPicker.OnValueChangeListener, OnMapReadyCallback {


    private val AUTOCOMPLETE_REQUEST_CODE = 101
    var mPriceResut: String = ""
    var mDescResult: String = ""
    var mType = -1
    var mNeighborhood = -1
    var mSqft = 0
    var mRooms = 0
    var mBathrooms = 0
    var mBedrooms = 0
    var mAvailable = 0
    var mdateCreate = ""
    var mAddress = ""
    var mSoldDate: String? = null
    var mPickerArray = IntArray(9)
    var mPicturePathArray = arrayListOf("", "", "", "", "", "", "", "")
    private lateinit var mMap: GoogleMap
    lateinit var marker: Marker

    private lateinit var pickerDisposable: Disposable
    lateinit var estateViewModel: EstateViewModel
    lateinit var userViewModel: UserViewModel
    lateinit var pictureViewModel: PictureViewModel
    lateinit var mEstatePhotosDir: File
    var eId: Long = 0
    var intentEId: Long = -1

    var PICK_IMAGE_REQUEST = 50

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(findViewById(R.id.add_estate_toolbar))
        Places.initialize(applicationContext, BuildConfig.google_maps_key)
        Places.createClient(this)
        configureDrawerLayout(add_estate_drawer_layout, add_estate_toolbar)
        configureListeners()
        createPhotosFolder()
        desc_date_added_choice_txt.text = Utils.todayDate
        desc_agent_choice_txt.text = currentUser?.displayName.toString()
        configureViewModel()
        configureMaps()
        intentEId = intent.getIntExtra("estateId", -1).toLong()
        editIntent()
        address_search_layout.setOnClickListener {
            autocompleteIntent()
        }

        pickerDisposable = RxBus.listen(RxEvent.PickerDescEvent::class.java).subscribe {
            mDescResult = it.desc
        }
        pickerDisposable = RxBus.listen(RxEvent.PickerPriceEvent::class.java).subscribe {
            mPriceResut = it.price
        }
    }

    private fun editIntent() {
        if (intentEId.toInt() != -1) {
            estateViewModel.getEstatePictures(intentEId).observe(this, Observer {
                val result = it[0]
                desc_estate_type_txt.text = Utils.ListOfString.listOfType()[result.estate.type]
                mPickerArray[0] = result.estate.type
                mType = result.estate.type
                desc_estate_neighborhood_txt.text = Utils.ListOfString.listOfNeighborhood()[result.estate.neighborhood]
                mPickerArray[1] = result.estate.neighborhood
                mNeighborhood = result.estate.neighborhood
                desc_estate_price_txt.text = result.estate.price
                mPriceResut = result.estate.price
                fragment_desc_desc_txt.text = result.estate.description
                mDescResult = result.estate.description
                desc_sqft_choice_txt.text = result.estate.sqft.toString()
                mPickerArray[4] = result.estate.sqft
                mSqft = result.estate.sqft
                desc_rooms_choice_txt.text = result.estate.rooms.toString()
                mPickerArray[5] = result.estate.rooms
                mRooms = result.estate.rooms
                desc_bedrooms_choice_txt.text = result.estate.bedrooms.toString()
                mPickerArray[6] = result.estate.bedrooms
                mBedrooms = result.estate.bedrooms
                desc_bathrooms_choice_txt.text = result.estate.bathrooms.toString()
                mPickerArray[7] = result.estate.bathrooms
                mBathrooms = result.estate.bathrooms
                desc_agent_choice_txt.text = result.estate.agent
                desc_available_choice_txt.text = Utils.ListOfString.listOfAvailable()[result.estate.available]
                mPickerArray[8] = result.estate.available
                mAvailable = result.estate.available
                desc_date_added_choice_txt.text = result.estate.addDate
                mdateCreate = result.estate.addDate
                desc_last_mod_date_choice_txt.text = Utils.todayDate
                address_txt_view.text = Utils.formatAddress(result.estate.fullAddress)
                mAddress = result.estate.fullAddress
                marker.position = LatLng(result.estate.latitude!!, result.estate.longitude!!)
                marker.title = "That's Here !"
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

    private fun configureMaps() {
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
        marker = mMap.addMarker(MarkerOptions().position(newYork)
                .title("Marker in NewYork"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(newYork))
        mMap.cameraPosition.zoom.absoluteValue
    }

    /**
     * The Google Autocomplete intent method.
     */
    fun autocompleteIntent() {
        val fields = listOf(Place.Field.LAT_LNG, Place.Field.NAME, Place.Field.ADDRESS)

        val intent = Autocomplete.IntentBuilder(
                AutocompleteActivityMode.OVERLAY, fields)
                .setTypeFilter(TypeFilter.ADDRESS)
                .setCountry("us")
                .build(applicationContext)
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)

    }


    private fun createPhotosFolder() {
        mEstatePhotosDir = File(applicationInfo.dataDir + "/files/", "estate_photos")

        if (mEstatePhotosDir.mkdir()) {
            Timber.tag("Folder FUN").d("Directory created")
        } else {
            Timber.tag("Folder FUN").d("Directory is not created")
        }
    }

    private fun configureViewModel() {
        val mViewModelFactory = Injection.provideViewModelFactory(this)
        this.estateViewModel = ViewModelProviders.of(this, mViewModelFactory).get(EstateViewModel::class.java)
        this.userViewModel = ViewModelProviders.of(this, mViewModelFactory).get(UserViewModel::class.java)
        this.pictureViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PictureViewModel::class.java)
    }

    private fun addToDatabase() {
        if (intentEId.toInt() == -1) {
            val user = User(currentUser!!.uid, currentUser!!.displayName.toString(), currentUser!!.email.toString(), currentUser!!.photoUrl.toString(), "TODAY")
            this.userViewModel.createUser(user)
            val estate = Estate(null, currentUser!!.uid, mType, mNeighborhood, mPriceResut, mDescResult, mSqft, mRooms, mBathrooms, mBedrooms, mAvailable, currentUser!!.displayName!!, Utils.todayDate, null, mSoldDate, marker.position.latitude, marker.position.longitude, mAddress)
            this.estateViewModel.createEstate(estate)
            this.estateViewModel.allEstate.observe(this, Observer {
                eId = it.lastIndex.toLong() + 1
                savePictureToCustomPath(eId)
            })
        } else {
            val estate = Estate(intentEId, currentUser!!.uid, mType, mNeighborhood, mPriceResut, mDescResult, mSqft, mRooms, mBathrooms, mBedrooms, mAvailable, currentUser!!.displayName!!, mdateCreate, Utils.todayDate, mSoldDate, marker.position.latitude, marker.position.longitude, mAddress)
            this.estateViewModel.createEstate(estate)
            savePictureToCustomPath(intentEId)
        }

    }

    override fun onClick(v: View?) {
        when (v) {
            desc_estate_type_img, desc_estate_type_txt -> {
                showNumberPicker(1, mPickerArray[0])
                Timber.i("Click Type")
            }
            desc_neighbourhood_img, desc_estate_neighborhood_txt -> {
                showNumberPicker(2, mPickerArray[1])
                Timber.i("Click Neighbourhood")
            }
            desc_price_img, desc_estate_price_txt -> {
                RxBus.publish(RxEvent.PickerPriceEvent(mPriceResut))
                showNumberPicker(3, mPickerArray[0])
                Timber.i("Click Price")
            }
            desc_description_img, desc_description_layout -> {
                RxBus.publish(RxEvent.PickerDescEvent(mDescResult))
                showNumberPicker(4, mPickerArray[0])
                Timber.i("Click Description")
            }
            desc_sqft_img, desc_sqft_layout -> {
                showNumberPicker(5, mPickerArray[4])
                Timber.i("Click Sqft")
            }
            desc_rooms_img, desc_rooms_layout -> {
                showNumberPicker(6, mPickerArray[5])
                Timber.i("Click Rooms")
            }
            desc_bathrooms_img, desc_bathrooms_layout -> {
                showNumberPicker(7, mPickerArray[6])
                Timber.i("Click Bathrooms")
            }
            desc_bedrooms_img, desc_bedrooms_layout -> {
                showNumberPicker(8, mPickerArray[7])
                Timber.i("Click Bedrooms")
            }
            desc_available_img, desc_available_layout -> {
                showNumberPicker(9, mPickerArray[8])
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


    fun imagePicker(pickImageRequest: Int) {
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
                address_txt_view.text = Utils.formatAddress(place.address!!)
                mAddress = place.address!!
                marker.title = place.name
                marker.position = place.latLng
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.latLng, 14f))
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // Handle the error.
                val status = Autocomplete.getStatusFromIntent(data!!)
                Timber.i(status.statusMessage)
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    fun savePictureToCustomPath(eId: Long) {
        var i = 0
        lateinit var fileDest: File
        while (i < mPicturePathArray.size) {
            if (mPicturePathArray[i] != "") {
                val file = File(mPicturePathArray[i])
                val pictureName = Utils.custromTimeStamp() + "_" + currentUser!!.displayName + "_" + "$i"
                if (i == 0) {
                    fileDest = File(mEstatePhotosDir.path + "/" + pictureName + "_main.jpg")
                } else {
                    fileDest = File(mEstatePhotosDir.path + "/" + pictureName + ".jpg")
                }
                copyFile(file, fileDest)
                this.pictureViewModel.createPicture(Picture(null, eId, pictureName, fileDest.toString()))

            }
            i++
        }
    }

    /**
     * copy contents from source file to mappPath file
     *
     * @param sourceFilePath  Source file path address
     * @param destinationFilePath Destination file path address
     */
    private fun copyFile(sourceFilePath: File, destinationFilePath: File) {

        try {

            if (!sourceFilePath.exists()) {
                return
            }

            val source: FileChannel = FileInputStream(sourceFilePath).channel
            val destination: FileChannel = FileOutputStream(destinationFilePath).channel
            destination.transferFrom(source, 0, source.size())
            source.close()
            destination.close()

        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        if (!pickerDisposable.isDisposed) pickerDisposable.dispose()
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
                desc_estate_price_txt.text = mPriceResut
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

    fun showNumberPicker(i: Int, mOldVal: Int?) {
        val newFragment = NumberPickerDialog(i, mOldVal)
        newFragment.setValueChangeListener(this)
        newFragment.show(supportFragmentManager, "time picker")
    }

    private fun configureListeners() {
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
        desc_description_layout.setOnClickListener(this)
        desc_sqft_img.setOnClickListener(this)
        desc_sqft_layout.setOnClickListener(this)
        desc_rooms_img.setOnClickListener(this)
        desc_rooms_layout.setOnClickListener(this)
        desc_bathrooms_img.setOnClickListener(this)
        desc_bathrooms_layout.setOnClickListener(this)
        desc_bedrooms_img.setOnClickListener(this)
        desc_bedrooms_layout.setOnClickListener(this)
        desc_available_img.setOnClickListener(this)
        desc_available_layout.setOnClickListener(this)
        first_pic.setOnClickListener(this)
        second_pic.setOnClickListener(this)
        address_search_layout.setOnClickListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        //  Navigation Drawer item settings

        when (item.itemId) {
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
     * On Toolbar item selected.
     */
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.add_estate_validate -> {
            checkIfNoNull()
            true
        }
        R.id.add_estate_cancel -> {
            Toast.makeText(this, "cancel", Toast.LENGTH_LONG).show()
            onBackPressed()
            true
        }
        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    private fun checkIfNoNull() {
        when {
            mPicturePathArray[0] == "" -> Utils.snackBarPreset(findViewById(android.R.id.content), "You have to select a Main Picture")
            mType == -1 -> Utils.snackBarPreset(findViewById(android.R.id.content), "You have to select a Type")
            mNeighborhood == -1 -> Utils.snackBarPreset(findViewById(android.R.id.content), "You have to select a Neighborhood ")
            mPriceResut == "" -> Utils.snackBarPreset(findViewById(android.R.id.content), "You have to set a Price")
            mAddress == "" -> Utils.snackBarPreset(findViewById(android.R.id.content), "You have to enter an Address")
            else -> {
                addToDatabase()
                Utils.snackBarPreset(findViewById(android.R.id.content), "Estate Saved")
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
