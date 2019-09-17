/*
 * *
 *  * Created by Lionel Joffray on 17/09/19 23:02
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 17/09/19 16:04
 *
 */

package com.openclassrooms.realestatemanager.utils

import android.content.ContentValues
import androidx.annotation.NonNull
import androidx.room.OnConflictStrategy
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * Created by Lionel JOFFRAY - on 17/09/2019.
 */
class DatabasePopulate {

    companion object {

        fun populate(): RoomDatabase.Callback {
            return object : RoomDatabase.Callback() {

                override fun onCreate(@NonNull db: SupportSQLiteDatabase) {
                    super.onCreate(db)

                    val user1 = ContentValues()
                    user1.put("userId", "user1")
                    user1.put("username", "Agent Smith")
                    user1.put("email", "smith@hotmail.fr")
                    user1.put("urlPicture", "Matrix")
                    user1.put("dateCreated", "2890")

                    db.insert("User", OnConflictStrategy.IGNORE, user1)

                    val estate1 = ContentValues()
                    estate1.put("estateId", 1)
                    estate1.put("userId_fk", 1)
                    estate1.put("type", 1)
                    estate1.put("neighborhood", 23)
                    estate1.put("price", "15 000 000")
                    estate1.put("description", "A stone mansion featuring 25,000 square feet " +
                            "living space on approximately 4.5 acres fully fenced gated property" +
                            " surrounded by other multi million dollar homes and only a short 25" +
                            " minute drive to New York City.")
                    estate1.put("sqft", 25000)
                    estate1.put("rooms", 18)
                    estate1.put("bathrooms", 9)
                    estate1.put("bedrooms", 7)
                    estate1.put("available", 0)
                    estate1.put("agent", "Agent Smith")
                    estate1.put("addDate", "27/08/2019")
                    estate1.put("editDate", "27/08/2019")
                    estate1.put("latitude", 40.666399)
                    estate1.put("longitude", -73.882578)
                    estate1.put("address", "761 New Lots Ave, Brooklyn, NY 11208")

                    db.insert("estate", OnConflictStrategy.IGNORE, estate1)

                    val picture1 = ContentValues()
                    picture1.put("estateId_fk", 1)
                    picture1.put("pictureName", "name")
                    picture1.put("picturePath", "https://pic.le-cdn.com/thumbs/1024x768/04/2/properties/Property-537600000000049900015d64e93a-77166163.jpg")

                    db.insert("picture", OnConflictStrategy.IGNORE, picture1)

                    val picture11 = ContentValues()
                    picture11.put("estateId_fk", 1)
                    picture11.put("pictureName", "name")
                    picture11.put("picturePath", "https://pic.le-cdn.com/thumbs/1024x768/04/3/properties/Property-537600000000049900025d64e93a-77166163.jpg")

                    db.insert("picture", OnConflictStrategy.IGNORE, picture11)

                    val picture12 = ContentValues()
                    picture12.put("estateId_fk", 1)
                    picture12.put("pictureName", "name")
                    picture12.put("picturePath", "https://pic.le-cdn.com/thumbs/1024x768/04/4/properties/Property-537600000000049900035d64e93a-77166163.jpg")

                    db.insert("picture", OnConflictStrategy.IGNORE, picture12)
                    val picture13 = ContentValues()
                    picture13.put("estateId_fk", 1)
                    picture13.put("pictureName", "name")
                    picture13.put("picturePath", "https://pic.le-cdn.com/thumbs/1024x768/04/6/properties/Property-537600000000049900055d64e93a-77166163.jpg")

                    db.insert("picture", OnConflictStrategy.IGNORE, picture13)

                    val nearby1 = ContentValues()
                    nearby1.put("estateId_fk", 1)
                    nearby1.put("type", "School")
                    nearby1.put("name", "super school")

                    db.insert("nearby", OnConflictStrategy.IGNORE, nearby1)

                    val nearby11 = ContentValues()
                    nearby11.put("estateId_fk", 1)
                    nearby11.put("type", "School")
                    nearby11.put("name", "super school 2")

                    db.insert("nearby", OnConflictStrategy.IGNORE, nearby11)

                    val nearby12 = ContentValues()
                    nearby12.put("estateId_fk", 1)
                    nearby12.put("type", "Police Station")
                    nearby12.put("name", "super station")

                    db.insert("nearby", OnConflictStrategy.IGNORE, nearby12)

                    val estate2 = ContentValues()
                    estate2.put("estateId", 2)
                    estate2.put("userId_fk", 1)
                    estate2.put("type", 11)
                    estate2.put("neighborhood", 30)
                    estate2.put("price", "24 540 000")
                    estate2.put("description", "Massive Half-Floor Penthouse home with 176 square " +
                            "foot terrace features a wall of curved glass windows providing unrivaled " +
                            "panoramic views of Hudson River, NY Harbor, Statue of Liberty," +
                            " World Trade Center and Manhattan Skyline.")
                    estate2.put("sqft", 3669)
                    estate2.put("rooms", 7)
                    estate2.put("bathrooms", 3)
                    estate2.put("bedrooms", 3)
                    estate2.put("available", 0)
                    estate2.put("agent", "Agent Smith")
                    estate2.put("addDate", "19/08/2019")
                    estate2.put("editDate", "22/08/2019")
                    estate2.put("latitude", 40.707958)
                    estate2.put("longitude", -74.015317)
                    estate2.put("address", "40 West St, New York, NY 10280")

                    db.insert("estate", OnConflictStrategy.IGNORE, estate2)

                    val picture2 = ContentValues()
                    picture2.put("estateId_fk", 2)
                    picture2.put("pictureName", "name")
                    picture2.put("picturePath", "https://pic.le-cdn.com/thumbs/1024x768/04/1/properties/Property-b2660000000001e2000157b5fd0a-31614642.jpg")

                    db.insert("picture", OnConflictStrategy.IGNORE, picture2)

                    val picture21 = ContentValues()
                    picture21.put("estateId_fk", 2)
                    picture21.put("pictureName", "name")
                    picture21.put("picturePath", "https://pic.le-cdn.com/thumbs/1024x768/04/2/properties/Property-b2660000000001e2000257b5fd0a-31614642.jpg")

                    db.insert("picture", OnConflictStrategy.IGNORE, picture21)

                    val picture22 = ContentValues()
                    picture22.put("estateId_fk", 2)
                    picture22.put("pictureName", "name")
                    picture22.put("picturePath", "https://pic.le-cdn.com/thumbs/1024x768/04/8/properties/Property-b2660000000001e2000857b5fd0a-31614642.jpg")

                    db.insert("picture", OnConflictStrategy.IGNORE, picture22)

                    val picture23 = ContentValues()
                    picture23.put("estateId_fk", 2)
                    picture23.put("pictureName", "name")
                    picture23.put("picturePath", "https://pic.le-cdn.com/thumbs/1024x768/04/10/properties/Property-b2660000000001e2000a57b5fd0a-31614642.jpg")

                    db.insert("picture", OnConflictStrategy.IGNORE, picture23)

                    val nearby2 = ContentValues()
                    nearby2.put("estateId_fk", 2)
                    nearby2.put("type", "School")
                    nearby2.put("name", "super school")

                    db.insert("nearby", OnConflictStrategy.IGNORE, nearby2)

                    val nearby21 = ContentValues()
                    nearby21.put("estateId_fk", 2)
                    nearby21.put("type", "Hospital")
                    nearby21.put("name", "super Hospital")

                    db.insert("nearby", OnConflictStrategy.IGNORE, nearby21)

                    val estate3 = ContentValues()
                    estate3.put("estateId", 3)
                    estate3.put("userId_fk", 1)
                    estate3.put("type", 0)
                    estate3.put("neighborhood", 30)
                    estate3.put("price", "5 995 000")
                    estate3.put("description", "STUNNING UPPER EAST SIDE TRUE FOUR BEDROOM W DINING ROOM\n" +
                            "LIVE AT THE TOP IN THIS BRAND NEW CONSTRUCTION IN THE UPPER EAST SIDE. " +
                            "OPEN VIEWS FROM YOUR APARTMENT AND THE CITYS BEST DINING AT YOUR FINGERTIPS." +
                            " THE PERFECT BUILDING FOR A FAMILY WITH AN OVERSIZED 3500 SQUARE FOOT" +
                            " CHILDREN'S PLAYROOM AND TOP OF THE LINE FINISHES!")
                    estate3.put("sqft", 25833)
                    estate3.put("rooms", 9)
                    estate3.put("bathrooms", 4)
                    estate3.put("bedrooms", 4)
                    estate3.put("available", 1)
                    estate3.put("agent", "Agent Smith")
                    estate3.put("addDate", "19/07/2019")
                    estate3.put("editDate", "22/08/2019")
                    estate3.put("soldDate", "22/08/2019")
                    estate3.put("latitude", 40.768179)
                    estate3.put("longitude", -73.960770)
                    estate3.put("address", "40 West St, New York, NY 10280")

                    db.insert("estate", OnConflictStrategy.IGNORE, estate3)

                    val picture3 = ContentValues()
                    picture3.put("estateId_fk", 3)
                    picture3.put("pictureName", "name")
                    picture3.put("picturePath", "https://pic.le-cdn.com/thumbs/1024x768/04/1/properties/Property-3e670000000001e2000157b5fea6-31614782.jpg")

                    db.insert("picture", OnConflictStrategy.IGNORE, picture3)

                    val picture31 = ContentValues()
                    picture31.put("estateId_fk", 3)
                    picture31.put("pictureName", "name")
                    picture31.put("picturePath", "https://pic.le-cdn.com/thumbs/1024x768/04/2/properties/Property-3e670000000001e2000257b5fea6-31614782.jpg")

                    db.insert("picture", OnConflictStrategy.IGNORE, picture31)

                    val picture32 = ContentValues()
                    picture32.put("estateId_fk", 3)
                    picture32.put("pictureName", "name")
                    picture32.put("picturePath", "https://pic.le-cdn.com/thumbs/1024x768/04/3/properties/Property-3e670000000001e2000357b5fea6-31614782.jpg")

                    db.insert("picture", OnConflictStrategy.IGNORE, picture32)

                    val nearby3 = ContentValues()
                    nearby3.put("estateId_fk", 3)
                    nearby3.put("type", "School")
                    nearby3.put("name", "super school")

                    db.insert("nearby", OnConflictStrategy.IGNORE, nearby3)

                    val nearby31 = ContentValues()
                    nearby31.put("estateId_fk", 3)
                    nearby31.put("type", "Hsopital")
                    nearby31.put("name", "super Hospital")

                    db.insert("nearby", OnConflictStrategy.IGNORE, nearby31)

                    val nearby32 = ContentValues()
                    nearby32.put("estateId_fk", 3)
                    nearby32.put("type", "Police Station")
                    nearby32.put("name", "super station")

                    db.insert("nearby", OnConflictStrategy.IGNORE, nearby32)

                    val estate4 = ContentValues()
                    estate4.put("estateId", 4)
                    estate4.put("userId_fk", 1)
                    estate4.put("type", 8)
                    estate4.put("neighborhood", 19)
                    estate4.put("price", "24 500 000")
                    estate4.put("description", "his extraordinary 28 foot one-of-a-kind townhome " +
                            "combines state-of-the-art modern amenities while leaving the original" +
                            " details intact. Located in the highly coveted Gold Coast of Greenwich " +
                            "Village, this mansion sized Anglo Italianate home with elevator expands " +
                            "over 9,200 square feet and features include 6 bedrooms, 7 full baths," +
                            " 3 half baths and spreads over 5 floors of sumptuous living.")
                    estate4.put("sqft", 9192)
                    estate4.put("rooms", 16)
                    estate4.put("bathrooms", 10)
                    estate4.put("bedrooms", 6)
                    estate4.put("available", 0)
                    estate4.put("agent", "Agent Smith")
                    estate4.put("addDate", "12/08/2019")
                    estate4.put("latitude", 40.733743)
                    estate4.put("longitude", -73.998103)
                    estate4.put("address", "47 W 9th St, New York, NY 10011")

                    db.insert("estate", OnConflictStrategy.IGNORE, estate4)

                    val picture4 = ContentValues()
                    picture4.put("estateId_fk", 4)
                    picture4.put("pictureName", "name")
                    picture4.put("picturePath", "https://pic.le-cdn.com/thumbs/1024x768/08/1/properties/Property-301c852347090ddaff2bda97a2dfe8d3-75546565.jpg")

                    db.insert("picture", OnConflictStrategy.IGNORE, picture4)

                    val picture41 = ContentValues()
                    picture41.put("estateId_fk", 4)
                    picture41.put("pictureName", "name")
                    picture41.put("picturePath", "https://pic.le-cdn.com/thumbs/1024x768/08/3/properties/Property-7c41560bc70b635b6bb450d81b4ee9df-75546565.jpg")

                    db.insert("picture", OnConflictStrategy.IGNORE, picture41)

                    val picture42 = ContentValues()
                    picture42.put("estateId_fk", 4)
                    picture42.put("pictureName", "name")
                    picture42.put("picturePath", "https://pic.le-cdn.com/thumbs/1024x768/08/5/properties/Property-14ca75523a9cf84220af079a49fb13cc-75546565.jpg")

                    db.insert("picture", OnConflictStrategy.IGNORE, picture42)

                    val picture43 = ContentValues()
                    picture43.put("estateId_fk", 4)
                    picture43.put("pictureName", "name")
                    picture43.put("picturePath", "https://pic.le-cdn.com/thumbs/1024x768/08/7/properties/Property-69d015f80fed02f0ffdd6662c1f23fef-75546565.jpg")

                    db.insert("picture", OnConflictStrategy.IGNORE, picture43)

                    val picture44 = ContentValues()
                    picture44.put("estateId_fk", 4)
                    picture44.put("pictureName", "name")
                    picture44.put("picturePath", "https://pic.le-cdn.com/thumbs/1024x768/08/10/properties/Property-800f9407e048771c1662182b5f0bb8d0-75546565.jpg")

                    db.insert("picture", OnConflictStrategy.IGNORE, picture44)

                    val nearby4 = ContentValues()
                    nearby4.put("estateId_fk", 4)
                    nearby4.put("type", "School")
                    nearby4.put("name", "super school")

                    db.insert("nearby", OnConflictStrategy.IGNORE, nearby4)

                    val nearby41 = ContentValues()
                    nearby41.put("estateId_fk", 4)
                    nearby41.put("type", "School")
                    nearby41.put("name", "super school 2")

                    db.insert("nearby", OnConflictStrategy.IGNORE, nearby41)
                    val nearby42 = ContentValues()
                    nearby42.put("estateId_fk", 4)
                    nearby42.put("type", "Police Station")
                    nearby42.put("name", "super station")

                    db.insert("nearby", OnConflictStrategy.IGNORE, nearby42)
                }
            }
        }
    }
}