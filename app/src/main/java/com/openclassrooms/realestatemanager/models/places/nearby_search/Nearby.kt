/*
 * *
 *  * Created by Lionel Joffray on 12/09/19 20:50
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 12/09/19 14:22
 *
 */

package com.openclassrooms.realestatemanager.models.places.nearby_search

import com.google.gson.annotations.SerializedName

class Nearby {

    @SerializedName("results")
    var results: List<Result>? = null
    @SerializedName("status")
    var status: String? = null

}
