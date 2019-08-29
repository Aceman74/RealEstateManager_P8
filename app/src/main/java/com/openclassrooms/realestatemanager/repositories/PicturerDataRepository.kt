/*
 * *
 *  * Created by Lionel Joffray on 29/08/19 22:26
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 29/08/19 22:22
 *
 */

package com.openclassrooms.realpicturemanager.repositories

import androidx.lifecycle.LiveData
import com.openclassrooms.realestatemanager.database.dao.PictureDao
import com.openclassrooms.realestatemanager.models.Picture


/**
 * Created by Lionel JOFFRAY - on 29/08/2019.
 */
class PicturerDataRepository(val pictureDao: PictureDao) {


    private var mAllData: LiveData<List<Picture>> = pictureDao.getAll()


    // --- GET ---

    fun findAllPicture(): LiveData<List<Picture>> {
        return mAllData
    }

    fun finPictureById(pictureId: Int): LiveData<List<Picture>> {
        return pictureDao.getPictureById(pictureId)
    }

    fun finPictureByEid(pictureEid: Int): LiveData<List<Picture>> {
        return pictureDao.getPictureById(pictureEid)
    }

    fun finPictureByte(pictureByte: ByteArray) {
        pictureDao.getOnePictureByByte(pictureByte)
    }

    // --- CREATE ---

    fun createPicture(picture: Picture) {
        pictureDao.createPicture(picture)
    }

    // --- DELETE ---
    fun deletePicture(picture: Picture) {
        pictureDao.deletePicture(picture)
    }

    // --- UPDATE ---
    fun updatePicture(picture: Picture) {
        pictureDao.updatePicture(picture)
    }
}