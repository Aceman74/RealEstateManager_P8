/*
 * *
 *  * Created by Lionel Joffray on 19/09/19 21:47
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 19/09/19 19:07
 *
 */

package com.openclassrooms.realestatemanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.openclassrooms.realestatemanager.models.Picture

/**
 * Created by Lionel JOFFRAY - on 26/08/2019.
 *
 * Data Access Object for the Picture in RealEstateDatabase.
 */
@Dao
interface PictureDao {

    @Query("SELECT * FROM Picture")
    fun getAll(): LiveData<List<Picture>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createPicture(picture: Picture)

    @Query("SELECT * FROM Picture WHERE pictureId LIKE :pictureId")
    fun getPictureById(pictureId: Long): LiveData<List<Picture>>

    @Query("SELECT * FROM Picture WHERE estateId_fk LIKE :estateId_fk")
    fun getPictureByEid(estateId_fk: Long): LiveData<List<Picture>>

    @Query("SELECT * FROM Picture WHERE pictureName LIKE :pictureName")
    fun getOnePictureByName(pictureName: String): Picture

    @Query("SELECT * FROM Picture WHERE picturePath LIKE :picturePath")
    fun getOnePictureByPath(picturePath: String): Picture

    @Delete
    fun deletePicture(picture: Picture)

    @Update
    fun updatePicture(vararg picture: Picture)
}