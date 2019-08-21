package com.openclassrooms.realestatemanager.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

/**
 * Created by Lionel JOFFRAY - on 05/06/2019.
 *
 *
 * DateSetter is used to set a date with history.
 */
object DateSetter {

    private var todayDate: Date? = null
    private var formatter: SimpleDateFormat? = null

    val formattedDate: String
        get() {

            todayDate = Calendar.getInstance().time
            Calendar.getInstance().time
            formatter = SimpleDateFormat("yyyy-MM-dd")

            return formatter!!.format(todayDate!!)
        }
}
