/*
 * *
 *  * Created by Lionel Joffray on 11/09/19 20:37
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 11/09/19 20:26
 *
 */

package com.openclassrooms.realestatemanager.activities.addestate


import android.content.Context
import com.openclassrooms.realestatemanager.utils.base.BaseView
import com.openclassrooms.realpicturemanager.activities.viewmodels.PictureViewModel
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
        fun copyFile(sourceFilePath: File, destinationFilePath: File)
        fun savePictureToCustomPath(eid: Long, mPicturePathArray: ArrayList<String>, mEstatePhotosDir: File, displayName: String?, mPictureViewModel: PictureViewModel)
        fun savePicture(pictureName: String, fileDest: String, eid: Long, mPictureViewModel: PictureViewModel)
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
    }
}
