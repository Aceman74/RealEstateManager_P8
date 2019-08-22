package com.openclassrooms.realestatemanager

import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import androidx.test.core.app.ApplicationProvider
import org.junit.Test

import org.junit.Assert.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

/**
 * Created by Lionel JOFFRAY - on 22/08/2019.
 */

class UtilsTest {

    @Test
    fun getTodayDate() {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
         dateFormat.format(Date())
        assertEquals(dateFormat, SimpleDateFormat("dd/MM/yyyy"))
        assertNotEquals(dateFormat,SimpleDateFormat("MM/dd/yyyy"))
        assertNotEquals(dateFormat,SimpleDateFormat("yyyy/MM/dd"))
    }

    @Test
    fun convertDollarToEuro() {
        val dollars = 20
        val dollars1 = 8
        assertEquals((dollars * 0.903).roundToInt(),18)
        assertEquals((dollars1 * 0.903).roundToInt(),7)
    }

    @Test
    fun convertEuroToDollar() {
        val euros = 20
        val euros1 = 8
        assertEquals((euros * 1.109).roundToInt(),22)
        assertEquals((euros1 * 1.109).roundToInt(),9)
    }

}