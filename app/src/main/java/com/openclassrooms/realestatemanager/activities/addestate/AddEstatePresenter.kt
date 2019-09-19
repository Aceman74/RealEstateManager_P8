/*
 * *
 *  * Created by Lionel Joffray on 19/09/19 21:47
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 19/09/19 20:03
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
 * The presenter for AddEstate Activity.
 */
class AddEstatePresenter : BasePresenter(), AddEstateContract.AddEstatePresenterInterface {
    /**
     * This method create the directory to sazve all images in DB.
     */
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
     * get MapsNearby Schools.
     */
    override fun nearbySchool(mLocation: String, mType: String, mRadius: Int) {

        val newDisposable = PlacesApi.instance.getLocationInfo(mLocation, mType, mRadius).subscribeWith(object : DisposableObserver<Nearby>() {
            override fun onNext(details: Nearby) {
                Timber.tag("SCHOOL_Next").i("On Next")
                Timber.tag("SCHOOL_OBSERVABLE").i("from: $mLocation type: $mType")
                (getView() as AddEstateContract.AddEstateViewInterface).updateNearbySchool(details)
            }

            override fun onError(e: Throwable) {
                Timber.tag("SCHOOL_Error").e("On Error%s", Log.getStackTraceString(e))
            }

            override fun onComplete() {
                Timber.tag("SCHOOL_Complete").i("On Complete !!")
            }
        })
    }

    /**
     * get MapsNearby Police.
     */
    override fun nearbyPolice(mLocation: String, mType: String, mRadius: Int) {

        val newDisposable = PlacesApi.instance.getLocationInfo(mLocation, mType, mRadius).subscribeWith(object : DisposableObserver<Nearby>() {
            override fun onNext(details: Nearby) {
                Timber.tag("POLICE_Next").i("On Next")
                Timber.tag("POLICE_OBSERVABLE").i("from: $mLocation type: $mType")
                (getView() as AddEstateContract.AddEstateViewInterface).updateNearbyPolice(details)
            }

            override fun onError(e: Throwable) {
                Timber.tag("POLICE_Error").e("On Error%s", Log.getStackTraceString(e))
            }

            override fun onComplete() {
                Timber.tag("POLICE_Complete").i("On Complete !!")

            }
        })
    }

    /**
     * get MapsNearby Hospitals.
     */
    override fun nearbyHospital(mLocation: String, mType: String, mRadius: Int) {

        val newDisposable = PlacesApi.instance.getLocationInfo(mLocation, mType, mRadius).subscribeWith(object : DisposableObserver<Nearby>() {
            override fun onNext(details: Nearby) {
                Timber.tag("HOSPITAL_Next").i("On Next")
                Timber.tag("HOSPITAL_OBSERVABLE").i("from: $mLocation type: $mType")
                (getView() as AddEstateContract.AddEstateViewInterface).updateNearbyHospital(details)
            }

            override fun onError(e: Throwable) {
                Timber.tag("HOSPITAL_Error").e("On Error%s", Log.getStackTraceString(e))
            }

            override fun onComplete() {
                Timber.tag("HOSPITAL_Complete").i("On Complete !!")

            }
        })
    }
}

