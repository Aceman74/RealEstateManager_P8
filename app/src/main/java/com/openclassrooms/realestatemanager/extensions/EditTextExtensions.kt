/*
 * *
 *  * Created by Lionel Joffray on 19/09/19 21:47
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 19/09/19 19:10
 *
 */

package com.openclassrooms.realestatemanager.extensions

import android.text.InputFilter
import android.widget.EditText

/**
 * Created by Lionel JOFFRAY - on 06/09/2019.
 * EditText extension for setting max length of var in Number pickers.
 */
fun EditText.setMaxLength(maxLength: Int) {
    filters = arrayOf(InputFilter.LengthFilter(maxLength))
}