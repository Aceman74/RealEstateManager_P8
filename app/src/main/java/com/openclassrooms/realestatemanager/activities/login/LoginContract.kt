/*
 * *
 *  * Created by Lionel Joffray on 19/09/19 21:47
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 19/09/19 18:38
 *  
 */

package com.openclassrooms.realestatemanager.activities.login


import com.openclassrooms.realestatemanager.utils.base.BaseView

/**
 * Created by Lionel JOFFRAY - on 28/05/2019.
 *
 *
 * The contracts for Login Activity.
 */
interface LoginContract {

    interface LoginPresenterInterface

    interface LoginViewInterface : BaseView {
        fun checkPermission()
        fun askPermission()
        fun dexterInit()
        fun startSignInActivity()
        fun configureViewModel()
        fun saveUserToDatabase()
    }
}
