/*
 * *
 *  * Created by Lionel Joffray on 12/09/19 20:50
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 12/09/19 14:19
 *
 */

package com.openclassrooms.realestatemanager.models.places.nearby_search

import com.google.gson.annotations.SerializedName

class AddressComponent {

    @SerializedName("long_name")
    var longName: String? = null
    @SerializedName("short_name")
    var shortName: String? = null
    @SerializedName("types")
    var types: List<String>? = null

}
