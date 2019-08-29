/*
 * *
 *  * Created by Lionel Joffray on 29/08/19 22:26
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 29/08/19 22:26
 *
 */

package com.openclassrooms.realestatemanager.repositories

import androidx.lifecycle.LiveData
import com.openclassrooms.realestatemanager.database.dao.EstateDao
import com.openclassrooms.realestatemanager.models.Estate


/**
 * Created by Lionel JOFFRAY - on 29/08/2019.
 */
class EstateDataRepository(val estateDao: EstateDao) {


    private var mAllData: LiveData<List<Estate>> = estateDao.getAll()


    // --- GET ---

    fun findAllEstate(): LiveData<List<Estate>> {
        return mAllData
    }

    fun finEstateById(estateid: Int): LiveData<List<Estate>> {
        return estateDao.findEstateByEid(estateid)
    }

    fun finEstateByUid(estateUid: String): LiveData<List<Estate>> {
        return estateDao.findEstateByUid(estateUid)
    }

    fun finByType(type: Int) {
        estateDao.findByType(type)
    }

    fun finByNeighborhood(neighborhood: Int) {
        estateDao.findByNeighborhood(neighborhood)
    }

    fun finByPrice(price: String) {
        estateDao.findByPrice(price)
    }

    fun finByDescription(desc: String) {
        estateDao.findByDescription(desc)
    }

    fun finBySqft(sqft: Int) {
        estateDao.findBySqft(sqft)
    }

    fun finByRooms(rooms: Int) {
        estateDao.findByRooms(rooms)
    }

    fun finByBathrooms(bathrooms: Int) {
        estateDao.findByBathrooms(bathrooms)
    }

    fun finByBedrooms(bedrooms: Int) {
        estateDao.findByBedrooms(bedrooms)
    }

    fun finByAvailability(available: Int) {
        estateDao.findByAvailability(available)
    }

    // --- CREATE ---

    fun createEstate(estate: Estate) {
        estateDao.createEstate(estate)
    }

    // --- DELETE ---
    fun deleteEstate(estate: Estate) {
        estateDao.deleteEstate(estate)
    }

    // --- UPDATE ---
    fun updateEstate(estate: Estate) {
        estateDao.updateEstate(estate)
    }
}