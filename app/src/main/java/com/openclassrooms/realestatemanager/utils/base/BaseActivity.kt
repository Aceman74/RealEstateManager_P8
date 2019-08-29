/*
 * *
 *  * Created by Lionel Joffray on 29/08/19 22:26
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 29/08/19 22:22
 *
 */

package com.openclassrooms.realestatemanager.utils.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.openclassrooms.realestatemanager.R
import timber.log.Timber

/**
 * Created by Lionel JOFFRAY - on 02/05/2019.
 *
 *
 * Base Class for all Activity.
 *
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(this.activityLayout)
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


