package com.openclassrooms.realestatemanager.data

import com.google.firebase.database.annotations.Nullable
import com.google.firebase.firestore.ServerTimestamp

import java.util.Date

/**
 * Created by Lionel JOFFRAY - on 02/05/2019.
 *
 *
 * User is a user object to save for user data (private).
 * Used for multiple class.
 */
class User(var uid:String, var username:String, var email:String, var urlPicture:String?, var dateCreated:String)