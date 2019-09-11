/*
 * *
 *  * Created by Lionel Joffray on 11/09/19 20:37
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 11/09/19 16:38
 *
 */

package com.openclassrooms.realestatemanager

import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.openclassrooms.realestatemanager.extensions.backSlashRemover
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


/**
 * Created by Philippe on 21/02/2018.
 */

object Utils {

    /**
     * Conversion de la date d'aujourd'hui en un format plus approprié
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @return
     */
    val todayDate: String
        get() {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
            return dateFormat.format(Date())
        }

    /**
     * Conversion d'un prix d'un bien immobilier (Dollars vers Euros)
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @param dollars
     * @return
     */
    fun convertDollarToEuro(dollars: Int): Int {
        return (dollars * 0.903).roundToInt()
    }

    /**
     * Conversion d'un prix d'un bien immobilier (Euros vers Dollars)
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @param euros
     * @return
     */
    fun convertEuroToDollar(euros: Int): Int {
        return (euros * 1.109).roundToInt()
    }

    /**
     * Vérification de la connexion réseau
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @param context
     * @return
     */
    fun isInternetAvailable(context: Context): Boolean {
        var activeNetwork: Boolean
        val wifi = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val network = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        activeNetwork = wifi.isWifiEnabled || network.activeNetworkInfo != null && network.activeNetworkInfo.isConnected
        return activeNetwork
    }

    fun snackBarPreset(view: View, message: String): Unit {
        val snack = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        val snackView = snack.view
        val txtView = snackView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        snackView.setPadding(0, 0, 0, 0)
        txtView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        txtView.setBackgroundResource(R.color.primaryDarkColor)
        return snack.show()
    }

    fun dateToMillis(year: Int, monthOfYear: Int, dayOfMonth: Int): Long {
        var cal = Calendar.getInstance()
        cal.set(Calendar.YEAR, year)
        cal.set(Calendar.MONTH, monthOfYear)
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        return cal.timeInMillis
    }

    fun millisToDate(millis: Long): String {
        val dateFormat = SimpleDateFormat("ddMMyyyy")
        val result = Date(millis)

        return dateFormat.format(result)
    }

    fun dateWithBSToMillis(dateString: String): Long {
        val string = String().backSlashRemover(dateString)

        return dateToMillis(string.substring(4, 8).toInt(), string.substring(2, 4).toInt(), string.substring(0, 2).toInt())
    }

    class ListOfString {

        companion object {
            fun listOfType(): Array<String> {
                return listOf("Apartment", "Castle", "Chalet / Cottage", "Country House", "Hotel",
                        "House", "Island", "Land", "Loft", "Mansion", "Office", "Penthouse",
                        "Residential complex", "Townhouse", "Villa").toTypedArray().sortedArray()
            }

            fun listOfNeighborhood(): Array<String> {

                return listOf("Albany", "Allegany", "Bronx", "Broome", "Cattaraugus", "Cayuga",
                        "Chautauqua", "Chemung", "Chenango", "Clinton", "Columbia", "Cortland",
                        "Delaware", "Dutchess", "Erie", "Essex", "Franklin", "Fulton", "Genesee",
                        "Greene", "Hamilton", "Herkimer", "Jefferson", "Kings (Brooklyn)",
                        "Lewis", "Livingston", "Madison", "Monroe", "Montgomery", "Nassau",
                        "New York (Manhattan)", "Niagara", "Oneida", "Onondaga", "Ontario",
                        "Orange", "Orleans", "Oswego", "Otsego", "Putnam", "Queens", "Rensselaer",
                        "Richmond (Staten Island)", "Rockland", "Saint Lawrence", "Saratoga",
                        "Schenectady", "Schoharie", "Schuyler", "Seneca", "Steuben", "Suffolk",
                        "Sullivan", "Tioga", "Tompkins", "Ulster", "Warren", "Washington",
                        "Wayne", "Westchester", "Wyoming", "Yates").toTypedArray().sortedArray()
            }

            fun listOfAvailable(): Array<String> {
                return listOf("For Sale", "Sold").toTypedArray()
            }

        }

    }
}
