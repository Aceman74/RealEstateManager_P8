/*
 * *
 *  * Created by Lionel Joffray on 29/08/19 22:26
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 29/08/19 22:26
 *
 */

package com.openclassrooms.realestatemanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.openclassrooms.realestatemanager.models.Picture

/**
 * Created by Lionel JOFFRAY - on 26/08/2019.
 */
@Dao
interface PictureDao {

    @Query("SELECT * FROM Picture")
    fun getAll(): LiveData<List<Picture>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createPicture(picture: Picture)

    @Query("SELECT * FROM Picture WHERE PID = :pictureId")
    fun getPictureById(pictureId: Int): LiveData<List<Picture>>

    @Query("SELECT * FROM Picture WHERE EID = :estateId")
    fun getPictureByEid(estateId: Int): LiveData<List<Picture>>

    @Query("SELECT * FROM Picture WHERE PICTURE LIKE :picture")
    fun getOnePictureByByte(picture: ByteArray): Picture

    @Delete
    fun deletePicture(picture: Picture)

    @Update
    fun updatePicture(vararg picture: Picture)
}