/*
 * *
 *  * Created by Lionel Joffray on 29/08/19 22:26
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 29/08/19 22:22
 *
 */

package com.openclassrooms.realestatemanager

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.openclassrooms.realestatemanager.database.RealEstateDatabase
import com.openclassrooms.realestatemanager.models.Estate
import com.openclassrooms.realestatemanager.models.User
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Test class for all User Cases.
 *
 * Created by Lionel JOFFRAY - on 28/08/2019.
 */
@RunWith(AndroidJUnit4::class)
class UserDaoTest {
    // FOR DATA
    private var database: RealEstateDatabase? = null
    // DATA SET FOR TEST
    private val USER_ID: String = "74es9wx1c"
    private val USER_ID_1: String = "ez994q23aklV"
    private val date = Utils.todayDate
    private val USER_DEMO = User(USER_ID, "Jean Paul", "azerty@qwerty.com", "https://ceci_est_un_test.com", date)
    private val USER_DEMO_1 = User(USER_ID_1, "Jean Paul 2", "pape@qwerty.com", "https://ceci_est_un_pape.com", date)
    private val HOTEL_ESTATE = Estate(1, USER_ID, 3, 5, "5 000 000", "Greate Hostel", 850, 20, 20, 20, 0)

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
    fun insertAndGetUser() {
        this.database?.userDao()?.createUser(USER_DEMO)

        val user = LiveDataTestUtil.getValue(this.database?.userDao()?.findUserById(USER_ID)!!)
        assertTrue(user.get(0).username.equals(USER_DEMO.username) && user.get(0).uid.equals(USER_ID))
    }

    @Test
    @Throws(InterruptedException::class)
    fun insertAndGetMultipleUsers() {
        this.database!!.userDao().createUser(USER_DEMO)
        this.database!!.userDao().createUser(USER_DEMO_1)

        val items = LiveDataTestUtil.getValue(this.database!!.userDao().getAll())
        assertTrue(items.size == 2)
    }

    @Test
    @Throws(InterruptedException::class)
    fun getUserWhenNoParamsInserted() {
        val items = LiveDataTestUtil.getValue(this.database!!.userDao().findUserById(USER_ID))
        assertTrue(items.isEmpty())
    }

    @Test
    @Throws(InterruptedException::class)
    fun getEstateByUserId() {
        this.database!!.userDao().createUser(USER_DEMO)
        this.database!!.estateDao().createEstate(HOTEL_ESTATE)

        val items: List<Estate> = LiveDataTestUtil.getValue(this.database!!.estateDao().findEstateByUid(USER_ID))

        assertTrue(items.get(0).uid.equals(USER_ID))
    }

    @Test
    @Throws(InterruptedException::class)
    fun insertAndUpdateUser() {
        this.database!!.userDao().createUser(USER_DEMO)

        val itemAdded = LiveDataTestUtil.getValue(this.database!!.userDao().findUserById(USER_ID)).get(0)
        itemAdded.username = "Morpheus"
        this.database!!.userDao().updateUser(itemAdded)

        val items = LiveDataTestUtil.getValue(this.database!!.userDao().findUserById(USER_ID))
        assertTrue(items.size == 1 && items.get(0).username.equals("Morpheus"))
    }

    @Test
    @Throws(InterruptedException::class)
    fun insertAndDeleteUser() {
        this.database!!.userDao().createUser(USER_DEMO)

        val itemAdded = LiveDataTestUtil.getValue(this.database!!.userDao().findUserById(USER_ID)).get(0)
        this.database!!.userDao().deleteUser(itemAdded)

        //TEST
        val items = LiveDataTestUtil.getValue(this.database!!.userDao().findUserById(USER_ID))
        assertTrue(items.isEmpty())
    }
}
