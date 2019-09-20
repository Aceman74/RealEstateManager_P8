/*
 * *
 *  * Created by Lionel Joffray on 20/09/19 18:13
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 20/09/19 12:47
 *
 */

package com.openclassrooms.realestatemanager


import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.openclassrooms.realestatemanager.ContentProviderTestHepler.generateItem
import com.openclassrooms.realestatemanager.ContentProviderTestHepler.generateItem2
import com.openclassrooms.realestatemanager.ContentProviderTestHepler.generateItem3
import com.openclassrooms.realestatemanager.ContentProviderTestHepler.generateItemUpdate
import com.openclassrooms.realestatemanager.database.RealEstateDatabase
import com.openclassrooms.realestatemanager.models.Estate
import com.openclassrooms.realestatemanager.models.User
import com.openclassrooms.realestatemanager.provider.EstateProvider
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Created by Lionel JOFFRAY - on 15/08/2019.
 *
 * This test class test all CRUD request for Content Provider.
 */
@RunWith(AndroidJUnit4::class)
class EstateContentProviderTest {
    private val USER_ID: String = "74es9wx1c"
    private val USER_DEMO = User(USER_ID, "Jean Paul", "azerty@qwerty.com", "https://ceci_est_un_test.com", "2019")

    // FOR DATA
    private lateinit var mContentResolver: ContentResolver

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        RealEstateDatabase.switchToInMemory(context)
        mContentResolver = InstrumentationRegistry.getInstrumentation().context.contentResolver
    }

    @Test
    fun getEstateWhenNoEstateInserted() {
        val cursor = mContentResolver.query(ContentUris.withAppendedId(EstateProvider.URI_ESTATE, 245), null, null, null, null)
        assertThat(cursor, notNullValue())
        assertThat(cursor!!.count, `is`(0))
        cursor.close()
    }


    @Test
    fun getDataBaseEstateCountOnCreation() {
        val cursor = mContentResolver.query(EstateProvider.URI_ESTATE,
                arrayOf(Estate.COLUMN_ID), null, null, null)
        assertThat(cursor, notNullValue())
        assertThat(cursor!!.count, `is`(0))
        cursor.close()
    }

    @Test
    fun insertEstateWithProvider() {
        val itemUri = mContentResolver.insert(EstateProvider.URI_ESTATE,
                generateItem())
        assertThat(itemUri, notNullValue())
        val cursor = mContentResolver.query(EstateProvider.URI_ESTATE,
                arrayOf(Estate.COLUMN_AGENT), null, null, null)
        assertThat(cursor, notNullValue())
        assertThat(cursor!!.count, `is`(1))
        assertThat(cursor.moveToFirst(), `is`(true))
        assertThat(cursor.getString(cursor.getColumnIndexOrThrow(Estate.COLUMN_AGENT)), `is`("Aceman"))
        cursor.close()
    }

    @Test
    fun updateEstateWithProvider() {
        val itemUri = mContentResolver.insert(EstateProvider.URI_ESTATE,
                generateItem())
        assertThat(itemUri, notNullValue())
        val count = mContentResolver.update(itemUri!!,
                generateItemUpdate(), null, null)
        assertThat(count, `is`(1))
        val cursor = mContentResolver.query(EstateProvider.URI_ESTATE,
                arrayOf(Estate.COLUMN_DESC), null, null, null)
        assertThat(cursor, notNullValue())
        assertThat(cursor!!.count, `is`(1))
        assertThat(cursor.moveToFirst(), `is`(true))
        assertThat(cursor.getString(cursor.getColumnIndexOrThrow(Estate.COLUMN_DESC)), `is`("Mr Robot"))
        cursor.close()
    }

    @Test
    fun deleteEstateWithProvider() {
        val itemUri = mContentResolver.insert(EstateProvider.URI_ESTATE,
                generateItem())
        assertThat(itemUri, notNullValue())
        val cursor1 = mContentResolver.query(EstateProvider.URI_ESTATE,
                arrayOf(Estate.COLUMN_ID), null, null, null)
        assertThat(cursor1, notNullValue())
        assertThat(cursor1!!.count, `is`(1))
        cursor1.close()
        val count = mContentResolver.delete(itemUri!!, null, null)
        assertThat(count, `is`(1))
        val cursor2 = mContentResolver.query(EstateProvider.URI_ESTATE,
                arrayOf(Estate.COLUMN_ID), null, null, null)
        assertThat(cursor2, notNullValue())
        assertThat(cursor2!!.count, `is`(0))
        cursor2.close()
    }

    @Test
    fun bulkInsertEstatesWithProvider() {
        val count = mContentResolver.bulkInsert(EstateProvider.URI_ESTATE,
                arrayOf(generateItem(), generateItem2(), generateItem3()))
        assertThat(count, `is`(3))
        val cursor = mContentResolver.query(EstateProvider.URI_ESTATE,
                arrayOf(Estate.COLUMN_ID), null, null, null)
        assertThat(cursor, notNullValue())
        assertThat(cursor!!.count, `is`(3))
        cursor.close()
    }
}