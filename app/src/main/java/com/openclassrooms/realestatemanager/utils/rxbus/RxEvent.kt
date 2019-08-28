package com.openclassrooms.realestatemanager.utils.rxbus

/**
 * Created by Lionel JOFFRAY - on 27/08/2019.
 */
class RxEvent {

    data class PickerPriceEvent(val price: String, val nbr: Int)

    data class PickerDescEvent(val desc: String, val nbr: Int)
}