/*
 * *
 *  * Created by Lionel Joffray on 17/09/19 23:02
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 17/09/19 11:38
 *
 */

package com.openclassrooms.realestatemanager.activities.addestate


import android.content.Context
import com.openclassrooms.realestatemanager.models.places.nearby_search.Nearby
import com.openclassrooms.realestatemanager.utils.base.BaseView
import java.io.File

/**
 * Created by Lionel JOFFRAY - on 28/05/2019.
 *
 *
 * The contracts for Main Activity.
 */
interface AddEstateContract {

    interface AddEstatePresenterInterface {

        fun createPhotosFolder(context: Context): File
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
    }
}
