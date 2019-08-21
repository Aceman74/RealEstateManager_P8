package com.openclassrooms.realestatemanager.activities.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.activities.login.MainContract
import com.openclassrooms.realestatemanager.adapters.MainPageAdapter
import com.openclassrooms.realestatemanager.adapters.PageAdapter
import com.openclassrooms.realestatemanager.utils.base.BaseActivity
import kotlinx.android.synthetic.main.activity_description.*
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity(override val activityLayout: Int = R.layout.activity_main) : BaseActivity(), MainContract.MainViewInterface, NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var pager: ViewPager
    private lateinit var pagerAdapter: MainPageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pager = main_activity_viewpager
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
            R.id.bottom_main_list -> {
                pager.currentItem = 0
                Timber.i("Click List")
            }
            R.id.bottom_main_map -> {
                pager.currentItem = 1
                Timber.i("Click Map")
            }
            else -> {
            }
        }
        return true
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }
    fun configureNavigationView() {
        main_activity_nav_view.setNavigationItemSelectedListener(this)
        main_activity_bottom_navigation.setOnNavigationItemSelectedListener(this)
    }
    fun configureViewPager() {
        pagerAdapter = MainPageAdapter(supportFragmentManager,applicationContext)
        pager.adapter = pagerAdapter
    }
}
