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

    @Query("SELECT * FROM User WHERE uid = :userId")
    fun findUserById(userId: String): LiveData<List<User>>

    @Query("SELECT * FROM User WHERE USERNAME LIKE :username")
    fun findByUsername(username: String): User

    @Query("SELECT * FROM User WHERE EMAIL LIKE :email")
    fun findByEmail(email: String): User

    @Query("SELECT * FROM User WHERE URLPICTURE LIKE :urlPicture")
    fun findByUrlPicture(urlPicture: String): User

    @Query("SELECT * FROM User WHERE DATECREATED LIKE :dateCreated")
    fun findByDateCreated(dateCreated: String): User

    @Delete
    fun deleteUser(user: User)

    @Update
    fun updateUser(vararg user: User)
}