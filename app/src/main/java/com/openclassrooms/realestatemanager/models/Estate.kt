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
@Entity(foreignKeys = [ForeignKey(entity = User::class, parentColumns = arrayOf("UID"), childColumns = arrayOf("UID"))])
data class Estate(
        @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "EID") var eid: Int,
        @ColumnInfo(name = "UID") var uid: String,
        @ColumnInfo(name = "TYPE") var type: Int,
        @ColumnInfo(name = "NEIGHBORHOOD") var neighborhood: Int,
        @ColumnInfo(name = "PRICE") var price: String,
        @ColumnInfo(name = "DESCRIPTION") var description: String,
        @ColumnInfo(name = "SQFT") var sqft: Int,
        @ColumnInfo(name = "ROOMS") var rooms: Int,
        @ColumnInfo(name = "BATHROOMS") var bathrooms: Int,
        @ColumnInfo(name = "BEDROOMS") var bedrooms: Int,
        @ColumnInfo(name = "AVAILABLE") var available: Int)
// var location: Location