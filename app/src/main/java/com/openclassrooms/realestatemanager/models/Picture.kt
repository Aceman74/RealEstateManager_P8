package com.openclassrooms.realestatemanager.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Created by Lionel JOFFRAY - on 26/08/2019.
 */

@Entity(foreignKeys = [ForeignKey(entity = Estate::class, parentColumns = arrayOf("pid"), childColumns = arrayOf("eid"))])
data class Picture(@PrimaryKey(autoGenerate = true) var pid: Long = 0,
                   @ColumnInfo(typeAffinity = ColumnInfo.BLOB) var picture: ByteArray? = null)