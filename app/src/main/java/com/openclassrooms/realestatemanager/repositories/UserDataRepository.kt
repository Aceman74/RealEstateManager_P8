/*
 * *
 *  * Created by Lionel Joffray on 29/08/19 22:26
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 29/08/19 22:22
 *
 */

package com.openclassrooms.realusermanager.repositories

import androidx.lifecycle.LiveData
import com.openclassrooms.realestatemanager.database.dao.UserDao
import com.openclassrooms.realestatemanager.models.User


/**
 * Created by Lionel JOFFRAY - on 29/08/2019.
 */
class UserDataRepository(val userDao: UserDao) {


    private var mAllData: LiveData<List<User>> = userDao.getAll()


    // --- GET ---

    fun findAllUser(): LiveData<List<User>> {
        return mAllData
    }

    fun finUserById(userid: String): LiveData<List<User>> {
        return userDao.findUserById(userid)
    }

    fun finUserByUsername(username: String) {
        userDao.findByUsername(username)
    }

    fun finByEmail(email: String) {
        userDao.findByEmail(email)
    }

    fun finByDateCreated(date: String) {
        userDao.findByDateCreated(date)
    }

    // --- CREATE ---

    fun createUser(user: User) {
        userDao.createUser(user)
    }

    // --- DELETE ---
    fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }

    // --- UPDATE ---
    fun updateUser(user: User) {
        userDao.updateUser(user)
    }
}