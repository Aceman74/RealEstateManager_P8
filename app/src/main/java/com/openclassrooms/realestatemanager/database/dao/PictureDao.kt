package com.openclassrooms.realestatemanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.openclassrooms.realestatemanager.models.Picture

/**
 * Created by Lionel JOFFRAY - on 26/08/2019.
 */
@Dao
interface PictureDao {

    @Query("SELECT * FROM Picture")
    fun getAll(): List<Picture>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createPicture(picture: Picture)

    @Query("SELECT * FROM Picture WHERE pid = :pictureId")
    fun getPicture(pictureId: Long): LiveData<Picture>
}