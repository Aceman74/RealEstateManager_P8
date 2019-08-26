package com.openclassrooms.realestatemanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.openclassrooms.realestatemanager.models.Estate
import com.openclassrooms.realestatemanager.models.Picture
import com.openclassrooms.realestatemanager.models.User

/**
 * Created by Lionel JOFFRAY - on 26/08/2019.
 */
@Dao
interface  PictureDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createPicture(picture: PictureDao)

    @Query("SELECT * FROM Picture WHERE pid = :pictureId")
    fun getPicture(pictureId: Long): LiveData<Picture>
}