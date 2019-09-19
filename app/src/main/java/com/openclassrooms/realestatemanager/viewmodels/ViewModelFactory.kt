/*
 * *
 *  * Created by Lionel Joffray on 19/09/19 21:47
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 19/09/19 20:20
 *
 */

package com.openclassrooms.realestatemanager.viewmodels


import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.util.concurrent.Executor

/**
 * Created by Lionel JOFFRAY - on 15/08/2019.
 *
 * ViewModel factory .
 */
class ViewModelFactory(private val executor: Executor) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(EstateViewModel::class.java) ->
                return EstateViewModel(Application(), executor) as T
            modelClass.isAssignableFrom(UserViewModel::class.java) ->
                return UserViewModel(Application(), executor) as T
            modelClass.isAssignableFrom(PictureViewModel::class.java) ->
                return PictureViewModel(Application(), executor) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
