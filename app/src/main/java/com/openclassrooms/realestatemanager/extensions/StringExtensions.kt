/*
 * *
 *  * Created by Lionel Joffray on 23/09/19 21:08
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 23/09/19 17:07
 *
 */

package com.openclassrooms.realestatemanager.extensions

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Lionel JOFFRAY - on 06/09/2019.
 * Mutliple Strings extensions.
 * AddSpace for adding space to prices, for better UX.
 */
fun String.priceAddSpace(string: String): String {
    val s: StringBuilder = StringBuilder(string.replace(" ", ""))
    var i = 3
    while (i < s.length) {
        s.insert(s.length - i, " ")
        i += 4
    }
    return s.toString()
}

/**
 * Remove price space for UX.
 */
fun String.priceRemoveSpace(string: String): String {

    val s: StringBuilder = StringBuilder(string.replace(" ", ""))
    return s.toString()
}

/**
 * Remove backslash to convert date saved to millis.
 */
fun String.backSlashRemover(string: String): String {
    val s: StringBuilder = StringBuilder(string.replace("/", ""))
    return s.toString()
}

/**
 * Go to line when , is here.
 */
fun String.formatAddress(string: String): String {
    return string.replace(",", "\n")
}

/**
 * Used to save images, may be still usefull.
 */
fun String.customTimeStamp(): String {
    val dateFormat = SimpleDateFormat("HH:mm:ss_")
    return dateFormat.format(Date()).toString()
}