/*
 * *
 *  * Created by Lionel Joffray on 12/09/19 20:50
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 12/09/19 14:39
 *
 */

package com.openclassrooms.realestatemanager.models.places.nearby_search

import com.google.gson.annotations.SerializedName

class Result {

    @SerializedName("geometry")
    private var mGeometry: Geometry? = null
    @SerializedName("name")
    var name: String? = null
    @SerializedName("types")
    var types: List<String>? = null


}
