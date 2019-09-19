/*
 * *
 *  * Created by Lionel Joffray on 19/09/19 21:47
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 19/09/19 19:34
 *
 */

package com.openclassrooms.realestatemanager.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Created by Lionel JOFFRAY - on 12/09/2019.
 *
 * MapsNearby class, used for Database. Nearest hospitals, police stations and schools.
 */
@Entity(foreignKeys = [ForeignKey(entity = Estate::class, parentColumns = arrayOf("estateId"), childColumns = arrayOf("estateId_fk"), onDelete = ForeignKey.CASCADE)])
data class Nearby(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "nearbyId") val nearbyId: Long? = null,
                  @ColumnInfo(name = "estateId_fk") var estateId_fk: Long,
                  @ColumnInfo(name = "type") var type: String,
                  @ColumnInfo(name = "name") var name: String

)