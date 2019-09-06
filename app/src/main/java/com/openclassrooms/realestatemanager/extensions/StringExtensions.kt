/*
 * *
 *  * Created by Lionel Joffray on 06/09/19 20:07
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 06/09/19 20:07
 *
 */

package com.openclassrooms.realestatemanager.extensions

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