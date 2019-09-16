/*
 * *
 *  * Created by Lionel Joffray on 16/09/19 21:09
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 16/09/19 21:09
 *
 */

package com.openclassrooms.realestatemanager.repositories

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.openclassrooms.realestatemanager.database.dao.EstateDao
import com.openclassrooms.realestatemanager.models.Estate
import com.openclassrooms.realestatemanager.models.EstateAndPictures
import com.openclassrooms.realestatemanager.models.Nearby


/**
 * Created by Lionel JOFFRAY - on 29/08/2019.
 */
class EstateDataRepository(val estateDao: EstateDao) {


    private var mAllData: LiveData<List<Estate>> = estateDao.getAll()
    var mAllEstateAndPicture: LiveData<List<EstateAndPictures>> = estateDao.getAllEstateAndPictures()
    var long: Long = 0


    // --- GET ---

    fun findAllEstate(): LiveData<List<Estate>> {
        return mAllData
    }

    fun findEstatePictures(estateid: Long): LiveData<List<EstateAndPictures>> {
        return estateDao.findEstatePictures(estateid)
    }

    fun findEstateById(estateid: Long): LiveData<List<Estate>> {
        return estateDao.findEstateByEid(estateid)
    }

    fun findEstateByUid(estateUid: String): LiveData<List<Estate>> {
        return estateDao.findEstateByUid(estateUid)
    }

    fun findByType(type: Int) {
        estateDao.findByType(type)
    }

    fun findByNeighborhood(neighborhood: Int) {
        estateDao.findByNeighborhood(neighborhood)
    }

    fun findByPrice(price: String) {
        estateDao.findByPrice(price)
    }

    fun findByDescription(desc: String) {
        estateDao.findByDescription(desc)
    }

    fun findBySqft(sqft: Int) {
        estateDao.findBySqft(sqft)
    }

    fun findByRooms(rooms: Int) {
        estateDao.findByRooms(rooms)
    }

    fun findByBathrooms(bathrooms: Int) {
        estateDao.findByBathrooms(bathrooms)
    }

    fun findByBedrooms(bedrooms: Int) {
        estateDao.findByBedrooms(bedrooms)
    }

    fun findByAvailability(available: Int) {
        estateDao.findByAvailability(available)
    }

    fun findEstateAndPictures(): LiveData<List<EstateAndPictures>> {
        return mAllEstateAndPicture
    }

    // --- CREATE ---

    fun createEstate(estate: Estate): Long {
        long = estateDao.createEstate(estate)
        return long
    }

    fun createNearby(nearby: Nearby): Long {
        return estateDao.createNearby(nearby)
    }

    // --- DELETE ---
    fun deleteEstate(estate: Estate) {
        estateDao.deleteEstate(estate)
    }

    // --- UPDATE ---
    fun updateEstate(estate: Estate) {
        estateDao.updateEstate(estate)
    }

    fun insert(estate: Estate) {
        InsertAsyncTask(estateDao).execute(estate)
    }

    private class InsertAsyncTask internal constructor(private val estateDao: EstateDao) : AsyncTask<Estate, Void, Long>() {

        override fun doInBackground(vararg params: Estate): Long? {
            return estateDao.createEstate(params[0])
        }
    }
}