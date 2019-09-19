/*
 * *
 *  * Created by Lionel Joffray on 19/09/19 21:47
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 19/09/19 18:28
 *
 */

package com.openclassrooms.realestatemanager.activities.estatedetail


import com.openclassrooms.realestatemanager.models.EstateAndPictures
import com.openclassrooms.realestatemanager.utils.base.BaseView

/**
 * Created by Lionel JOFFRAY - on 28/05/2019.
 *
 *
 * The contracts for EstateDetailActivity.
 */
interface EstateDetailContract {

    interface EstateDetailPresenterInterface

    interface EstateViewInterface : BaseView {
        fun configureView()
        fun loadSharedPref()
        fun configureMaps()
        fun configureDetails()
        fun configureRecyclerView()
        fun showNearby(it: List<EstateAndPictures>?)
    }
}
