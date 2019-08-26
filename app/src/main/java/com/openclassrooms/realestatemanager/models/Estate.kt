package com.openclassrooms.realestatemanager.models

import android.location.Location
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Created by Lionel JOFFRAY - on 26/08/2019.
 */
@Entity(foreignKeys = [ForeignKey(entity = User::class, parentColumns = arrayOf("eid"), childColumns = arrayOf("uid"))])
data class Estate(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "ID") var eid: Long,
                  var type: String,
                  var neighborhood: String,
                  var price: Int,
                  var surface: Int,
                  var rooms: Int,
                  var bathrooms: Int,
                  var bedrooms: Int,
                  var location: Location)