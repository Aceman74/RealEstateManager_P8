/*
 * *
 *  * Created by Lionel Joffray on 20/09/19 18:13
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 20/09/19 18:13
 *
 */

package com.openclassrooms.realestatemanager

import android.content.ContentValues
import com.openclassrooms.realestatemanager.models.User

/**
 * Created by Lionel JOFFRAY - on 20/09/2019.
 *
 * This object content functions to generate Estate and User for test.
 */
object ContentProviderTestHepler {
    private val USER_ID: String = "74es9wx1c"
    private val USER_DEMO = User(USER_ID, "Jean Paul", "azerty@qwerty.com", "https://ceci_est_un_test.com", "2019")

    fun generateItem(): ContentValues {
        val values = ContentValues()
        values.put("userId_fk", USER_DEMO.userId)
        values.put("type", 2)
        values.put("neighborhood", 3)
        values.put("price", "50000000")
        values.put("description", "hello")
        values.put("sqft", 20)
        values.put("rooms", 12)
        values.put("bathrooms", 4)
        values.put("bedrooms", 7)
        values.put("available", 1)
        values.put("agent", "Aceman")
        values.put("addDate", "2019")
        values.put("editDate", "2019")
        values.put("soldDate", "2019")
        values.put("latitude", 0.0)
        values.put("longitude", 0.0)
        values.put("address", "42 RedWood NY")
        return values
    }

    fun generateItem2(): ContentValues {
        val values = ContentValues()
        values.put("userId_fk", USER_DEMO.userId)
        values.put("type", 2)
        values.put("neighborhood", 3)
        values.put("price", "50000000")
        values.put("description", "hello")
        values.put("sqft", 20)
        values.put("rooms", 12)
        values.put("bathrooms", 4)
        values.put("bedrooms", 7)
        values.put("available", 1)
        values.put("agent", "Aceman")
        values.put("addDate", "2019")
        values.put("editDate", "2019")
        values.put("soldDate", "2019")
        values.put("latitude", 0.0)
        values.put("longitude", 0.0)
        values.put("address", "42 RedWood NY")
        return values
    }

    fun generateItem3(): ContentValues {
        val values = ContentValues()
        values.put("userId_fk", USER_DEMO.userId)
        values.put("type", 2)
        values.put("neighborhood", 3)
        values.put("price", "50000000")
        values.put("description", "hello")
        values.put("sqft", 20)
        values.put("rooms", 12)
        values.put("bathrooms", 4)
        values.put("bedrooms", 7)
        values.put("available", 1)
        values.put("agent", "Aceman")
        values.put("addDate", "2019")
        values.put("editDate", "2019")
        values.put("soldDate", "2019")
        values.put("latitude", 0.0)
        values.put("longitude", 0.0)
        values.put("address", "42 RedWood NY")
        return values
    }

    fun generateItemUpdate(): ContentValues {
        val values = ContentValues()
        values.put("userId_fk", USER_DEMO.userId)
        values.put("type", 2)
        values.put("neighborhood", 3)
        values.put("price", "50000000")
        values.put("description", "Mr Robot")
        values.put("sqft", 20)
        values.put("rooms", 12)
        values.put("bathrooms", 4)
        values.put("bedrooms", 7)
        values.put("available", 1)
        values.put("agent", "Aceman")
        values.put("addDate", "2019")
        values.put("editDate", "2019")
        values.put("soldDate", "2019")
        values.put("latitude", 0.0)
        values.put("longitude", 0.0)
        values.put("address", "42 RedWood NY")
        return values
    }
}