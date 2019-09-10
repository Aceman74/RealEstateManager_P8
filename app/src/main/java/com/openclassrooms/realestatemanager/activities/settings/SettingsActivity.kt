/*
 * *
 *  * Created by Lionel Joffray on 10/09/19 20:32
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 10/09/19 20:31
 *
 */

package com.openclassrooms.realestatemanager.activities.settings

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.activities.main.MainActivity
import com.openclassrooms.realestatemanager.utils.base.BaseActivity
import kotlinx.android.synthetic.main.activity_settings.*
import timber.log.Timber

class SettingsActivity(override val activityLayout: Int = R.layout.activity_settings) : BaseActivity(), NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    var mDevise: String = "$"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(findViewById(R.id.settings_tb))
        configureDrawerLayout(settings_dl, settings_tb)
        navigationDrawerHeader(settings_activity_nav_view)
        configureListeners()
        loadSharedPref()

    }

    private fun loadSharedPref() {
        val shared = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE)
        mDevise = shared.getString("actual_devise", "$")!!
        when (mDevise) {
            "$" -> {
                dollars_btn.setBackgroundResource(R.drawable.button_style_round)
                euros_btn.setBackgroundResource(R.drawable.button_style_round_primary)
            }
            "€" -> {
                euros_btn.setBackgroundResource(R.drawable.button_style_round)
                dollars_btn.setBackgroundResource(R.drawable.button_style_round_primary)
            }
        }
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
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
        return true
    }

    override fun onClick(v: View?) {
        when (v) {
            dollars_btn -> {
                mDevise = "$"
                val sharedPref = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE)
                val devise = sharedPref.edit()
                devise.putString("actual_devise", mDevise)
                devise.apply()
                loadSharedPref()
            }
            euros_btn -> {
                mDevise = "€"
                val sharedPref = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE)
                val devise = sharedPref.edit()
                devise.putString("actual_devise", mDevise)
                devise.apply()
                loadSharedPref()
            }
        }
    }

    /**
     * Override on back pressed when drawer layout is open.
     */
    override fun onBackPressed() {
        if (settings_dl.isDrawerOpen(GravityCompat.START)) {
            settings_dl.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    fun configureListeners() {
        settings_activity_nav_view.setNavigationItemSelectedListener(this)
        dollars_btn.setOnClickListener(this)
        euros_btn.setOnClickListener(this)
    }
}
