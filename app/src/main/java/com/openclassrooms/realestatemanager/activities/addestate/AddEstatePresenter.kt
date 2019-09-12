/*
 * *
 *  * Created by Lionel Joffray on 12/09/19 20:50
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 12/09/19 20:50
 *
 */

package com.openclassrooms.realestatemanager.activities.addestate


import android.content.Context
import android.util.Log
import com.openclassrooms.realestatemanager.api.PlacesApi
import com.openclassrooms.realestatemanager.models.Picture
import com.openclassrooms.realestatemanager.models.places.nearby_search.Nearby
import com.openclassrooms.realestatemanager.utils.base.BasePresenter
import com.openclassrooms.realpicturemanager.activities.viewmodels.PictureViewModel
import io.reactivex.observers.DisposableObserver
import timber.log.Timber
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.channels.FileChannel

/**
 * Created by Lionel JOFFRAY - on 28/05/2019.
 *
 *
 * The presenter for Login Activity.
 */
class AddEstatePresenter : BasePresenter(), AddEstateContract.AddEstatePresenterInterface {

    override fun savePictureToCustomPath(eid: Long, mPicturePathArray: ArrayList<String>, mEstatePhotosDir: File, displayName: String?, mPictureViewModel: PictureViewModel) {

        var i = 0
        lateinit var fileDest: File
        while (i < mPicturePathArray.size) {
            if (mPicturePathArray[i] != "") {
                val file = File(mPicturePathArray[i])
                val pictureName = eid.toString() + "_" + displayName + "_" + "$i"
                if (i == 0) {
                    fileDest = File(mEstatePhotosDir.path + "/" + pictureName + "_main.jpg")
                } else {
                    fileDest = File(mEstatePhotosDir.path + "/" + pictureName + ".jpg")
                }
                if (!fileDest.exists()) {
                    copyFile(file, fileDest)
                }
                savePicture(pictureName, fileDest.toString(), eid, mPictureViewModel)
            }
            i++
        }
    }

    override fun savePicture(pictureName: String, fileDest: String, eid: Long, mPictureViewModel: PictureViewModel) {
        mPictureViewModel.createPicture(Picture(null, eid, pictureName, fileDest))
    }

    override fun createPhotosFolder(context: Context): File {
        val mEstatePhotosDir = File(context.applicationInfo.dataDir + "/files/", "estate_photos")

        if (mEstatePhotosDir.mkdir()) {
            Timber.tag("Folder FUN").d("Directory created")
        } else {
            Timber.tag("Folder FUN").d("Directory is not created")
        }
        return mEstatePhotosDir
    }

    /**
     * copy contents from source file to mappPath file
     *
     * @param sourceFilePath  Source file path address
     * @param destinationFilePath Destination file path address
     */
    override fun copyFile(sourceFilePath: File, destinationFilePath: File) {

        try {

            if (!sourceFilePath.exists()) {
                return
            }

            val source: FileChannel = FileInputStream(sourceFilePath).channel
            val destination: FileChannel = FileOutputStream(destinationFilePath).channel
            destination.transferFrom(source, 0, source.size())
            source.close()
            destination.close()

        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }

    override fun nearbyRequest(mLocation: String, mType: String, mKeyWord: String, mRadius: Int) {

        val newDisposable = PlacesApi.instance.getLocationInfo(mLocation, mType, mKeyWord, mRadius).subscribeWith(object : DisposableObserver<Nearby>() {
            override fun onNext(details: Nearby) {
                Timber.tag("NEARBY_Next").i("On Next")
                Timber.tag("NEARBY_OBSERVABLE").i("from: $mLocation type: $mType")
                (getView() as AddEstateContract.AddEstateViewInterface).updateNearby(details)
            }

            override fun onError(e: Throwable) {
                Timber.tag("NEARBY_Error").e("On Error%s", Log.getStackTraceString(e))
            }

            override fun onComplete() {
                Timber.tag("NEARBY_Complete").i("On Complete !!")

            }
        })
    }

}

