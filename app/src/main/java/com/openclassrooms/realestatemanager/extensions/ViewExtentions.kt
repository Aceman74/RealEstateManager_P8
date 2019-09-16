/*
 * *
 *  * Created by Lionel Joffray on 16/09/19 21:09
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 16/09/19 20:56
 *
 */

package com.openclassrooms.realestatemanager.extensions

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Created by Lionel JOFFRAY - on 16/09/2019.
 */
fun View.hideKeyboard() {
    val inputMethodManager = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    inputMethodManager?.hideSoftInputFromWindow(this.windowToken, 0)
}