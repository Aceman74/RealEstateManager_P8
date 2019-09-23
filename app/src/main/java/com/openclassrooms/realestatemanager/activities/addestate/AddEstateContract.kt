/*
 * *
 *  * Created by Lionel Joffray on 23/09/19 21:08
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 23/09/19 21:08
 *
 */

package com.openclassrooms.realestatemanager.activities.addestate


import com.openclassrooms.realestatemanager.models.places.nearby_search.Nearby
import com.openclassrooms.realestatemanager.utils.base.BaseView

/**
 * Created by Lionel JOFFRAY - on 28/05/2019.
 *
 *
 * The contracts for AddEstate Activity.
 */
interface AddEstateContract {

    interface AddEstatePresenterInterface {

        fun nearbySchool(mLocation: String, mType: String, mRadius: Int)
        fun nearbyPolice(mLocation: String, mType: String, mRadius: Int)
        fun nearbyHospital(mLocation: String, mType: String, mRadius: Int)
    }

    interface AddEstateViewInterface : BaseView {
        fun configureView()
        fun configureListeners()
        fun configureViewModel()
        fun configureMaps()
        fun addToDatabase()
        fun showNumberPicker(i: Int, mOldVal: Int?, string: String?)
        fun checkIfNoNull()
        fun imagePicker(pickImageRequest: Int)
        fun autocompleteIntent()
        fun editIntent()
        fun loadSharedPref()
        fun updateNearbySchool(details: Nearby)
        fun updateNearbyPolice(details: Nearby)
        fun updateNearbyHospital(details: Nearby)
        fun callForNearby(locat: String)
    }
}
