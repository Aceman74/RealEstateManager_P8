/*
 * *
 *  * Created by Lionel Joffray on 12/09/19 20:50
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 12/09/19 15:01
 *
 */

package com.openclassrooms.realestatemanager.models

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Created by Lionel JOFFRAY - on 02/09/2019.
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