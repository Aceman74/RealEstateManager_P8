package com.openclassrooms.realestatemanager.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.v4.view.ViewPager
import android.view.MenuItem
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.adapters.PageAdapter
import kotlinx.android.synthetic.main.activity_description.*
import timber.log.Timber

class DescriptionActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var pager:ViewPager
    private lateinit var pagerAdapter: PageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)
        pager = description_viewpager
        configureNavigationView()
        configureViewPager()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        //  Navigation Drawer item settings
        val id = item.itemId

        when (id) {
            R.id.drawer_first -> {
                Timber.i("Click first")
                /**
                 *
                val lunch = Intent(this, PlacesDetailActivity::class.java)
                mIntent = getString(R.string.lunch)
                lunch.putExtra(getString(R.string.detail_intent), mIntent)
                this.startActivity(lunch)
                Timber.i("Click Your Lunch")
                 */
            }
            R.id.drawer_second -> {
                Timber.i("Click second")
            }
            R.id.drawer_third -> {
                Timber.i("Click third")
            }
            R.id.bottom_description -> {
                pager.currentItem = 0
                Timber.i("Click Description")
            }
            R.id.bottom_location -> {
                pager.currentItem = 1
                Timber.i("Click Location")
            }
            else -> {
            }
        }
        return true
    }

    fun configureNavigationView() {
        description_nav_view.setNavigationItemSelectedListener(this)
        description_bottom_navigation.setOnNavigationItemSelectedListener(this)
    }
    fun configureViewPager() {
        pagerAdapter = PageAdapter(supportFragmentManager,applicationContext)
        pager.adapter = pagerAdapter
    }
}
