package com.openclassrooms.realestatemanager.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.adapters.PageAdapter
import kotlinx.android.synthetic.main.activity_description.*

class DescriptionActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {
    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        return true
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)
        configureNavigationView()
        configureViewPager()
    }

    fun configureNavigationView() {
        description_nav_view.setNavigationItemSelectedListener(this)
        description_bottom_navigation.setOnNavigationItemSelectedListener(this)
    }
    fun configureViewPager() {
        description_viewpager.setAdapter(PageAdapter(supportFragmentManager, applicationContext))
    }
}
