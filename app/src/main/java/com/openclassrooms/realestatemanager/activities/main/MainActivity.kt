/*
 * *
 *  * Created by Lionel Joffray on 09/09/19 20:10
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 09/09/19 20:07
 *
 */

package com.openclassrooms.realestatemanager.activities.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.activities.SettingsActivity
import com.openclassrooms.realestatemanager.activities.addestate.AddEstateActivity
import com.openclassrooms.realestatemanager.activities.login.MainContract
import com.openclassrooms.realestatemanager.activities.search.SearchActivity
import com.openclassrooms.realestatemanager.adapters.MainPagerAdapter
import com.openclassrooms.realestatemanager.utils.DepthPageTransformer
import com.openclassrooms.realestatemanager.utils.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity(override val activityLayout: Int = R.layout.activity_main) : BaseActivity(), MainContract.MainViewInterface, NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {


    private lateinit var pager: ViewPager
    private lateinit var mPagerAdapter: MainPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pager = main_activity_viewpager
        setSupportActionBar(findViewById(R.id.main_toolbar))
        configureDrawerLayout(main_drawer_layout, main_toolbar)
        configureItemListeners()
        configureViewPager()
        navigationDrawerHeader(main_activity_nav_view)
    }

    /**
     * On navigation drawer or bottom navigation itemSelected.
     */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        //  Navigation Drawer item settings
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
                pager.currentItem = 0
                Timber.i("Click List")
            }
            R.id.bottom_main_map -> {
                pager.currentItem = 1
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
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
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

    fun configureItemListeners() {
        main_activity_nav_view.setNavigationItemSelectedListener(this)
        main_activity_bottom_navigation.setOnNavigationItemSelectedListener(this)
    }

    fun configureViewPager() {
        mPagerAdapter = MainPagerAdapter(supportFragmentManager, applicationContext)
        pager.adapter = mPagerAdapter
        pager.setPageTransformer(true, DepthPageTransformer())
        pager.addOnPageChangeListener(this)
    }
}
