package com.openclassrooms.realestatemanager.activities.addestate

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.activities.estate.EstateDetailActivity
import com.openclassrooms.realestatemanager.activities.login.AddEstateContract
import com.openclassrooms.realestatemanager.activities.main.MainActivity
import com.openclassrooms.realestatemanager.utils.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_estate.*
import kotlinx.android.synthetic.main.fragment_description.*
import timber.log.Timber

class AddEstateActivity(override val activityLayout: Int = R.layout.activity_add_estate) : BaseActivity(), AddEstateContract.AddEstateViewInterface, NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(findViewById(R.id.add_estate_toolbar))
        configureDrawerLayout(add_estate_drawer_layout, add_estate_toolbar)
        configureItemListeners()
        configureEditView()
    }

    override fun onClick(v: View?) {
        when (v) {
            desc_estate_type_img ->
                Timber.i("Click Type")
            desc_neighbourhood_img ->
                Timber.i("Click Neighbourhood")
            desc_price_img ->
                Timber.i("Click Price")
            desc_description_img ->
                Timber.i("Click Description")
            desc_sqft_img ->
                Timber.i("Click Sqft")
            desc_rooms_img ->
                Timber.i("Click Rooms")
            desc_bathrooms_img ->
                Timber.i("Click Bathrooms")
            desc_bedrooms_img ->
                Timber.i("Click Bedrooms")
            desc_avaiable_img ->
                Timber.i("Click Avaiable")


        }
    }

    private fun configureEditView() {
        desc_estate_type_img.visibility = ImageView.VISIBLE
        desc_neighbourhood_img.visibility = ImageView.VISIBLE
        desc_price_img.visibility = ImageView.VISIBLE
        desc_description_img.visibility = ImageView.VISIBLE
        desc_sqft_img.visibility = ImageView.VISIBLE
        desc_rooms_img.visibility = ImageView.VISIBLE
        desc_bathrooms_img.visibility = ImageView.VISIBLE
        desc_bedrooms_img.visibility = ImageView.VISIBLE
        desc_avaiable_img.visibility = ImageView.VISIBLE

        desc_estate_type_img.setOnClickListener(this)
        desc_neighbourhood_img.setOnClickListener(this)
        desc_price_img.setOnClickListener(this)
        desc_description_img.setOnClickListener(this)
        desc_sqft_img.setOnClickListener(this)
        desc_rooms_img.setOnClickListener(this)
        desc_bathrooms_img.setOnClickListener(this)
        desc_bedrooms_img.setOnClickListener(this)
        desc_avaiable_img.setOnClickListener(this)
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

    fun configureItemListeners() {
        add_estate_nav_view.setNavigationItemSelectedListener(this)
    }
}
