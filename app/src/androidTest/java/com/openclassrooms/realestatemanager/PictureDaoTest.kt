/*
 * *
 *  * Created by Lionel Joffray on 16/09/19 21:09
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 16/09/19 16:07
 *
 */

package com.openclassrooms.realestatemanager

import android.database.sqlite.SQLiteConstraintException
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.openclassrooms.realestatemanager.database.RealEstateDatabase
import com.openclassrooms.realestatemanager.models.Estate
import com.openclassrooms.realestatemanager.models.Picture
import com.openclassrooms.realestatemanager.models.User
import com.openclassrooms.realestatemanager.utils.Utils
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Test class for all PictureDao Cases.
 *
 * Created by Lionel JOFFRAY - on 28/08/2019.
 */
@RunWith(AndroidJUnit4::class)
class PictureDaoTest {
    // FOR DATA
    private var database: RealEstateDatabase? = null
    // DATA SET FOR TEST
    private val USER_ID: String = "cacahuete554466"
    private val ESTATE_ID: Long = 1
    private val ESTATE_ID_1: Long = 2
    private val date = Utils.todayDate


    private val USER_DEMO = User(USER_ID, "Aceman", "azerty@qwerty.fr", "https://ceci_est_un_test.fr", date)
    private val HOTEL_ESTATE = Estate(ESTATE_ID, USER_ID, 3, 5, "5 000 000", "Greate Hostel", 850, 20, 20, 20, 0, "bob", date, null, null, 10.1, 10.1, "New York")
    private val HOUSE_ESTATE = Estate(ESTATE_ID_1, USER_ID, 8, 22, "3 333 000", "Greate House", 550, 10, 2, 4, 1, "bob", date, null, null, 10.1, 10.1, "New York")
    private val PICTURE_DEMO = Picture(1, ESTATE_ID, "Photo 1", "//Device")


    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    @Throws(Exception::class)
    fun initDb() {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().context,
                RealEstateDatabase::class.java)
                .allowMainThreadQueries()
                .build()
    }

    @After
    @Throws(Exception::class)
    fun closeDb() {
        database?.close()
    }

    @Test
    @Throws(InterruptedException::class)
    fun insertAndGetPicture() {
        this.database?.userDao()?.createUser(USER_DEMO)
        this.database?.estateDao()?.createEstate(HOTEL_ESTATE)
        this.database?.pictureDao()?.createPicture(PICTURE_DEMO)

        val user = LiveDataTestUtil.getValue(this.database?.userDao()?.findUserById(USER_ID)!!)
        val picture = LiveDataTestUtil.getValue(this.database?.pictureDao()?.getPictureByEid(ESTATE_ID)!!)
        val estate = LiveDataTestUtil.getValue(this.database?.estateDao()?.findEstateByEid(ESTATE_ID)!!)
        assertTrue(picture.get(0).estateId_fk.equals(estate.get(0).estateId) && user.get(0).userId.equals(estate.get(0).userId_fk))
    }

    @Test
    @Throws(InterruptedException::class)
    fun getPictureWhenNoParamsInserted() {
        val items = LiveDataTestUtil.getValue(this.database!!.pictureDao().getPictureByEid(1))
        assertTrue(items.isEmpty())
    }

    @Test
    @Throws(InterruptedException::class)
    fun insertAndUpdatePicture() {
        this.database?.userDao()?.createUser(USER_DEMO)
        this.database?.estateDao()?.createEstate(HOTEL_ESTATE)
        this.database?.pictureDao()?.createPicture(PICTURE_DEMO)

        val itemAdded = LiveDataTestUtil.getValue(this.database!!.pictureDao().getPictureByEid(ESTATE_ID)).get(0)
        itemAdded.pictureName = "BAZINGA"
        this.database!!.pictureDao().updatePicture(itemAdded)

        val items = LiveDataTestUtil.getValue(this.database!!.pictureDao().getPictureByEid(ESTATE_ID))
        assertTrue(items.size == 1 && items.get(0).pictureName.equals("BAZINGA"))
    }

    @Test
    @Throws(InterruptedException::class)
    fun insertAndDeletePicture() {
        this.database?.userDao()?.createUser(USER_DEMO)
        this.database?.estateDao()?.createEstate(HOTEL_ESTATE)
        this.database?.pictureDao()?.createPicture(PICTURE_DEMO)

        val itemAdded = LiveDataTestUtil.getValue(this.database!!.pictureDao().getPictureByEid(ESTATE_ID)).get(0)
        this.database!!.pictureDao().deletePicture(itemAdded)

        //TEST
        val items = LiveDataTestUtil.getValue(this.database!!.pictureDao().getPictureByEid(ESTATE_ID))
        assertTrue(items.isEmpty())
    }

    /**
     * Expected an SQL exception, because of missing the User Foreign Key.
     */
    @Test(expected = SQLiteConstraintException::class)
    @Throws(InterruptedException::class)
    fun insertPictureWithoutEstateForeignKey() {
        //  User removed
        this.database?.estateDao()?.createEstate(HOTEL_ESTATE)
        this.database?.pictureDao()?.createPicture(PICTURE_DEMO)

        val user = LiveDataTestUtil.getValue(this.database?.userDao()?.findUserById(USER_ID)!!)
        val picture = LiveDataTestUtil.getValue(this.database?.pictureDao()?.getPictureByEid(ESTATE_ID)!!)
        val estate = LiveDataTestUtil.getValue(this.database?.estateDao()?.findEstateByEid(ESTATE_ID)!!)
        assertTrue(picture.get(0).estateId_fk.equals(estate.get(0).estateId) && user.get(0).userId.equals(estate.get(0).userId_fk))
    }

    /**
     * Expected an SQL exception, because of missing the User and Estate Foreign Key.
     */
    @Test(expected = SQLiteConstraintException::class)
    @Throws(InterruptedException::class)
    fun insertPictureWithoutEstateAndPictureForeignKey() {
        //  User removed
        //  Estate removed
        this.database?.pictureDao()?.createPicture(PICTURE_DEMO)

        val user = LiveDataTestUtil.getValue(this.database?.userDao()?.findUserById(USER_ID)!!)
        val picture = LiveDataTestUtil.getValue(this.database?.pictureDao()?.getPictureByEid(ESTATE_ID)!!)
        val estate = LiveDataTestUtil.getValue(this.database?.estateDao()?.findEstateByEid(ESTATE_ID)!!)
        assertTrue(picture.get(0).estateId_fk.equals(estate.get(0).estateId) && user.get(0).userId.equals(estate.get(0).userId_fk))
    }
}
