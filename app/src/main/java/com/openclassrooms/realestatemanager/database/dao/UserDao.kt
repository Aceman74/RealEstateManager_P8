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

    @Query("SELECT * FROM User")
    fun getAll(): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createUser(user: User)

    @Query("SELECT * FROM User WHERE uid = :userId")
    fun getUser(userId: String): LiveData<User>

    @Query("SELECT * FROM User WHERE USERNAME LIKE :username")
    fun findByUsername(username: String): User

    @Query("SELECT * FROM User WHERE EMAIL LIKE :email")
    fun findByEmail(email: String): User

    @Query("SELECT * FROM User WHERE URLPICTURE LIKE :urlPicture")
    fun findByUrlPicture(urlPicture: String): User

    @Query("SELECT * FROM User WHERE DATECREATED LIKE :dateCreated")
    fun findByDateCreated(dateCreated: String): User
}