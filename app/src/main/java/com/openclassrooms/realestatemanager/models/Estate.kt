package com.openclassrooms.realestatemanager.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Created by Lionel JOFFRAY - on 26/08/2019.
 */
@Entity(foreignKeys = [ForeignKey(entity = User::class, parentColumns = arrayOf("UID"), childColumns = arrayOf("EID"))])
data class Estate(
        @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "ID") var id: Int = 0,
        @ColumnInfo(name = "EID") var eid: String,
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