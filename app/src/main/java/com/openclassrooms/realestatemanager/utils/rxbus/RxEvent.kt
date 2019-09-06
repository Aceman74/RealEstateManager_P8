/*
 * *
 *  * Created by Lionel Joffray on 06/09/19 20:07
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 06/09/19 17:01
 *
 */

package com.openclassrooms.realestatemanager.utils.rxbus

/**
 * Created by Lionel JOFFRAY - on 27/08/2019.
 */
class RxEvent {

    data class PickerDescEvent(val desc: String)

    data class PickerPriceEvent(val price: String)

    data class PickerPriceMaxEvent(val priceMax: String)
}