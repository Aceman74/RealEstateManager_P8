/*
 * *
 *  * Created by Lionel Joffray on 20/09/19 18:13
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 20/09/19 18:13
 *
 */

package com.openclassrooms.realestatemanager.utils

import com.openclassrooms.realestatemanager.extensions.backSlashRemover
import org.junit.Assert
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

/**
 * Created by Lionel JOFFRAY - on 22/08/2019.
 *
 * Testing all Utils class function made for this Application.
 */

class UtilsTest {

    @Test
    fun getTodayDateTest() {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        dateFormat.format(Date())
        Assert.assertEquals(dateFormat, SimpleDateFormat("dd/MM/yyyy"))
        Assert.assertNotEquals(dateFormat, SimpleDateFormat("MM/dd/yyyy"))
        Assert.assertNotEquals(dateFormat, SimpleDateFormat("yyyy/MM/dd"))
    }

    @Test
    fun convertDollarToEuroTest() {
        val dollars = 20
        val dollars1 = 8
        Assert.assertEquals((dollars * 0.903).roundToInt(), 18)
        Assert.assertEquals((dollars1 * 0.903).roundToInt(), 7)
    }

    @Test
    fun convertEuroToDollarTest() {
        val euros = 20
        val euros1 = 8
        Assert.assertEquals((euros * 1.109).roundToInt(), 22)
        Assert.assertEquals((euros1 * 1.109).roundToInt(), 9)
    }

    @Test
    fun dateToMillisTest() {
        val cal = Calendar.getInstance()
        cal.set(Calendar.YEAR, 1990)
        cal.set(Calendar.MONTH, 10)
        cal.set(Calendar.DAY_OF_MONTH, 29)

        Assert.assertNotNull(cal.timeInMillis)
    }

    @Test
    fun millisToDateTest() {
        val millis = 659880865959
        val dateFormat = SimpleDateFormat("ddMMyyyy")
        val result = Date(millis)

        Assert.assertNotNull(dateFormat.format(result))
        Assert.assertTrue(result.time.toString().contains("659880865959"))
    }

    @Test
    fun dateWithBSToMillisTest() {
        val cal = Calendar.getInstance()
        cal.set(1990, 10, 29)

        val dateString = "29/11/1990"
        val string = String().backSlashRemover(dateString)

        val converted = Utils.dateToMillis(string.substring(4, 8).toInt(), string.substring(2, 4).toInt(), string.substring(0, 2).toInt())
        Assert.assertNotNull(converted)

    }
}
