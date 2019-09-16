/*
 * *
 *  * Created by Lionel Joffray on 16/09/19 21:09
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 16/09/19 21:09
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
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.multi.DialogOnAnyDeniedMultiplePermissionsListener
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.activities.main.MainActivity
import com.openclassrooms.realestatemanager.utils.Utils.isInternetAvailable
import com.openclassrooms.realestatemanager.utils.base.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Created by Lionel JOFFRAY - on 06/08/2019.
 */

@Suppress("DEPRECATED_IDENTITY_EQUALS")
class LoginActivity(override val activityLayout: Int = R.layout.activity_login) : BaseActivity(), LoginContract.LoginViewInterface {

    private val mPresenter: LoginPresenter = LoginPresenter()
    private val RC_SIGN_IN = 111
    private var mAlertDialog: AlertDialog.Builder? = null
    var mDevise = "$"

    override fun onCreate(savedInstanceState: Bundle?) {
        loadSharedPref()
        super.onCreate(savedInstanceState)
        mPresenter.attachView(this)
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
                    //  mPresenter.alreadyLogged()
                    val intent = Intent(baseContext, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    startSignInActivity()
                }
            }
        }
    }

    override fun loadSharedPref() {
        val shared = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE)
        mDevise = shared.getString("actual_devise", "$")!!
        when (mDevise) {
            "$" -> {
                setTheme(R.style.AppTheme)
            }
            "€" -> {
                setTheme(R.style.AppTheme_1)
            }
        }
    }

    /**
     * Check if user already grant permission
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
            mAlertDialog!!.setTitle("NEED TO ASK")
            mAlertDialog!!.setMessage("REGULAR BLA BLA")
            mAlertDialog!!.setPositiveButton(android.R.string.yes) { dialog, _ ->
                dialog.dismiss()
                dexterInit()
            }
            mAlertDialog!!.show()

        }
    }

    /**
     * Dexter library used for permissions.
     */
    override fun dexterInit() {
        val dialogMultiplePermissionsListener = DialogOnAnyDeniedMultiplePermissionsListener.Builder
                .withContext(this)
                .withTitle("NEED PERM")
                .withMessage("I NEED SOME ACCESS DUDE")
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
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
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
