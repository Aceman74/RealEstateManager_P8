/*
 * *
 *  * Created by Lionel Joffray on 19/09/19 21:47
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 19/09/19 18:59
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

    interface SettingsPresenterInterface {
        fun calculateLoan(mIntArray: DoubleArray, mAmount: Int, mRate: Double, mYears: Int): DoubleArray
    }

    interface SettingsViewInterface : BaseView {

        fun configureView()
        fun configureListeners()
        fun loadSharedPref()
        fun applyNewTheme()
        fun loanSimulation()
    }
}
