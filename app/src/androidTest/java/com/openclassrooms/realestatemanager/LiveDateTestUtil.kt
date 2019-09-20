/*
 * *
 *  * Created by Lionel Joffray on 20/09/19 18:13
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 20/09/19 12:47
 *
 */

package com.openclassrooms.realestatemanager

import androidx.annotation.Nullable
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


/**
 * Created by Lionel JOFFRAY - on 28/08/2019.
 *
 * Utils class for livedata Testing in
 * @see EstateDaoTest
 */
object LiveDataTestUtil {
    @Throws(InterruptedException::class)
    fun <T> getValue(liveData: LiveData<T>): T {
        val data = arrayOfNulls<Any>(1)
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(@Nullable o: T) {
                data[0] = o
                latch.countDown()
                liveData.removeObserver(this)
            }
        }
        liveData.observeForever(observer)
        latch.await(2, TimeUnit.SECONDS)

        return data[0] as T
    }
}