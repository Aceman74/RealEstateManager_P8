/*
 * *
 *  * Created by Lionel Joffray on 11/09/19 20:37
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 11/09/19 20:37
 *
 */

package com.openclassrooms.realestatemanager.utils.base

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.activities.login.LoginActivity
import kotlinx.android.synthetic.main.navigation_drawer_header.view.*
import timber.log.Timber

/**
 * Created by Lionel JOFFRAY - on 02/05/2019.
 *
 *
 * Base Class for all Activity.
 *
 */
abstract class BaseActivity : AppCompatActivity() {
    val SIGN_OUT_TASK = 12
    var mTheme = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        loadTheme()
        super.onCreate(savedInstanceState)
        this.setContentView(this.activityLayout)
    }

    private fun loadTheme() {
        val shared = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE)
        mTheme = shared.getInt("actual_theme", 0)
        when (mTheme) {
            0 -> setTheme(R.style.AppTheme)
            1 -> setTheme(R.style.AppTheme_1)
            2 -> setTheme(R.style.AppTheme_2)
            3 -> setTheme(R.style.AppTheme_3)
        }
    }

    /**
     * Get Layout.
     *
     * @return layout
     */
    abstract val activityLayout: Int


    fun configureDrawerLayout(drawer: DrawerLayout, toolbar: Toolbar) {
        val toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
    }

    fun navigationDrawerHeader(navView: NavigationView) {
        val headerView = navView.getHeaderView(0)
        Glide.with(applicationContext)
                .load(currentUser?.photoUrl)
                .into(headerView.profile_image_nav_header)
        headerView.name_nav_header.text = currentUser?.displayName
        headerView.email_nav_header.text = currentUser?.email
    }
    /**
     * Get current user on Firebase Auth.
     *
     * @return current user
     */
    protected val currentUser: FirebaseUser?
        get() = FirebaseAuth.getInstance().currentUser

    /**
     * Check if user is logged.
     *
     * @return log state
     */
    protected val isCurrentUserLogged: Boolean?
        get() = this.currentUser != null

    /**
     * Sign out user from firebase method.
     */
    fun signOutUserFromFirebase() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnSuccessListener(this, this.updateUIAfterRESTRequestsCompleted(SIGN_OUT_TASK))
                .addOnFailureListener(onFailureListener())
        val start = Intent(applicationContext, LoginActivity::class.java)
        startActivity(start)
    }

    fun updateUIAfterRESTRequestsCompleted(origin: Int): OnSuccessListener<Void> {
        return OnSuccessListener {
            if (origin == SIGN_OUT_TASK) {
                finish()
            }
        }
    }
    /**
     * On Failure listener for Firebase REST.
     *
     * @return failure
     */
    protected fun onFailureListener(): OnFailureListener {
        return OnFailureListener { e ->
            Toast.makeText(applicationContext, getString(R.string.error_unknown_error), Toast.LENGTH_LONG).show()
            Timber.tag("Firebase ERROR").e(e)
        }
    }
}


