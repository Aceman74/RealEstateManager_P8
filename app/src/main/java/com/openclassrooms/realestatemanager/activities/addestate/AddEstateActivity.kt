/*
 * *
 *  * Created by Lionel Joffray on 29/08/19 22:26
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 29/08/19 22:26
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
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.Utils
import com.openclassrooms.realestatemanager.activities.estate.EstateDetailActivity
import com.openclassrooms.realestatemanager.activities.login.AddEstateContract
import com.openclassrooms.realestatemanager.activities.main.MainActivity
import com.openclassrooms.realestatemanager.activities.viewmodels.EstateViewModel
import com.openclassrooms.realestatemanager.fragments.numberpicker.NumberPickerDialog
import com.openclassrooms.realestatemanager.injections.Injection
import com.openclassrooms.realestatemanager.models.Estate
import com.openclassrooms.realestatemanager.models.User
import com.openclassrooms.realestatemanager.utils.base.BaseActivity
import com.openclassrooms.realestatemanager.utils.rxbus.RxBus
import com.openclassrooms.realestatemanager.utils.rxbus.RxEvent
import com.openclassrooms.realpicturemanager.activities.viewmodels.PictureViewModel
import com.openclassrooms.realusermanager.activities.viewmodels.UserViewModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_add_estate.*
import kotlinx.android.synthetic.main.fragment_add_images.*
import kotlinx.android.synthetic.main.fragment_description.*
import timber.log.Timber
import java.io.IOException


class AddEstateActivity(override val activityLayout: Int = R.layout.activity_add_estate) : BaseActivity(), AddEstateContract.AddEstateViewInterface, NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, NumberPicker.OnValueChangeListener {
    var mPriceResut: String = ""
    var mDescResult: String = ""
    var mType = 0
    var mNeighborhood = 0
    var mSqft = 0
    var mRooms = 0
    var mBathrooms = 0
    var mBedrooms = 0
    var mAvailable = 0
    var mIntResult: Int = 0
    var mPickerArray = IntArray(9)
    private lateinit var pickerDisposable: Disposable
    lateinit var estateViewModel: EstateViewModel
    lateinit var userViewModel: UserViewModel
    lateinit var pictureViewModel: PictureViewModel

    var PICK_IMAGE_REQUEST = 50

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(findViewById(R.id.add_estate_toolbar))
        configureDrawerLayout(add_estate_drawer_layout, add_estate_toolbar)
        configureListeners()
        desc_date_added_choice_txt.text = Utils.todayDate
        desc_agent_choice_txt.text = currentUser?.displayName.toString()
        configureViewModel()

        pickerDisposable = RxBus.listen(RxEvent.PickerDescEvent::class.java).subscribe {
            mDescResult = it.desc
            mIntResult = it.nbr
        }
        pickerDisposable = RxBus.listen(RxEvent.PickerPriceEvent::class.java).subscribe {
            mPriceResut = it.price
            mIntResult = it.nbr
        }
    }

    private fun configureViewModel() {
        val mViewModelFactory = Injection.provideViewModelFactory(this)
        this.estateViewModel = ViewModelProviders.of(this, mViewModelFactory).get(EstateViewModel::class.java)
        this.userViewModel = ViewModelProviders.of(this, mViewModelFactory).get(UserViewModel::class.java)
        this.pictureViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PictureViewModel::class.java)
    }

    private fun addToDatabase() {
        var user = User(currentUser!!.uid, currentUser!!.displayName.toString(), currentUser!!.email.toString(), currentUser!!.photoUrl.toString(), "TODAY")
        this.userViewModel.createUser(user)
        var estate = Estate(1, currentUser!!.uid, mType, mNeighborhood, mPriceResut, mDescResult, mSqft, mRooms, mBathrooms, mBedrooms, mAvailable)
        this.estateViewModel.createEstate(estate)
        onBackPressed()
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
                showNumberPicker(3, mPickerArray[0])
                Timber.i("Click Price")
            }
            desc_description_img, desc_description_layout -> {
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
                openGallery()
            }
            second_pic -> {
                PICK_IMAGE_REQUEST = 2
                openGallery()
            }
            third_pic -> {
                PICK_IMAGE_REQUEST = 3
                openGallery()
            }
            fourth_pic -> {
                PICK_IMAGE_REQUEST = 4
                openGallery()
            }
        }
    }


    fun openGallery() {
        val intent = Intent()
// Show only images, no videos or anything else
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
// Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            lateinit var imageView: ImageView
            val uri = data.data

            try {
                Timber.tag("URI $PICK_IMAGE_REQUEST").i("$uri")
                when (PICK_IMAGE_REQUEST) {
                    1 -> imageView = first_pic
                    2 -> imageView = second_pic
                    3 -> imageView = third_pic
                    4 -> imageView = fourth_pic
                }
                Glide.with(this)
                        .load(uri)
                        .centerCrop()
                        .into(imageView)
            } catch (e: IOException) {
                e.printStackTrace()
            }
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
                        add_estate_state_txt.text = "For Sale"
                        add_estate_state_txt.setTextColor(resources.getColor(R.color.quantum_lightgreen))
                        detail_estate_date_sold.text = ""
                    }
                    1 -> {
                        add_estate_state_txt.text = "Sold"
                        add_estate_state_txt.setTextColor(resources.getColor(R.color.quantum_googred))
                        detail_estate_date_sold.text = Utils.todayDate
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
        third_pic.setOnClickListener(this)
        fourth_pic.setOnClickListener(this)
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
            Toast.makeText(this, "Add action", Toast.LENGTH_LONG).show()
            addToDatabase()
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
