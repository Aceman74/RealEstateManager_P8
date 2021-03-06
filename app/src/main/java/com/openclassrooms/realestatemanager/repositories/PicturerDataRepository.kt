/*
 * *
 *  * Created by Lionel Joffray on 21/09/19 12:09
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 21/09/19 12:09
 *
 */

package com.openclassrooms.realestatemanager.repositories

import androidx.lifecycle.LiveData
import com.openclassrooms.realestatemanager.database.dao.PictureDao
import com.openclassrooms.realestatemanager.models.Picture


/**
 * Created by Lionel JOFFRAY - on 29/08/2019.
 *
 * Repository for Picture Data.
 */
class PicturerDataRepository(val pictureDao: PictureDao) {


    private var mAllData: LiveData<List<Picture>> = pictureDao.getAll()


    // --- GET ---

    fun findAllPicture(): LiveData<List<Picture>> {
        return mAllData
    }

    fun findPictureById(pictureId: Long): LiveData<List<Picture>> {
        return pictureDao.getPictureById(pictureId)
    }

    fun findPictureByEid(pictureEid: Long): LiveData<List<Picture>> {
        return pictureDao.getPictureByEid(pictureEid)
    }

    fun findPictureByName(name: String) {
        pictureDao.getOnePictureByName(name)
    }

    fun findPictureByPath(path: String) {
        pictureDao.getOnePictureByPath(path)
    }

    // --- CREATE ---

    fun createPicture(picture: Picture) {
        pictureDao.createPicture(picture)
    }

    // --- DELETE ---
    fun deletePicture(picture: Picture) {
        pictureDao.deletePicture(picture)
    }

    fun deletePictureByEid(pictureEid: Long): Int {
        return pictureDao.deletePictureByEid(pictureEid)
    }
    // --- UPDATE ---
    fun updatePicture(picture: Picture) {
        pictureDao.updatePicture(picture)
    }
}