/*
 * *
 *  * Created by Lionel Joffray on 24/09/19 11:30
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 24/09/19 11:30
 *
 */

package com.openclassrooms.realestatemanager.extensions

import org.junit.Assert
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Lionel JOFFRAY - on 20/09/2019.
 * Test class for all extensions Tests.
 */
class ExtensionsTest {

    @Test
    fun stringPriceAddSpaceTest() {
        val stringBase = "25000000"
        val stringResult = String().priceAddSpace(stringBase)

        Assert.assertNotNull(stringResult)
        Assert.assertEquals("25 000 000", stringResult)
    }

    @Test
    fun stringPriceRemoveSpaceTest() {
        val stringBase = "25 000 000"
        val stringResult = String().priceRemoveSpace(stringBase)

        Assert.assertNotNull(stringResult)
        Assert.assertEquals("25000000", stringResult)
    }

    @Test
    fun stringBackSlashRemoverTest() {
        val stringBase = "29/11/1990/abc/def/"
        val stringResult = String().backSlashRemover(stringBase)

        Assert.assertNotNull(stringResult)
        Assert.assertEquals("29111990abcdef", stringResult)
    }

    @Test
    fun stringFormatAddressTest() {
        val stringBase = "74144, New York, 9th street"
        val stringResult = String().formatAddress(stringBase)

        Assert.assertNotNull(stringResult)
        Assert.assertEquals("74144\n New York\n 9th street", stringResult)
    }

    @Test
    fun stringCustomTimeStampTest() {
        val dateFormat = SimpleDateFormat("HH:mm:ss_")
        val date = Date()

        val stringResult = String().customTimeStamp()
        Assert.assertNotNull(stringResult)
        Assert.assertTrue(stringResult == dateFormat.format(date))

    }
}