/*
 * *
 *  * Created by Lionel Joffray on 20/09/19 18:13
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 20/09/19 14:01
 *
 */

package com.openclassrooms.realestatemanager.activities.settings

import org.junit.Assert
import org.junit.Test
import kotlin.math.pow
import kotlin.math.round

/**
 * Created by Lionel JOFFRAY - on 20/09/2019.
 */
class SettingsPresenterTest {

    @Test
    fun calculateLoan() {
        val mYears = 15
        val mRate = 4.5
        val mAmount = 200000

        val duration: Double = mYears.toDouble() * 12
        val firstVal = mAmount * (mRate / 100) / 12
        val secondVal = 1 - (1 + (mRate / 100) / 12).pow((0 - duration))
        val monthly = firstVal / secondVal
        val interest = (monthly * duration) - mAmount
        val value1 = (round(monthly * 100) / 100)
        val value2 = (round(duration * 100) / 100)
        val value3 = (round(interest * 100) / 100)

        Assert.assertTrue(value1 == 1529.99)
        Assert.assertTrue(value2 == 180.0)
        Assert.assertTrue(value3 == 75397.58)
    }
}