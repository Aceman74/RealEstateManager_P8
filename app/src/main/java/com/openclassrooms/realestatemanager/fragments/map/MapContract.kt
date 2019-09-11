/*
 * *
 *  * Created by Lionel Joffray on 11/09/19 20:37
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 11/09/19 20:37
 *
 */

package com.openclassrooms.realestatemanager.fragments.map


import com.openclassrooms.realestatemanager.utils.base.BaseView


/**
 * Created by Lionel JOFFRAY - on 04/06/2019.
 *
 *
 * Workmates Contracts.
 */
interface MapContract {

    interface MapPresenterInterface

    interface MapViewInterface : BaseView {
        fun getLocationPermission()
        fun configureMapMarkers()
    }
}
