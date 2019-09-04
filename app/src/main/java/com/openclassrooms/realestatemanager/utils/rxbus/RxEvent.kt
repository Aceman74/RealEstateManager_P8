/*
 * *
 *  * Created by Lionel Joffray on 04/09/19 19:35
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 04/09/19 15:22
 *
 */

package com.openclassrooms.realestatemanager.utils.rxbus

/**
 * Created by Lionel JOFFRAY - on 27/08/2019.
 */
class RxEvent {

    data class PickerPriceEvent(val price: String)

    data class PickerDescEvent(val desc: String)
}