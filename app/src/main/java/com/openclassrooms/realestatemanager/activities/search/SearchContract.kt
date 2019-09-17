/*
 * *
 *  * Created by Lionel Joffray on 17/09/19 23:02
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 17/09/19 15:51
 *
 */

package com.openclassrooms.realestatemanager.activities.search


import com.openclassrooms.realestatemanager.models.EstateAndPictures
import com.openclassrooms.realestatemanager.utils.base.BaseView

/**
 * Created by Lionel JOFFRAY - on 28/05/2019.
 *
 *
 * The contracts for Main Activity.
 */
interface SearchContract {

    interface SearchPresenterInterface

    interface SearchViewInterface : BaseView {

        fun configureView()
        fun configureViewModel()
        fun configureItemListeners()
        fun lauchDetailActivity(it: Int)
        fun showNumberPicker(i: Int, mOldVal: Int?, string: String?)
        fun executeFilteredSearch(observePicture: List<EstateAndPictures>): List<EstateAndPictures>
        fun onSearchBtnClick()
        fun loadSharedPref()
    }
}
