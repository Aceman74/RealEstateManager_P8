package com.openclassrooms.realestatemanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.openclassrooms.realestatemanager.models.User

/**
 * Created by Lionel JOFFRAY - on 26/08/2019.
 */
@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createUser(user: User)

    @Query("SELECT * FROM User WHERE uid = :userId")
    fun getUser(userId: String): LiveData<User>
}