/*
 * *
 *  * Created by Lionel Joffray on 03/09/19 16:31
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 02/09/19 19:33
 *
 */

package com.openclassrooms.realestatemanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.openclassrooms.realestatemanager.models.User

/**
 * Created by Lionel JOFFRAY - on 26/08/2019.
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