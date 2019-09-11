/*
 * *
 *  * Created by Lionel Joffray on 11/09/19 20:37
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 11/09/19 14:26
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
}