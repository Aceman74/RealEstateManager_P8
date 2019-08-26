package com.openclassrooms.realestatemanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.openclassrooms.realestatemanager.models.Estate
import com.openclassrooms.realestatemanager.models.User

/**
 * Created by Lionel JOFFRAY - on 26/08/2019.
 */
@Dao
interface  EstateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createEstate(estate: Estate)

    @Query("SELECT * FROM Estate WHERE ID = :estateId")
    fun getEstate(estateId: Long): LiveData<Estate>

    @Insert
     fun insertEstate(estate: Estate): Long

    @Update
     fun updateEstate(estate: Estate): Int
}