/*
 * *
 *  * Created by Lionel Joffray on 19/09/19 21:47
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 19/09/19 19:27
 *
 */

package com.openclassrooms.realestatemanager.models

import android.content.ContentValues
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.openclassrooms.realestatemanager.models.Estate.Companion.TABLE_NAME
import com.openclassrooms.realestatemanager.utils.Utils

/**
 * Created by Lionel JOFFRAY - on 26/08/2019.
 *
 * Estate class, used for Database.
 */


@Entity(tableName = TABLE_NAME, foreignKeys = [ForeignKey(entity = User::class, parentColumns = arrayOf("userId"), childColumns = arrayOf("userId_fk"))])
data class Estate(
        @PrimaryKey(autoGenerate = true) @ColumnInfo(name = COLUMN_ID) var estateId: Long? = null,
        @ColumnInfo(name = COLUMN_FK) var userId_fk: String,
        @ColumnInfo(name = COLUMN_TYPE) var type: Int,
        @ColumnInfo(name = COLUMN_HOOD) var neighborhood: Int,
        @ColumnInfo(name = COLUMN_PRICE) var price: String,
        @ColumnInfo(name = COLUMN_DESC) var description: String,
        @ColumnInfo(name = COLUMN_SQFT) var sqft: Int,
        @ColumnInfo(name = COLUMN_ROOMS) var rooms: Int,
        @ColumnInfo(name = COLUMN_BATHROOMS) var bathrooms: Int,
        @ColumnInfo(name = COLUMN_BEDROOMS) var bedrooms: Int,
        @ColumnInfo(name = COLUMN_AVAILABLE) var available: Int,
        @ColumnInfo(name = COLUMN_AGENT) var agent: String,
        @ColumnInfo(name = COLUMN_ADD_DATE) var addDate: String,
        @ColumnInfo(name = COLUMN_EDIT_DATE) var editDate: String?,
        @ColumnInfo(name = COLUMN_SOLD_DATE) var soldDate: String?,
        @ColumnInfo(name = COLUMN_LAT) var latitude: Double?,
        @ColumnInfo(name = COLUMN_LONG) var longitude: Double?,
        @ColumnInfo(name = COLUMN_ADDRESS) var fullAddress: String

) {
    companion object {
        const val TABLE_NAME = "estate"
        const val COLUMN_ID = "estateId"
        const val COLUMN_FK = "userId_fk"
        const val COLUMN_TYPE = "type"
        const val COLUMN_HOOD = "neighborhood"
        const val COLUMN_PRICE = "price"
        const val COLUMN_DESC = "description"
        const val COLUMN_SQFT = "sqft"
        const val COLUMN_ROOMS = "rooms"
        const val COLUMN_BATHROOMS = "bathrooms"
        const val COLUMN_BEDROOMS = "bedrooms"
        const val COLUMN_AVAILABLE = "available"
        const val COLUMN_AGENT = "agent"
        const val COLUMN_ADD_DATE = "addDate"
        const val COLUMN_EDIT_DATE = "editDate"
        const val COLUMN_SOLD_DATE = "soldDate"
        const val COLUMN_LAT = "latitude"
        const val COLUMN_LONG = "longitude"
        const val COLUMN_ADDRESS = "address"


        fun fromContentValues(values: ContentValues): Estate {
            val estate = Estate(null, "ContentProvider", 0, 0, "0",
                    "0", 0, 0, 0, 0, 0, "ContentProvider",
                    Utils.todayDate, null, "0", 0.0, 0.0, "ContentProvider")
            if (values.containsKey(COLUMN_ID)) {
                estate.estateId = values.getAsLong(COLUMN_ID)
            }
            if (values.containsKey(COLUMN_FK)) {
                estate.userId_fk = values.getAsString(COLUMN_FK)
            }
            if (values.containsKey(COLUMN_TYPE)) {
                estate.type = values.getAsInteger(COLUMN_TYPE)
            }
            if (values.containsKey(COLUMN_HOOD)) {
                estate.neighborhood = values.getAsInteger(COLUMN_HOOD)
            }
            if (values.containsKey(COLUMN_DESC)) {
                estate.description = values.getAsString(COLUMN_DESC)
            }
            if (values.containsKey(COLUMN_SQFT)) {
                estate.sqft = values.getAsInteger(COLUMN_SQFT)
            }
            if (values.containsKey(COLUMN_ROOMS)) {
                estate.rooms = values.getAsInteger(COLUMN_ROOMS)
            }
            if (values.containsKey(COLUMN_BATHROOMS)) {
                estate.bathrooms = values.getAsInteger(COLUMN_BATHROOMS)
            }
            if (values.containsKey(COLUMN_BEDROOMS)) {
                estate.bedrooms = values.getAsInteger(COLUMN_BEDROOMS)
            }
            if (values.containsKey(COLUMN_AVAILABLE)) {
                estate.available = values.getAsInteger(COLUMN_AVAILABLE)
            }
            if (values.containsKey(COLUMN_AGENT)) {
                estate.agent = values.getAsString(COLUMN_AGENT)
            }
            if (values.containsKey(COLUMN_ADD_DATE)) {
                estate.addDate = values.getAsString(COLUMN_ADD_DATE)
            }
            if (values.containsKey(COLUMN_EDIT_DATE)) {
                estate.editDate = values.getAsString(COLUMN_EDIT_DATE)
            }
            if (values.containsKey(COLUMN_SOLD_DATE)) {
                estate.soldDate = values.getAsString(COLUMN_SOLD_DATE)
            }
            if (values.containsKey(COLUMN_LAT)) {
                estate.latitude = values.getAsDouble(COLUMN_LAT)
            }
            if (values.containsKey(COLUMN_LONG)) {
                estate.longitude = values.getAsDouble(COLUMN_LONG)
            }
            if (values.containsKey(COLUMN_ADDRESS)) {
                estate.fullAddress = values.getAsString(COLUMN_ADDRESS)
            }
            return estate
        }
    }
}

