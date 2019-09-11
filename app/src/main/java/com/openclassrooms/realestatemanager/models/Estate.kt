/*
 * *
 *  * Created by Lionel Joffray on 11/09/19 20:37
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 11/09/19 14:26
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
@Entity(foreignKeys = [ForeignKey(entity = User::class, parentColumns = arrayOf("userId"), childColumns = arrayOf("userId_fk"))])
data class Estate(
        @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "estateId") val estateId: Long? = 0,
        @ColumnInfo(name = "userId_fk") var userId_fk: String,
        @ColumnInfo(name = "type") var type: Int,
        @ColumnInfo(name = "neighborhood") var neighborhood: Int,
        @ColumnInfo(name = "price") var price: String,
        @ColumnInfo(name = "description") var description: String,
        @ColumnInfo(name = "sqft") var sqft: Int,
        @ColumnInfo(name = "rooms") var rooms: Int,
        @ColumnInfo(name = "bathrooms") var bathrooms: Int,
        @ColumnInfo(name = "bedrooms") var bedrooms: Int,
        @ColumnInfo(name = "available") var available: Int,
        @ColumnInfo(name = "agent") var agent: String,
        @ColumnInfo(name = "addDate") var addDate: String,
        @ColumnInfo(name = "editDate") var editDate: String?,
        @ColumnInfo(name = "soldDate") var soldDate: String?,
        @ColumnInfo(name = "latitude") var latitude: Double?,
        @ColumnInfo(name = "longitude") var longitude: Double?,
        @ColumnInfo(name = "address") var fullAddress: String

)