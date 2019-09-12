/*
 * *
 *  * Created by Lionel Joffray on 12/09/19 20:50
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 12/09/19 14:24
 *
 */

package com.openclassrooms.realestatemanager.models.places.nearby_search

import com.google.gson.annotations.SerializedName

class Viewport {

    @SerializedName("northeast")
    var northeast: Northeast? = null
    @SerializedName("southwest")
    var southwest: Southwest? = null

}
