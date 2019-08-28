package com.openclassrooms.realestatemanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.openclassrooms.realestatemanager.models.Estate

/**
 * Created by Lionel JOFFRAY - on 26/08/2019.
 */
@Dao
interface EstateDao {

    @Query("SELECT * FROM Estate")
    fun getAll(): List<Estate>


    @Query("SELECT * FROM Estate WHERE EID LIKE :estateId")
    fun getEstate(estateId: String): LiveData<List<Estate>>

    @Query("SELECT * FROM Estate WHERE TYPE LIKE :type")
    fun findByType(type: Int): Estate

    @Query("SELECT * FROM Estate WHERE NEIGHBORHOOD LIKE :neighborhood")
    fun findByNeighborhood(neighborhood: Int): Estate

    @Query("SELECT * FROM Estate WHERE PRICE LIKE :price")
    fun findByPrice(price: String): Estate

    @Query("SELECT * FROM Estate WHERE DESCRIPTION LIKE :description")
    fun findByDescription(description: String): Estate

    @Query("SELECT * FROM Estate WHERE SQFT LIKE :sqft")
    fun findBySqft(sqft: Int): Estate

    @Query("SELECT * FROM Estate WHERE ROOMS LIKE :rooms")
    fun findByRooms(rooms: Int): Estate

    @Query("SELECT * FROM Estate WHERE BATHROOMS LIKE :bathrooms")
    fun findByBathrooms(bathrooms: Int): Estate

    @Query("SELECT * FROM Estate WHERE BEDROOMS LIKE :bedrooms")
    fun findByBedrooms(bedrooms: Int): Estate

    @Query("SELECT * FROM Estate WHERE AVAILABLE LIKE :available")
    fun findByAvailability(available: Int): Estate

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createEstate(estate: Estate)

    @Delete
    fun deleteEstate(estate: Estate)

    @Update
    fun updateEstate(vararg estate: Estate)
}