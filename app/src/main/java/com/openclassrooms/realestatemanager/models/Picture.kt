/*
 * *
 *  * Created by Lionel Joffray on 29/08/19 22:26
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 29/08/19 22:22
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

@Entity(foreignKeys = [ForeignKey(entity = Estate::class, parentColumns = arrayOf("EID"), childColumns = arrayOf("EID"))])
data class Picture(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "PID") var pid: Int,
                   @ColumnInfo(name = "EID") var eid: Int,
                   @ColumnInfo(name = "NAME") var name: String,
                   @ColumnInfo(typeAffinity = ColumnInfo.BLOB, name = "PICTURE") var picture: ByteArray? = null)