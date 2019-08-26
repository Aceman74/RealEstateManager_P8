package com.openclassrooms.realestatemanager.database

import android.content.ContentValues
import android.content.Context
import androidx.annotation.NonNull
import androidx.room.Database
import androidx.room.OnConflictStrategy
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.openclassrooms.realestatemanager.database.dao.EstateDao
import com.openclassrooms.realestatemanager.database.dao.UserDao
import com.openclassrooms.realestatemanager.models.Estate
import com.openclassrooms.realestatemanager.models.User
import java.util.*

/**
 * Created by Lionel JOFFRAY - on 26/08/2019.
 */
@Database(entities= [User::class, Estate::class],version = 1, exportSchema = false)
abstract class RealEstateDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun itemDao(): EstateDao

    companion object {
        @Volatile private var INSTANCE : RealEstateDatabase? = null

        fun getInstance(context: Context): RealEstateDatabase =
                INSTANCE ?: synchronized(this){
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it}
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        RealEstateDatabase::class.java, "EstateDatabase.db")
                        .addCallback(userPopulate())
                        .addCallback(estatePopulate())
                        .build()

        private fun userPopulate(): Callback {
            return object : Callback() {

                override fun onCreate(@NonNull db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    var date:Date = Date()

                    val contentValues = ContentValues()
                    contentValues.put("uid", 1)
                    contentValues.put("username", "Hubert Bonisseur")
                    contentValues.put("email", "https://risibank.fr/cache/stickers/d112/11248-full.png")
                    contentValues.put("urlPicture", "https://risibank.fr/cache/stickers/d112/11248-full.png")
                    contentValues.put("dateCreated",date.toString())

                    db.insert("User", OnConflictStrategy.IGNORE, contentValues)
                }
            }
        }
        private fun estatePopulate(): Callback {
            return object : Callback() {

                override fun onCreate(@NonNull db: SupportSQLiteDatabase) {
                    super.onCreate(db)

                    val contentValues = ContentValues()
                    contentValues.put("eid", 1)
                    contentValues.put("type", "Duplex")
                    contentValues.put("neighborhood", "Brooklyn")
                    contentValues.put("price", 5000000)
                    contentValues.put("surface",450)
                    contentValues.put("rooms",3)
                    contentValues.put("bathrooms",2)
                    contentValues.put("bedrooms",2)
                    contentValues.put("location",45)

                    db.insert("User", OnConflictStrategy.IGNORE, contentValues)
                }
            }
        }
    }


}