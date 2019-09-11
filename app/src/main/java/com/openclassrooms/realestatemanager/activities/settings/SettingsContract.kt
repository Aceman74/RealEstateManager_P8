/*
 * *
 *  * Created by Lionel Joffray on 11/09/19 20:37
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 11/09/19 14:00
 *
 */

package com.openclassrooms.realestatemanager.activities.settings


import com.openclassrooms.realestatemanager.utils.base.BaseView

/**
 * Created by Lionel JOFFRAY - on 28/05/2019.
 *
 *
 * The contracts for Main Activity.
 */
interface SettingsContract {

    interface SettingsPresenterInterface

    interface SettingsViewInterface : BaseView {

        fun configureView()
        fun configureListeners()
        fun loadSharedPref()
        fun applyNewTheme()
    }
}
