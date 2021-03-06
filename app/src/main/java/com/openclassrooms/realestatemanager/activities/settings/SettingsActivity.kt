/*
 * *
 *  * Created by Lionel Joffray on 23/09/19 21:08
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 23/09/19 21:08
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
import com.openclassrooms.realestatemanager.extensions.hideKeyboard
import com.openclassrooms.realestatemanager.utils.Utils
import com.openclassrooms.realestatemanager.utils.base.BaseActivity
import kotlinx.android.synthetic.main.activity_settings.*
import timber.log.Timber
import kotlin.math.roundToInt

/**
 * Created by Lionel JOFFRAY - on 06/08/2019.
 *
 * This activity is the Settings, where user can change devise, theme and simulate a loan.
 * Extends:
 * @see BaseActivity for setting the view
 * @see SettingsContract contract for MVP
 * @View
 * @NavigationView
 *
 */
class SettingsActivity(override val activityLayout: Int = R.layout.activity_settings) : BaseActivity(), SettingsContract.SettingsViewInterface, NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    var mDevise: String = "$"
    private val mPresenter = SettingsPresenter()
    /**
     * Set the view, presenter, listeners and load devise.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.attachView(this)
        configureView()
        configureListeners()
        loadSharedPref()
        loanSimulation()
    }

    /**
     * Init the view with toolbar, drawer etc.
     */
    override fun configureView() {
        setSupportActionBar(findViewById(R.id.settings_tb))
        configureDrawerLayout(settings_dl, settings_tb)
        navigationDrawerHeader(settings_activity_nav_view)
    }

    /**
     * Load the devise.
     */
    override fun loadSharedPref() {
        val shared = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE)
        mDevise = shared.getString("actual_devise", "$")!!
        when (mDevise) {
            "$" -> {
                dollars_btn.setBackgroundResource(R.drawable.button_style_round)
                amount_money_tv.text = "$"
                interest_money_tv.text = "$"
                monthly_money_tv.text = "$"
            }
            "€" -> {
                euros_btn.setBackgroundResource(R.drawable.button_style_round)
                amount_money_tv.text = "€"
                interest_money_tv.text = "€"
                monthly_money_tv.text = "€"
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
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                Timber.i(getString(R.string.main_click))
            }
            R.id.drawer_second -> {
                val intent = Intent(baseContext, SettingsActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                Timber.i(getString(R.string.settings_click))
            }
            R.id.drawer_third -> {
                signOutUserFromFirebase()
                Timber.i(getString(R.string.logout))
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
        return true
    }

    /**
     * User can simulate a loan, with null check.
     * @see SettingsPresenter.calculateLoan
     */
    override fun loanSimulation() {
        button_loans.setOnClickListener {
            when {
                loan_amount_it.text.toString() == "" -> Utils.snackBarPreset(findViewById(android.R.id.content), getString(R.string.set_amount))
                loan_rate_it.text.toString() == "" -> Utils.snackBarPreset(findViewById(android.R.id.content), getString(R.string.set_rate))
                loan_years_it.text.toString() == "" -> Utils.snackBarPreset(findViewById(android.R.id.content), getString(R.string.set_years))
                else -> {
                    val mAmount: Int = loan_amount_it.text.toString().toInt()
                    val mRate: Double = loan_rate_it.text.toString().toDouble()
                    val mYears: Int = loan_years_it.text.toString().toInt()
                    var mIntArray = DoubleArray(3)
                    mPresenter.calculateLoan(mIntArray, mAmount, mRate, mYears)
                    loan_monthly_tv.text = mIntArray[0].toString()
                    loan_duration_tv.text = mIntArray[1].roundToInt().toString()
                    loan_interest_tv.text = mIntArray[2].toString()
                    loan_rate_tv.text = mRate.toString()
                    cardview_result.visibility = View.VISIBLE
                    Utils.setFadeAnimation(cardview_result, this)
                    loan_duration_tv.visibility = View.VISIBLE
                    loan_monthly_tv.visibility = View.VISIBLE
                    loan_interest_tv.visibility = View.VISIBLE
                    loan_rate_tv.visibility = View.VISIBLE
                }
            }

        }
    }

    /**
     * On Click the buttons of settings.
     * For changing devise, or Themes, save them in SharedPreferences.
     * @see applyNewTheme
     */
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

    /**
     * Apply a new theme to the app. Restart the app.
     */
    override fun applyNewTheme() {
        finish()
        val intent = Intent.makeMainActivity(ComponentName(
                this@SettingsActivity, LoginActivity::class.java))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    /**
     * Override on back pressed when drawer layout is open.
     */
    override fun onBackPressed() {
        if (settings_dl.isDrawerOpen(GravityCompat.START)) {
            settings_dl.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
    }

    /**
     * Set the listeners.
     */
    override fun configureListeners() {
        settings_activity_nav_view.setNavigationItemSelectedListener(this)
        dollars_btn.setOnClickListener(this)
        euros_btn.setOnClickListener(this)
        theme_0_btn.setOnClickListener(this)
        theme_1_btn.setOnClickListener(this)
        theme_2_btn.setOnClickListener(this)
        theme_3_btn.setOnClickListener(this)
        button_loans.setOnClickListener(this)
        settings_activity_layout.setOnClickListener { it.hideKeyboard() }
    }
}
