/*
 * *
 *  * Created by Lionel Joffray on 06/09/19 20:07
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 06/09/19 20:07
 *
 */

package com.openclassrooms.realestatemanager.utils

import android.app.Application
import com.evernote.android.state.StateSaver
import com.openclassrooms.realestatemanager.BuildConfig
import timber.log.Timber

/**
 * Created by Lionel JOFFRAY - on 06/09/2019.
 */
class RealEsateManager : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
        StateSaver.setEnabledForAllActivitiesAndSupportFragments(this, true)
    }
}