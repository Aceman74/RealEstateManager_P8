/*
 * *
 *  * Created by Lionel Joffray on 16/09/19 21:09
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 14/09/19 22:17
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
        fun nearbyRequest(mLocation: String, mType: String, mKeyWord: String, mRadius: Int)
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
        fun updateNearby(details: Nearby)
    }
}
