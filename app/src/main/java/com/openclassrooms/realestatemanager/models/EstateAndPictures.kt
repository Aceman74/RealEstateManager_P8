/*
 * *
 *  * Created by Lionel Joffray on 03/09/19 16:31
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 03/09/19 16:31
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
    var estate: Estate? = null
    @Relation(parentColumn = "estateId",
            entityColumn = "estateId_fk")

    var pictures: List<Picture> = ArrayList()
}