package com.openclassrooms.realestatemanager.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * Created by Lionel JOFFRAY - on 02/05/2019.
 *
 *
 * User is a user object to save for user data (private).
 * Used for multiple class.
 */
@Entity
data class User(@PrimaryKey var uid: String,
                var username: String,
                var email: String,
                var urlPicture: String?,
                var dateCreated: Date)