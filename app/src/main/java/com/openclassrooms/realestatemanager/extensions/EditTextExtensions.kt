/*
 * *
 *  * Created by Lionel Joffray on 06/09/19 20:07
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 06/09/19 12:26
 *
 */

package com.openclassrooms.realestatemanager.extensions

import android.text.InputFilter
import android.widget.EditText

/**
 * Created by Lionel JOFFRAY - on 06/09/2019.
 */
fun EditText.setMaxLength(maxLength: Int) {
    filters = arrayOf(InputFilter.LengthFilter(maxLength))
}