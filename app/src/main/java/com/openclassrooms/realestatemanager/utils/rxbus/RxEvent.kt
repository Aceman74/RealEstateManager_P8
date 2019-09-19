/*
 * *
 *  * Created by Lionel Joffray on 19/09/19 21:47
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 19/09/19 21:35
 *
 */

package com.openclassrooms.realestatemanager.utils.rxbus

/**
 * Created by Lionel JOFFRAY - on 27/08/2019.
 * Event use with RxBus
 *
 */
class RxEvent {

    data class PickerDescEvent(val desc: String)

    data class PickerPriceEvent(val price: String)

    data class PickerPriceMaxEvent(val priceMax: String)
}