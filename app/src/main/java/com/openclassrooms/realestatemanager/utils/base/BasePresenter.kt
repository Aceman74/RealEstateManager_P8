/*
 * *
 *  * Created by Lionel Joffray on 29/08/19 22:26
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 29/08/19 22:22
 *  
 */

package com.openclassrooms.realestatemanager.utils.base

/**
 * Created by Lionel JOFFRAY - on 12/06/2019.
 *
 *
 * Class with a BaseView for presenter.
 */
abstract class BasePresenter {

    lateinit var mView: BaseView

    fun attachView(v: BaseView) {
        mView = v
    }

    fun getView(): BaseView {
        return mView
    }
}
