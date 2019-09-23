/*
 * *
 *  * Created by Lionel Joffray on 23/09/19 21:08
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 23/09/19 21:08
 *  
 */

package com.openclassrooms.realestatemanager.activities.login

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.multi.DialogOnAnyDeniedMultiplePermissionsListener
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.activities.main.MainActivity
import com.openclassrooms.realestatemanager.injections.Injection
import com.openclassrooms.realestatemanager.models.User
import com.openclassrooms.realestatemanager.utils.Utils
import com.openclassrooms.realestatemanager.utils.Utils.isInternetAvailable
import com.openclassrooms.realestatemanager.utils.base.BaseActivity
import com.openclassrooms.realestatemanager.viewmodels.UserViewModel
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Created by Lionel JOFFRAY - on 06/08/2019.
 *
 * This activity is for login the user to the app.
 * Extends:
 * @see BaseActivity for setting the view
 * @see LoginContract contract for MVP
 *
 */
@Suppress("DEPRECATED_IDENTITY_EQUALS")
class LoginActivity(override val activityLayout: Int = R.layout.activity_login) : BaseActivity(), LoginContract.LoginViewInterface {

    private val mPresenter: LoginPresenter = LoginPresenter()
    private val RC_SIGN_IN = 111
    private var mAlertDialog: AlertDialog.Builder? = null
    private lateinit var mUserViewModel: UserViewModel
    /**
     * Set the presenter, configure UserViewModel, check permission for the app, check logged or not,
     * also check if internet connexion.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.attachView(this)
        configureViewModel()
        checkPermission()
        isCurrentUserLogged
        isInternetAvailable(applicationContext)

        login_btn.setOnClickListener {
            if (ContextCompat.checkSelfPermission(applicationContext,
                            Manifest.permission.ACCESS_FINE_LOCATION) !== PackageManager.PERMISSION_GRANTED) run {
                mAlertDialog = null
                askPermission()
            } else {
                if (isCurrentUserLogged == true) {
                    val intent = Intent(baseContext, MainActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                } else {
                    startSignInActivity()
                }
            }
        }
    }

    /**
     * Check if user already grant permission.
     */
    override fun checkPermission() {
        if (ContextCompat.checkSelfPermission(applicationContext,
                        Manifest.permission.ACCESS_FINE_LOCATION) !== PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) !== PackageManager.PERMISSION_GRANTED) {
            askPermission()
        }
    }

    /**
     * Ask user for location and call permission.
     *
     * @see Dexter
     */
    override fun askPermission() {
        if (mAlertDialog == null) {
            mAlertDialog = AlertDialog.Builder(this)
            mAlertDialog!!.setTitle("Real Estate Manager")
            mAlertDialog!!.setMessage("For fully working features, this application needs some permissions from you.")
            mAlertDialog!!.setPositiveButton(android.R.string.yes) { dialog, _ ->
                dialog.dismiss()
                dexterInit()
            }
            mAlertDialog!!.show()

        }
    }

    /**
     * Set the userViewModel to save.
     */
    override fun configureViewModel() {
        val mViewModelFactory = Injection.provideViewModelFactory(this)
        this.mUserViewModel = ViewModelProviders.of(this, mViewModelFactory).get(UserViewModel::class.java)
        if (isCurrentUserLogged!!)
            login_btn.text = getString(R.string.enter)
    }

    /**
     * Save user to Database.
     */
    override fun saveUserToDatabase() {
        val user = User(currentUser!!.uid, currentUser!!.displayName.toString(), currentUser!!.email.toString(), currentUser!!.photoUrl.toString(), Utils.todayDate)
        this.mUserViewModel.createUser(user)
    }

    /**
     * Dexter library used for permissions.
     */
    override fun dexterInit() {
        val dialogMultiplePermissionsListener = DialogOnAnyDeniedMultiplePermissionsListener.Builder
                .withContext(this)
                .withTitle("Permissions denied")
                .withMessage("Unfortunately, you cannot run the application without these permissions.")
                .withButtonText(android.R.string.ok)
                .build()

        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                ).withListener(dialogMultiplePermissionsListener)
                .check()
    }

    /**
     * Start the signin activity, with google or email.
     */
    override fun startSignInActivity() {

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(
                                listOf(AuthUI.IdpConfig.EmailBuilder().build(), AuthUI.IdpConfig.GoogleBuilder().build()))
                        .setIsSmartLockEnabled(false, true)
                        .setLogo(R.drawable.logo_black)
                        .build(),
                RC_SIGN_IN)   // Sign In
    }

    /**
     * Handle the response after Signed in.
     *
     * @param requestCode requestCode
     * @param resultCode  resultCode
     * @param data        data
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                saveUserToDatabase()
                // ...
            } else {
                Utils.snackBarPreset(this.findViewById(android.R.id.content), getString(R.string.error_try_again))
            }
        }
    }
    /**
     *
    private fun configureTextViewMain() {
    textViewMain.textSize = 15f
    textViewMain.text = "Le premier bien immobilier enregistré vaut "
    }

    private fun configureTextViewQuantity() {
    val quantity = Utils.convertDollarToEuro(200)
    textViewQuantity.textSize = 20f
    textViewQuantity.text = quantity.toString()   //  Fix the variable, convert from int to desc.
    }

     */
}
