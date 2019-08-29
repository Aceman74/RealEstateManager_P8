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
import androidx.room.PrimaryKey

/**
 * Created by Lionel JOFFRAY - on 02/05/2019.
 *
 *
 * User is a user object to save for user data (private).
 * Used for multiple class.
 */
@Entity
data class User(@PrimaryKey @ColumnInfo(name = "UID") var uid: String,
                @ColumnInfo(name = "USERNAME") var username: String,
                @ColumnInfo(name = "EMAIL") var email: String,
                @ColumnInfo(name = "URLPICTURE") var urlPicture: String?,
                @ColumnInfo(name = "DATECREATED") var dateCreated: String)