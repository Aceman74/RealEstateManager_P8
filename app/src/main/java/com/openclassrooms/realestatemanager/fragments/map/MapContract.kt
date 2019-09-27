/*
 * *
 *  * Created by Lionel Joffray on 27/09/19 11:20
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 27/09/19 09:45
 *
 */

package com.openclassrooms.realestatemanager.fragments.map


import com.openclassrooms.realestatemanager.utils.base.BaseView


/**
 * Created by Lionel JOFFRAY - on 04/06/2019.
 *
 *
 * Fragment Map Contracts.
 */
interface MapContract {

    interface MapPresenterInterface

    interface MapViewInterface : BaseView {
        fun getLocationPermission()
        fun configureMapMarkers()
        fun initMap()
        fun loadSharedPref()
    }
}
