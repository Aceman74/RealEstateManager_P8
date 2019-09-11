/*
 * *
 *  * Created by Lionel Joffray on 11/09/19 20:37
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 11/09/19 14:23
 *
 */

package com.openclassrooms.realestatemanager.activities.settings

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.activities.login.LoginActivity
import com.openclassrooms.realestatemanager.activities.main.MainActivity
import com.openclassrooms.realestatemanager.utils.base.BaseActivity
import kotlinx.android.synthetic.main.activity_settings.*
import timber.log.Timber

class SettingsActivity(override val activityLayout: Int = R.layout.activity_settings) : BaseActivity(), SettingsContract.SettingsViewInterface, NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    var mDevise: String = "$"
    private val mPresenter = SettingsPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.attachView(this)
        configureView()
        configureListeners()
        loadSharedPref()
    }

    override fun configureView() {
        setSupportActionBar(findViewById(R.id.settings_tb))
        configureDrawerLayout(settings_dl, settings_tb)
        navigationDrawerHeader(settings_activity_nav_view)
    }

    override fun loadSharedPref() {
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
            theme_0_btn -> {
                val sharedPref = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE)
                val theme = sharedPref.edit()
                theme.putInt("actual_theme", 0)
                theme.apply()
            }
            theme_1_btn -> {
                val sharedPref = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE)
                val theme = sharedPref.edit()
                theme.putInt("actual_theme", 1)
                theme.apply()
            }
            theme_2_btn -> {
                val sharedPref = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE)
                val theme = sharedPref.edit()
                theme.putInt("actual_theme", 2)
                theme.apply()
            }
            theme_3_btn -> {
                val sharedPref = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE)
                val theme = sharedPref.edit()
                theme.putInt("actual_theme", 3)
                theme.apply()
            }
        }
        applyNewTheme()
    }

    override fun applyNewTheme() {
        finish()
        val intent = Intent.makeMainActivity(ComponentName(
                this@SettingsActivity, LoginActivity::class.java))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
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

    override fun configureListeners() {
        settings_activity_nav_view.setNavigationItemSelectedListener(this)
        dollars_btn.setOnClickListener(this)
        euros_btn.setOnClickListener(this)
        theme_0_btn.setOnClickListener(this)
        theme_1_btn.setOnClickListener(this)
        theme_2_btn.setOnClickListener(this)
        theme_3_btn.setOnClickListener(this)
    }
}
