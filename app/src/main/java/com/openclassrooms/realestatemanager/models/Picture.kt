/*
 * *
 *  * Created by Lionel Joffray on 16/09/19 21:09
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 13/09/19 11:33
 *
 */

package com.openclassrooms.realestatemanager.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Created by Lionel JOFFRAY - on 26/08/2019.
 */

@Entity(foreignKeys = [ForeignKey(entity = Estate::class, parentColumns = arrayOf("estateId"), childColumns = arrayOf("estateId_fk"), onDelete = ForeignKey.CASCADE)])
data class Picture(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "pictureId") var pictureId: Long? = null,
                   @ColumnInfo(name = "estateId_fk") var estateId_fk: Long,
                   @ColumnInfo(name = "pictureName") var pictureName: String,
                   @ColumnInfo(name = "picturePath") var picturePath: String)