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
import com.openclassrooms.realestatemanager.activities.addestate.AddEstateActivity
import com.openclassrooms.realestatemanager.activities.estate.EstateDetailActivity
import com.openclassrooms.realestatemanager.activities.login.MainContract
import com.openclassrooms.realestatemanager.adapters.MainPagerAdapter
import com.openclassrooms.realestatemanager.utils.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity(override val activityLayout: Int = R.layout.activity_main) : BaseActivity(), MainContract.MainViewInterface, NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var pager: ViewPager
    private lateinit var mPagerAdapter: MainPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pager = main_activity_viewpager
        setSupportActionBar(findViewById(R.id.main_toolbar))
        configureDrawerLayout(main_drawer_layout, main_toolbar)
        configureItemListeners()
        configureViewPager()
    }

    /**
     * On navigation drawer or bottom navigation itemSelected.
     */
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
            Toast.makeText(this, "Add action", Toast.LENGTH_LONG).show()
            intent = Intent(this, AddEstateActivity::class.java)
            startActivity(intent)
            true
        }
        R.id.toolbar_search -> {
            Toast.makeText(this, "Search action", Toast.LENGTH_LONG).show()
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
    }
}
