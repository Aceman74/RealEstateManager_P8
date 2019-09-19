/*
 * *
 *  * Created by Lionel Joffray on 19/09/19 21:47
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 19/09/19 18:59
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
import com.openclassrooms.realestatemanager.activities.search.SearchActivity
import com.openclassrooms.realestatemanager.activities.settings.SettingsActivity
import com.openclassrooms.realestatemanager.adapters.MainPagerAdapter
import com.openclassrooms.realestatemanager.fragments.list.ListFragment
import com.openclassrooms.realestatemanager.fragments.map.MapFragment
import com.openclassrooms.realestatemanager.utils.DepthPageTransformer
import com.openclassrooms.realestatemanager.utils.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

/**
 * Created by Lionel JOFFRAY - on 06/08/2019.
 *
 * This activity is the main view after login.
 * Extends:
 * @see BaseActivity for setting the view
 * @see MainContract contract for MVP
 * @NavigationView
 * @BottomNavigationView for navigation, disable on w720dp
 * @ViewPager for navigation, disable on w720dp
 *
 */
class MainActivity(override val activityLayout: Int = R.layout.activity_main) : BaseActivity(), MainContract.MainViewInterface, NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {

    private lateinit var mPager: ViewPager
    private lateinit var mPagerAdapter: MainPagerAdapter
    private val mPresenter = MainPresenter()
    /**
     * Setting the view, presenter, configure the fragment and viewpager, set listeners for bottom nav.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.attachView(this)
        configureAndShowMapFragment()
        configureView()
        configureItemListeners()
    }

    /**
     * Set the view.
     */
    override fun configureView() {
        setSupportActionBar(findViewById(R.id.main_toolbar))
        configureDrawerLayout(main_drawer_layout, main_toolbar)
        navigationDrawerHeader(main_activity_nav_view)
    }

    /**
     * Set the 2 fragments in one view if on screen more than 720dp,
     * or only list fragment if under.
     */
    override fun configureAndShowMapFragment() {
        if (main_activity_viewpager != null) {
            mPager = main_activity_viewpager
            main_activity_bottom_navigation.setOnNavigationItemSelectedListener(this)
            configureViewPager()
        } else {
            var mapFragment = supportFragmentManager.findFragmentById(R.id.map) as MapFragment?
            val listFragment = ListFragment()
            supportFragmentManager.beginTransaction()
                    .add(R.id.main_activity_listview, listFragment)
                    .commit()
            //A - We only add MapFragment in Tablet mode (If found main_activity_listview)
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
            R.id.bottom_main_list -> {
                mPager.currentItem = 0
                Timber.i(getString(R.string.list_click))
            }
            R.id.bottom_main_map -> {
                mPager.currentItem = 1
                Timber.i(getString(R.string.map_click))
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
        return true
    }

    /**
     * For Pager.
     */
    override fun onPageScrollStateChanged(state: Int) {
    }

    /**
     * For Pager.
     */
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    /**
     * For Pager.
     */
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
            Toast.makeText(this, getString(R.string.adding_estate), Toast.LENGTH_LONG).show()
            intent = Intent(this, AddEstateActivity::class.java)
            startActivity(intent)
            true
        }
        R.id.toolbar_search -> {
            Toast.makeText(this, getString(R.string.searching_estate), Toast.LENGTH_LONG).show()
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

    /**
     * Item listener for navigation.
     */
    override fun configureItemListeners() {
        main_activity_nav_view.setNavigationItemSelectedListener(this)
    }

    /**
     * Configure the ViewPager.
     * @see MainPagerAdapter
     */
    override fun configureViewPager() {
        mPagerAdapter = MainPagerAdapter(supportFragmentManager, applicationContext)
        mPager.adapter = mPagerAdapter
        mPager.setPageTransformer(true, DepthPageTransformer())
        mPager.addOnPageChangeListener(this)
    }
}
