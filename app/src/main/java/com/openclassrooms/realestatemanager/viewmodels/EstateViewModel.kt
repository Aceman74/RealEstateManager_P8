/*
 * *
 *  * Created by Lionel Joffray on 23/09/19 21:08
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 23/09/19 21:08
 *
 */

package com.openclassrooms.realestatemanager.viewmodels

import android.app.Application
import android.content.Context
import androidx.annotation.Nullable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.openclassrooms.realestatemanager.database.RealEstateDatabase
import com.openclassrooms.realestatemanager.models.Estate
import com.openclassrooms.realestatemanager.models.EstateAndPictures
import com.openclassrooms.realestatemanager.models.Nearby
import com.openclassrooms.realestatemanager.models.User
import com.openclassrooms.realestatemanager.models.places.nearby_search.Result
import com.openclassrooms.realestatemanager.repositories.EstateDataRepository
import com.openclassrooms.realestatemanager.utils.Utils
import java.io.File
import java.util.concurrent.Executor

/**
 * Created by Lionel JOFFRAY - on 15/08/2019.
 *
 * ViewModel for Estate .
 */
class EstateViewModel(application: Application, val executor: Executor) : AndroidViewModel(application) {

    // DATA
    @Nullable
    private var currentUser: LiveData<User>? = null
    private val repository: EstateDataRepository
    val allEstate: LiveData<List<Estate>>
    var long: Long = 0
    val allEstateWithPitures: LiveData<List<EstateAndPictures>>

    init {
        val estateDao = RealEstateDatabase.getInstance(application).estateDao()
        repository = EstateDataRepository(estateDao)
        allEstate = repository.findAllEstate()
        allEstateWithPitures = repository.findEstateAndPictures()
    }
    // -------------
    // FOR USER
    // -------------

    fun getUser(userId: Long): LiveData<User>? {
        return this.currentUser
    }

    // -------------
    // FOR ITEM
    // -------------

    fun getEstateById(estateId: Long): LiveData<List<Estate>> {
        return repository.findEstateById(estateId)
    }

    fun getEstateByUid(estateUid: String): LiveData<List<Estate>> {
        return repository.findEstateByUid(estateUid)
    }

    fun getEstatePictures(estateId: Long): LiveData<List<EstateAndPictures>> {
        return repository.findEstatePictures(estateId)
    }

    fun getEstateByType(type: Int) {
        executor.execute { repository.findByType(type) }
    }

    fun getEstateByNeighborhood(neighborhood: Int) {
        executor.execute { repository.findByNeighborhood(neighborhood) }
    }

    fun getEstateByPrice(price: String) {
        executor.execute { repository.findByPrice(price) }
    }

    fun getEstateByDescription(desc: String) {
        executor.execute { repository.findByDescription(desc) }
    }

    fun getEstateBySqft(sqft: Int) {
        executor.execute { repository.findBySqft(sqft) }
    }

    fun getEstateByRooms(rooms: Int) {
        executor.execute { repository.findByRooms(rooms) }
    }

    fun getEstateByBathrooms(bathrooms: Int) {
        executor.execute { repository.findByBathrooms(bathrooms) }
    }

    fun getEstateByBedrooms(bedrooms: Int) {
        executor.execute { repository.findByBedrooms(bedrooms) }
    }

    fun getEstateByAvailability(available: Int) {
        executor.execute { repository.findByAvailability(available) }
    }

    /**
     * Here the estate is created, and the Foreign Key is used to save Pictures and Nearby next.
     * @see Utils.savePictureToCustomPath
     * @see Utils.createNearby
     */
    fun createEstate(estate: Estate, mPath: ArrayList<String>, mContext: Context, mName: String?, mViewModel: PictureViewModel, mEstateViewModel: EstateViewModel, mSchool: java.util.ArrayList<String>, mPolice: List<Result>?, mHospital: List<Result>?) {
        executor.execute {
            val eid: Long = repository.createEstate(estate)
            Utils.savePictureToCustomPath(eid, mPath, mContext, mName, mViewModel)
            Utils.createNearby(eid, mSchool, mPolice, mHospital, mEstateViewModel)
        }
    }

    fun insterEstate(estate: Estate): Long? {
        repository.insert(estate)
        return estate.estateId
    }

    fun createNearby(nearby: Nearby) {
        executor.execute { repository.createNearby(nearby) }
    }

    fun deleteEstate(estate: Estate) {
        executor.execute { repository.deleteEstate(estate) }
    }

    /**
     * Update estate and get Foreign key for saving Pictures too.
     */
    fun updateEstate(estate: Estate, eid: Long, mPath: ArrayList<String>, mDir: File, mName: String?, mViewModel: PictureViewModel) {
        executor.execute {
            repository.createEstate(estate)
        }
    }

}
