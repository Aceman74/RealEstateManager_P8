/*
 * *
 *  * Created by Lionel Joffray on 29/08/19 22:26
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 29/08/19 22:22
 *
 */

package com.openclassrooms.realusermanager.activities.viewmodels

import android.app.Application
import androidx.annotation.Nullable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.openclassrooms.realestatemanager.database.RealEstateDatabase
import com.openclassrooms.realestatemanager.models.User
import com.openclassrooms.realusermanager.repositories.UserDataRepository
import java.util.concurrent.Executor

/**
 * Created by Lionel JOFFRAY - on 15/08/2019.
 */
class UserViewModel(application: Application, val executor: Executor) : AndroidViewModel(application) {

    // DATA
    @Nullable
    private var currentUser: LiveData<User>? = null
    private val repository: UserDataRepository
    val allUsers: LiveData<List<User>>

    init {
        val userDao = RealEstateDatabase.getInstance(application).userDao()
        repository = UserDataRepository(userDao)
        allUsers = repository.findAllUser()
    }
    // -------------
    // FOR USER
    //  TODO
    // -------------

    fun getUser(userId: Long): LiveData<User>? {
        return this.currentUser
    }

    // -------------
    // FOR ITEM
    // -------------

    fun getUserById(userId: String): LiveData<List<User>> {
        return repository.finUserById(userId)
    }

    fun getUserByUsername(username: String) {
        executor.execute { repository.finUserByUsername(username) }
    }

    fun getUserByEmail(email: String) {
        executor.execute { repository.finByEmail(email) }
    }

    fun getUserByDate(date: String) {
        executor.execute { repository.finByDateCreated(date) }
    }

    fun createUser(user: User) {
        executor.execute { repository.createUser(user) }
    }

    fun deleteUser(user: User) {
        executor.execute { repository.deleteUser(user) }
    }

    fun updateUser(user: User) {
        executor.execute { repository.updateUser(user) }
    }
}
