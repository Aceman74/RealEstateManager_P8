/*
 * *
 *  * Created by Lionel Joffray on 12/09/19 20:50
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 12/09/19 14:41
 *
 */

package com.openclassrooms.realestatemanager.models.places.nearby_search

import com.google.gson.annotations.SerializedName

class Geometry {

    @SerializedName("location")
    var location: Location? = null
    @SerializedName("viewport")
    var viewport: Viewport? = null

}
