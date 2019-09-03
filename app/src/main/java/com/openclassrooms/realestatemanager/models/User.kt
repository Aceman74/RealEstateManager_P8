/*
 * *
 *  * Created by Lionel Joffray on 03/09/19 16:31
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 02/09/19 19:29
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
data class User(@PrimaryKey @ColumnInfo(name = "userId") var userId: String,
                @ColumnInfo(name = "username") var username: String,
                @ColumnInfo(name = "email") var email: String,
                @ColumnInfo(name = "urlPicture") var urlPicture: String?,
                @ColumnInfo(name = "dateCreated") var dateCreated: String)