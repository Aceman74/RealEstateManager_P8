/*
 * *
 *  * Created by Lionel Joffray on 17/09/19 23:02
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 17/09/19 16:04
 *
 */

package com.openclassrooms.realestatemanager.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.openclassrooms.realestatemanager.database.dao.EstateDao
import com.openclassrooms.realestatemanager.database.dao.PictureDao
import com.openclassrooms.realestatemanager.database.dao.UserDao
import com.openclassrooms.realestatemanager.models.Estate
import com.openclassrooms.realestatemanager.models.Nearby
import com.openclassrooms.realestatemanager.models.Picture
import com.openclassrooms.realestatemanager.models.User
import com.openclassrooms.realestatemanager.utils.DatabasePopulate

/**
 * Created by Lionel JOFFRAY - on 26/08/2019.
 */
@Database(entities = [User::class, Estate::class, Picture::class, Nearby::class], version = 1, exportSchema = false)
abstract class RealEstateDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun estateDao(): EstateDao
    abstract fun pictureDao(): PictureDao

    companion object {
        @Volatile
        private var INSTANCE: RealEstateDatabase? = null

        fun getInstance(context: Context): RealEstateDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        RealEstateDatabase::class.java, "EstateDatabase.db")
                        .addCallback(DatabasePopulate.populate())
                        .build()
    }

}