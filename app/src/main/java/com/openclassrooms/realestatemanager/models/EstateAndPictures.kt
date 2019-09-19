/*
 * *
 *  * Created by Lionel Joffray on 19/09/19 21:47
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 19/09/19 19:27
 *
 */

package com.openclassrooms.realestatemanager.models

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Created by Lionel JOFFRAY - on 02/09/2019.
 *
 * Estate and pictures provide all pictures and nearby related to an Estate ID.
 */
class EstateAndPictures {

    @Embedded
    lateinit var estate: Estate
    @Relation(parentColumn = "estateId",
            entityColumn = "estateId_fk")
    var pictures: List<Picture> = ArrayList()
    @Relation(parentColumn = "estateId",
            entityColumn = "estateId_fk")
    var nearby: List<Nearby> = ArrayList()
}