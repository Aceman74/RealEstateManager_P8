/*
 * *
 *  * Created by Lionel Joffray on 19/09/19 21:47
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 19/09/19 21:35
 *
 */

package com.openclassrooms.realestatemanager.utils

import androidx.multidex.MultiDexApplication
import com.evernote.android.state.StateSaver
import com.openclassrooms.realestatemanager.BuildConfig
import timber.log.Timber

/**
 * Created by Lionel JOFFRAY - on 06/09/2019.
 * Init Timber and EverNote.
 */
class RealEsateManager : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
        StateSaver.setEnabledForAllActivitiesAndSupportFragments(this, true)
    }
}