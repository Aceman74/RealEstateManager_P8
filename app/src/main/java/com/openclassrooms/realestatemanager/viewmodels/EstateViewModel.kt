/*
 * *
 *  * Created by Lionel Joffray on 03/09/19 16:31
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 03/09/19 16:31
 *
 */

package com.openclassrooms.realestatemanager.viewmodels

import android.app.Application
import androidx.annotation.Nullable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.openclassrooms.realestatemanager.database.RealEstateDatabase
import com.openclassrooms.realestatemanager.models.Estate
import com.openclassrooms.realestatemanager.models.EstateAndPictures
import com.openclassrooms.realestatemanager.models.User
import com.openclassrooms.realestatemanager.repositories.EstateDataRepository
import java.util.concurrent.Executor

/**
 * Created by Lionel JOFFRAY - on 15/08/2019.
 */
class EstateViewModel(application: Application, val executor: Executor) : AndroidViewModel(application) {

    // DATA
    @Nullable
    private var currentUser: LiveData<User>? = null
    private val repository: EstateDataRepository
    val allEstate: LiveData<List<Estate>>
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
        return repository.finEstateById(estateId)
    }

    fun getEstateByUid(estateUid: String): LiveData<List<Estate>> {
        return repository.finEstateByUid(estateUid)
    }

    fun getEstateByType(type: Int) {
        executor.execute { repository.finByType(type) }
    }

    fun getEstateByNeighborhood(neighborhood: Int) {
        executor.execute { repository.finByNeighborhood(neighborhood) }
    }

    fun getEstateByPrice(price: String) {
        executor.execute { repository.finByPrice(price) }
    }

    fun getEstateByDescription(desc: String) {
        executor.execute { repository.finByDescription(desc) }
    }

    fun getEstateBySqft(sqft: Int) {
        executor.execute { repository.finBySqft(sqft) }
    }

    fun getEstateByRooms(rooms: Int) {
        executor.execute { repository.finByRooms(rooms) }
    }

    fun getEstateByBathrooms(bathrooms: Int) {
        executor.execute { repository.finByBathrooms(bathrooms) }
    }

    fun getEstateByBedrooms(bedrooms: Int) {
        executor.execute { repository.finByBedrooms(bedrooms) }
    }

    fun getEstateByAvailability(available: Int) {
        executor.execute { repository.finByAvailability(available) }
    }

    fun createEstate(estate: Estate) {
        executor.execute { repository.createEstate(estate) }

    }

    fun deleteEstate(estate: Estate) {
        executor.execute { repository.deleteEstate(estate) }
    }

    fun updateEstate(estate: Estate) {
        executor.execute { repository.updateEstate(estate) }
    }
}
