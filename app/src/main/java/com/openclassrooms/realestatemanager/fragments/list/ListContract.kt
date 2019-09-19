/*
 * *
 *  * Created by Lionel Joffray on 19/09/19 21:47
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 19/09/19 19:15
 *  
 */

package com.openclassrooms.realestatemanager.fragments.list


import com.openclassrooms.realestatemanager.utils.base.BaseView


/**
 * Created by Lionel JOFFRAY - on 04/06/2019.
 *
 *
 * Fragment List Contracts.
 */
interface ListContract {

    interface ListPresenterInterface

    interface ListViewInterface : BaseView {
        fun loadSharedPref()
        fun configureViewModel()
        fun launchDetailActivity(it: Int)

    }
}
