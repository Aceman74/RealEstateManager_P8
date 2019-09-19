/*
 * *
 *  * Created by Lionel Joffray on 19/09/19 21:47
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 19/09/19 19:27
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
 *
 * Injection class for providing database.
 */
class Injection {

    companion object {
        /**
         * provide the datasource
         */
        private fun provideEstateDataSource(context: Context): EstateDataRepository {
            val database = RealEstateDatabase.getInstance(context)
            return EstateDataRepository(database.estateDao())
        }


        /**
         * provide the Executor
         */
        private fun provideExecutor(): Executor {
            return Executors.newSingleThreadExecutor()
        }

        /**
         * provide the ViewModel Factory
         */
        fun provideViewModelFactory(context: Context): ViewModelFactory {
            val dataSourceItem = provideEstateDataSource(context)
            val executor = provideExecutor()
            return ViewModelFactory(executor)
        }
    }
}