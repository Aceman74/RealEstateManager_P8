/*
 * *
 *  * Created by Lionel Joffray on 03/09/19 16:31
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 02/09/19 19:29
 *
 */

package com.openclassrooms.realestatemanager.injections

import android.content.Context
import com.openclassrooms.realestatemanager.database.RealEstateDatabase
import com.openclassrooms.realestatemanager.repositories.EstateDataRepository
import com.openclassrooms.realestatemanager.viewmodels.ViewModelFactory
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Created by Lionel JOFFRAY - on 29/08/2019.
 */
class Injection {

    companion object {

        fun provideEstateDataSource(context: Context): EstateDataRepository {
            val database = RealEstateDatabase.getInstance(context)
            return EstateDataRepository(database.estateDao())
        }


        fun provideExecutor(): Executor {
            return Executors.newSingleThreadExecutor()
        }

        fun provideViewModelFactory(context: Context): ViewModelFactory {
            val dataSourceItem = provideEstateDataSource(context)
            val executor = provideExecutor()
            return ViewModelFactory(executor)
        }
    }
}