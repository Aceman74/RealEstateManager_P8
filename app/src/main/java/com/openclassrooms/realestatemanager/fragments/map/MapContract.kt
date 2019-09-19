/*
 * *
 *  * Created by Lionel Joffray on 19/09/19 21:47
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 19/09/19 19:27
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
    }
}
