/*
 * *
 *  * Created by Lionel Joffray on 17/09/19 23:02
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 17/09/19 15:51
 *  
 */

package com.openclassrooms.realestatemanager.fragments.list


import com.openclassrooms.realestatemanager.utils.base.BaseView


/**
 * Created by Lionel JOFFRAY - on 04/06/2019.
 *
 *
 * Workmates Contracts.
 */
interface ListContract {

    interface ListPresenterInterface

    interface ListViewInterface : BaseView {
        fun loadSharedPref()
        fun configureViewModel()
        fun launchDetailActivity(it: Int)

    }
}
