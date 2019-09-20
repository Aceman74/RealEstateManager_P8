/*
 * *
 *  * Created by Lionel Joffray on 20/09/19 18:13
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 20/09/19 10:34
 *
 */

package com.openclassrooms.realestatemanager.database

import android.content.Context
import androidx.annotation.VisibleForTesting
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
 * The App Database with Room SqLite.
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

        /**
         * Init the database with some Estate for Demo
         * @see DatabasePopulate
         */
        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        RealEstateDatabase::class.java, "EstateDatabase.db")
                        .addCallback(DatabasePopulate.populate())
                        .build()

        /**
         * Switches the internal implementation with an empty in-memory database.
         *
         * @param context The context.
         */
        @VisibleForTesting
        fun switchToInMemory(context: Context) {
            INSTANCE = Room.inMemoryDatabaseBuilder(context.applicationContext,
                    RealEstateDatabase::class.java)
                    .addCallback(DatabasePopulate.userTest())
                    .build()
        }
    }
}