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
import com.openclassrooms.realestatemanager.models.User

/**
 * Created by Lionel JOFFRAY - on 26/08/2019.
 *
 * Data Access Object for the User in RealEstateDatabase.
 */
@Dao
interface UserDao {

    @Query("SELECT * FROM User")
    fun getAll(): LiveData<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createUser(user: User)

    @Query("SELECT * FROM User WHERE userId LIKE :userId")
    fun findUserById(userId: String): LiveData<List<User>>

    @Query("SELECT * FROM User WHERE username LIKE :username")
    fun findByUsername(username: String): User

    @Query("SELECT * FROM User WHERE email LIKE :email")
    fun findByEmail(email: String): User

    @Query("SELECT * FROM User WHERE urlPicture LIKE :urlPicture")
    fun findByUrlPicture(urlPicture: String): User

    @Query("SELECT * FROM User WHERE dateCreated LIKE :dateCreated")
    fun findByDateCreated(dateCreated: String): User

    @Delete
    fun deleteUser(user: User)

    @Update
    fun updateUser(vararg user: User)
}