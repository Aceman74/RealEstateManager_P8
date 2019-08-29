/*
 * *
 *  * Created by Lionel Joffray on 29/08/19 22:26
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 29/08/19 22:26
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
import com.openclassrooms.realestatemanager.models.User
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Test class for all EstateDao Cases.
 *
 * Created by Lionel JOFFRAY - on 28/08/2019.
 */
@RunWith(AndroidJUnit4::class)
class EstateDaoTest {
    // FOR DATA
    private var database: RealEstateDatabase? = null
    // DATA SET FOR TEST
    private val USER_ID: String = "54gre92xxdf4a"
    private val date = Utils.todayDate
    private val USER_DEMO = User(USER_ID, "Aceman", "azerty@qwerty.fr", "https://ceci_est_un_test.fr", date)
    private val HOTEL_ESTATE = Estate(1,USER_ID, 3, 5, "5 000 000", "Greate Hostel", 850, 20, 20, 20, 0)
    private val HOUSE_ESTATE = Estate(2,USER_ID, 8, 22, "3 333 000", "Greate House", 550, 10, 2, 4, 1)
    private val CASTLE_ESTATE = Estate(3,USER_ID, 12, 28, "13 250 000", "Greate Castle", 950, 12, 4, 7, 0)

    @Rule @JvmField
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
    fun getUserWhenNoParamsInserted() {
        val items = LiveDataTestUtil.getValue(this.database!!.estateDao().findEstateByUid(USER_ID))
        assertTrue(items.isEmpty())
    }

    @Test
    @Throws(InterruptedException::class)
    fun insertAndGetEstate() {
        this.database!!.userDao().createUser(USER_DEMO)
        this.database!!.estateDao().createEstate(HOTEL_ESTATE)
        this.database!!.estateDao().createEstate(HOUSE_ESTATE)
        this.database!!.estateDao().createEstate(CASTLE_ESTATE)

        val items: List<Estate> = LiveDataTestUtil.getValue(this.database!!.estateDao().findEstateByUid(USER_ID))
        assertTrue(items.size == 3)
    }

    @Test
    @Throws(InterruptedException::class)
    fun insertAndUpdateEstate() {
        this.database!!.userDao().createUser(USER_DEMO)
        this.database!!.estateDao().createEstate(HOTEL_ESTATE)

        val itemAdded = LiveDataTestUtil.getValue(this.database!!.estateDao().findEstateByUid(USER_ID)).get(0)
        itemAdded.price = "8 000 000"
        this.database!!.estateDao().updateEstate(itemAdded)

        val items = LiveDataTestUtil.getValue(this.database!!.estateDao().findEstateByUid(USER_ID))
        assertTrue(items.size == 1 && items.get(0).price.equals("8 000 000"))
    }

    @Test
    @Throws(InterruptedException::class)
    fun insertAndDeleteEstate() {
        this.database!!.userDao().createUser(USER_DEMO)
        this.database!!.estateDao().createEstate(HOTEL_ESTATE)

        val itemAdded = LiveDataTestUtil.getValue(this.database!!.estateDao().findEstateByUid(USER_ID)).get(0)
        this.database!!.estateDao().deleteEstate(itemAdded)

        //TEST
        val items = LiveDataTestUtil.getValue(this.database!!.estateDao().findEstateByUid(USER_ID))
        assertTrue(items.isEmpty())
    }

    /**
     * Expected an SQL exception, because of missing the User Foreign Key.
     */
    @Test(expected = SQLiteConstraintException::class)
    @Throws(InterruptedException::class)
    fun insertEstateWithoutUserForeignKey() {
        //  User removed
        this.database!!.estateDao().createEstate(HOTEL_ESTATE)
        this.database!!.estateDao().createEstate(HOUSE_ESTATE)
        this.database!!.estateDao().createEstate(CASTLE_ESTATE)

        val items: List<Estate> = LiveDataTestUtil.getValue(this.database!!.estateDao().findEstateByUid(USER_ID))
        assertTrue(items.size == 3)
    }
}
