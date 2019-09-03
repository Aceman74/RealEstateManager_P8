/*
 * *
 *  * Created by Lionel Joffray on 03/09/19 16:31
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 03/09/19 16:31
 *
 */

package com.openclassrooms.realestatemanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.openclassrooms.realestatemanager.models.Estate
import com.openclassrooms.realestatemanager.models.EstateAndPictures

/**
 * Created by Lionel JOFFRAY - on 26/08/2019.
 */
@Dao
interface EstateDao {

    @Query("SELECT * FROM Estate")
    fun getAll(): LiveData<List<Estate>>

    @Transaction
    @Query("SELECT * FROM Estate")
    fun getEstateAndPictures(): LiveData<List<EstateAndPictures>>

    @Query("SELECT * FROM Estate WHERE estateId LIKE :estateId")
    fun findEstateByEid(estateId: Long): LiveData<List<Estate>>

    @Query("SELECT * FROM Estate WHERE userId_fk LIKE :estateUid")
    fun findEstateByUid(estateUid: String): LiveData<List<Estate>>

    @Query("SELECT * FROM Estate WHERE type LIKE :type")
    fun findByType(type: Int): Estate

    @Query("SELECT * FROM Estate WHERE neighborhood LIKE :neighborhood")
    fun findByNeighborhood(neighborhood: Int): Estate

    @Query("SELECT * FROM Estate WHERE price LIKE :price")
    fun findByPrice(price: String): Estate

    @Query("SELECT * FROM Estate WHERE description LIKE :description")
    fun findByDescription(description: String): Estate

    @Query("SELECT * FROM Estate WHERE sqft LIKE :sqft")
    fun findBySqft(sqft: Int): Estate

    @Query("SELECT * FROM Estate WHERE rooms LIKE :rooms")
    fun findByRooms(rooms: Int): Estate

    @Query("SELECT * FROM Estate WHERE bathrooms LIKE :bathrooms")
    fun findByBathrooms(bathrooms: Int): Estate

    @Query("SELECT * FROM Estate WHERE bedrooms LIKE :bedrooms")
    fun findByBedrooms(bedrooms: Int): Estate

    @Query("SELECT * FROM Estate WHERE available LIKE :available")
    fun findByAvailability(available: Int): Estate

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createEstate(estate: Estate): Long

    @Delete
    fun deleteEstate(estate: Estate)

    @Update
    fun updateEstate(vararg estate: Estate)
}