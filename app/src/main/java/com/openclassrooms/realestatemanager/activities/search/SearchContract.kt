/*
 * *
 *  * Created by Lionel Joffray on 19/09/19 21:47
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 19/09/19 18:59
 *
 */

package com.openclassrooms.realestatemanager.activities.search


import com.openclassrooms.realestatemanager.models.EstateAndPictures
import com.openclassrooms.realestatemanager.utils.base.BaseView

/**
 * Created by Lionel JOFFRAY - on 28/05/2019.
 *
 *
 * The contracts for Search Activity.
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
