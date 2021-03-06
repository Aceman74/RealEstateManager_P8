/*
 * *
 *  * Created by Lionel Joffray on 23/09/19 21:08
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 23/09/19 21:08
 *
 */

package com.openclassrooms.realestatemanager.viewmodels

import android.app.Application
import androidx.annotation.Nullable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.openclassrooms.realestatemanager.database.RealEstateDatabase
import com.openclassrooms.realestatemanager.models.Picture
import com.openclassrooms.realestatemanager.models.User
import com.openclassrooms.realestatemanager.repositories.PicturerDataRepository
import java.util.concurrent.Executor

/**
 * Created by Lionel JOFFRAY - on 15/08/2019.
 *
 * ViewModel for Picture .
 */
class PictureViewModel(application: Application, val executor: Executor) : AndroidViewModel(application) {

    // DATA
    @Nullable
    private var currentUser: LiveData<User>? = null
    private val repository: PicturerDataRepository
    val allPicture: LiveData<List<Picture>>

    init {
        val pictureDao = RealEstateDatabase.getInstance(application).pictureDao()
        repository = PicturerDataRepository(pictureDao)
        allPicture = repository.findAllPicture()
    }
    // -------------
    // FOR USER
    // -------------

    fun getUser(userId: Long): LiveData<User>? {
        return this.currentUser
    }

    // -------------
    // FOR ITEM
    // -------------

    fun getPictureById(pictureId: Long): LiveData<List<Picture>> {
        return repository.findPictureById(pictureId)
    }

    fun getPictureByEid(pictureEid: Long): LiveData<List<Picture>> {
        return repository.findPictureByEid(pictureEid)
    }

    fun getPictureByName(string: String) {
        executor.execute { repository.findPictureByName(string) }
    }

    fun getPictureByNPath(path: String) {
        executor.execute { repository.findPictureByPath(path) }
    }

    fun createPicture(picture: Picture) {
        executor.execute { repository.createPicture(picture) }
    }

    fun deletePicture(picture: Picture) {
        executor.execute { repository.deletePicture(picture) }
    }

    fun deletePictureEid(pictureEid: Long) {
        executor.execute { repository.deletePictureByEid(pictureEid) }
    }
    fun updatePicture(picture: Picture) {
        executor.execute { repository.updatePicture(picture) }
    }
}
