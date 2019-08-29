/*
 * *
 *  * Created by Lionel Joffray on 29/08/19 22:26
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 29/08/19 22:22
 *
 */

package com.openclassrooms.realpicturemanager.activities.viewmodels

import android.app.Application
import androidx.annotation.Nullable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.openclassrooms.realestatemanager.database.RealEstateDatabase
import com.openclassrooms.realestatemanager.models.Picture
import com.openclassrooms.realestatemanager.models.User
import com.openclassrooms.realpicturemanager.repositories.PicturerDataRepository
import java.util.concurrent.Executor

/**
 * Created by Lionel JOFFRAY - on 15/08/2019.
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

    fun getPictureById(pictureId: Int): LiveData<List<Picture>> {
        return repository.finPictureById(pictureId)
    }

    fun getPictureByEid(pictureEid: Int): LiveData<List<Picture>> {
        return repository.finPictureByEid(pictureEid)
    }

    fun getPictureByte(byte: ByteArray) {
        executor.execute { repository.finPictureByte(byte) }
    }


    fun createPicture(picture: Picture) {
        executor.execute { repository.createPicture(picture) }
    }

    fun deletePicture(picture: Picture) {
        executor.execute { repository.deletePicture(picture) }
    }

    fun updatePicture(picture: Picture) {
        executor.execute { repository.updatePicture(picture) }
    }
}
