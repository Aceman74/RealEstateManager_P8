/*
 * *
 *  * Created by Lionel Joffray on 11/09/19 20:37
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 11/09/19 20:37
 *
 */

package com.openclassrooms.realestatemanager.extensions

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Lionel JOFFRAY - on 06/09/2019.
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

fun String.priceRemoveSpace(string: String): String {

    val s: StringBuilder = StringBuilder(string.replace(" ", ""))
    return s.toString()
}

fun String.backSlashRemover(string: String): String {
    val s: StringBuilder = StringBuilder(string.replace("/", ""))
    return s.toString()
}

fun String.formatAddress(string: String): String {
    return string.replace(",", "\n")
}

fun String.custromTimeStamp(): String {
    val dateFormat = SimpleDateFormat("ddMM_HH:mm")
    return dateFormat.format(Date()).toString()
}