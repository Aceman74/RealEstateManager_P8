/*
 * *
 *  * Created by Lionel Joffray on 18/09/19 23:09
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 18/09/19 15:38
 *
 */

package com.openclassrooms.realestatemanager.activities.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.activities.addestate.AddEstateActivity
import com.openclassrooms.realestatemanager.activities.login.MainContract
import com.openclassrooms.realestatemanager.activities.search.SearchActivity
import com.openclassrooms.realestatemanager.activities.settings.SettingsActivity
import com.openclassrooms.realestatemanager.adapters.MainPagerAdapter
import com.openclassrooms.realestatemanager.fragments.list.ListFragment
import com.openclassrooms.realestatemanager.fragments.map.MapFragment
import com.openclassrooms.realestatemanager.utils.DepthPageTransformer
import com.openclassrooms.realestatemanager.utils.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber


class MainActivity(override val activityLayout: Int = R.layout.activity_main) : BaseActivity(), MainContract.MainViewInterface, NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {


    private lateinit var mPager: ViewPager
    private lateinit var mPagerAdapter: MainPagerAdapter
    private val mPresenter = MainPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.attachView(this)
        configureAndShowMapFragment()
        configureView()
        configureItemListeners()
    }

    override fun configureView() {
        setSupportActionBar(findViewById(R.id.main_toolbar))
        configureDrawerLayout(main_drawer_layout, main_toolbar)
        navigationDrawerHeader(main_activity_nav_view)
    }

    fun configureAndShowMapFragment() {
        if (main_activity_viewpager != null) {
            mPager = main_activity_viewpager
            main_activity_bottom_navigation.setOnNavigationItemSelectedListener(this)
            configureViewPager()
        } else {
            var listFragment = supportFragmentManager.findFragmentById(R.id.main_activity_listview) as ListFragment?
            var mapFragment = supportFragmentManager.findFragmentById(R.id.map) as MapFragment?
            listFragment = ListFragment()
            supportFragmentManager.beginTransaction()
                    .add(R.id.main_activity_listview, listFragment)
                    .commit()
            //A - We only add DetailFragment in Tablet mode (If found frame_layout_detail)
            if (mapFragment == null && findViewById<View>(R.id.main_activity_map) != null) {
                mapFragment = MapFragment()
                supportFragmentManager.beginTransaction()
                        .add(R.id.main_activity_map, mapFragment)
                        .commit()
            }

        }
    }

    /**
     * On navigation drawer or bottom navigation itemSelected.
     */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when (id) {
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
            R.id.bottom_main_list -> {
                mPager.currentItem = 0
                Timber.i("Click List")
            }
            R.id.bottom_main_map -> {
                mPager.currentItem = 1
                Timber.i("Click Map")
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
        return true
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        when (position) {
            0 -> main_activity_bottom_navigation.selectedItemId = R.id.bottom_main_list
            1 -> main_activity_bottom_navigation.selectedItemId = R.id.bottom_main_map
        }
    }

    /**
     * Override on back pressed when drawer layout is open.
     */
    override fun onBackPressed() {
        if (main_drawer_layout.isDrawerOpen(GravityCompat.START)) {
            main_drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    /**
     * On Toolbar item selected.
     */
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.toolbar_add -> {
            Toast.makeText(this, "Add Estate", Toast.LENGTH_LONG).show()
            intent = Intent(this, AddEstateActivity::class.java)
            startActivity(intent)
            true
        }
        R.id.toolbar_search -> {
            Toast.makeText(this, "Search Estate", Toast.LENGTH_LONG).show()
            intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
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
     * Inflate the menu; this adds items to the action bar if it is present.
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun configureItemListeners() {
        main_activity_nav_view.setNavigationItemSelectedListener(this)
    }

    override fun configureViewPager() {
        mPagerAdapter = MainPagerAdapter(supportFragmentManager, applicationContext)
        mPager.adapter = mPagerAdapter
        mPager.setPageTransformer(true, DepthPageTransformer())
        mPager.addOnPageChangeListener(this)
    }
}
