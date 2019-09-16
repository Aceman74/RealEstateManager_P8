/*
 * *
 *  * Created by Lionel Joffray on 16/09/19 21:09
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 14/09/19 22:17
 *
 */

package com.openclassrooms.realestatemanager.activities.addestate


import android.content.Context
import android.util.Log
import com.openclassrooms.realestatemanager.api.PlacesApi
import com.openclassrooms.realestatemanager.models.places.nearby_search.Nearby
import com.openclassrooms.realestatemanager.utils.base.BasePresenter
import io.reactivex.observers.DisposableObserver
import timber.log.Timber
import java.io.File

/**
 * Created by Lionel JOFFRAY - on 28/05/2019.
 *
 *
 * The presenter for Login Activity.
 */
class AddEstatePresenter : BasePresenter(), AddEstateContract.AddEstatePresenterInterface {

    override fun createPhotosFolder(context: Context): File {
        val mEstatePhotosDir = File(context.applicationInfo.dataDir + "/files/", "estate_photos")

        if (mEstatePhotosDir.mkdir()) {
            Timber.tag("Folder FUN").d("Directory created")
        } else {
            Timber.tag("Folder FUN").d("Directory is not created")
        }
        return mEstatePhotosDir
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

