/*
 * *
 *  * Created by Lionel Joffray on 19/09/19 21:47
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 19/09/19 21:35
 *
 */

package com.openclassrooms.realestatemanager.utils


import android.app.Dialog
import android.os.Bundle
import android.text.InputType
import android.widget.DatePicker
import android.widget.EditText
import android.widget.NumberPicker
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.openclassrooms.realestatemanager.extensions.priceAddSpace
import com.openclassrooms.realestatemanager.extensions.setMaxLength
import com.openclassrooms.realestatemanager.utils.rxbus.RxBus
import com.openclassrooms.realestatemanager.utils.rxbus.RxEvent
import java.util.*


/**
 * This class is used in AddEstate, Search and EditEstate.
 * All pickers are from here, and are set for this app.
 */
class NumberPickerDialog(val i: Int, private var mOldVal: Int?, val s: String? = null) : DialogFragment() {
    private lateinit var valueChangeListener: NumberPicker.OnValueChangeListener
    private lateinit var dateChangeListener: DatePicker.OnDateChangedListener
    var string: String = ""

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val numberPicker = NumberPicker(activity)
        val editText = EditText(activity)
        val builder = AlertDialog.Builder(requireContext())
        val datePicker = DatePicker(activity)

        when (i) {
            1 -> {
                numberPicker.minValue = 0
                numberPicker.maxValue = 14
                numberPicker.displayedValues =
                        Utils.ListOfString.listOfType()

                builder.setTitle("Type")
                builder.setMessage("Choose the type of the estate :")
                numberPicker.tag = 1

            }
            2 -> {
                numberPicker.minValue = 0
                numberPicker.maxValue = 61
                numberPicker.displayedValues =
                        Utils.ListOfString.listOfNeighborhood()

                builder.setTitle("Neighborhood")
                builder.setMessage("Choose the neighborhood :")
                numberPicker.tag = 2
            }
            3 -> {
                builder.setTitle("Price")
                builder.setMessage("Set the price :")
                editText.maxLines = 1
                editText.setMaxLength(9)
                editText.setText(s)
                editText.inputType = InputType.TYPE_CLASS_NUMBER
                numberPicker.tag = 3
                editText.tag = 3

            }
            4 -> {
                builder.setTitle("Description")
                builder.setMessage("describe the estate :")
                editText.maxLines = 4
                editText.setText(s)
                numberPicker.tag = 4
                editText.tag = 4

            }
            5 -> {
                numberPicker.minValue = 0
                numberPicker.maxValue = 50000

                builder.setTitle("Square Feet")
                builder.setMessage("Choose square feet :")
                numberPicker.tag = 5
            }
            6 -> {
                numberPicker.minValue = 0
                numberPicker.maxValue = 30

                builder.setTitle("Rooms")
                builder.setMessage("Choose the number of rooms :")
                numberPicker.tag = 6
            }
            7 -> {
                numberPicker.minValue = 0
                numberPicker.maxValue = 30

                builder.setTitle("Bathrooms")
                builder.setMessage("Choose the number of bathrooms :")
                numberPicker.tag = 7
            }
            8 -> {
                numberPicker.minValue = 0
                numberPicker.maxValue = 30

                builder.setTitle("Bedrooms")
                builder.setMessage("Choose the number of bedrooms :")
                numberPicker.tag = 8
            }
            9 -> {
                numberPicker.minValue = 0
                numberPicker.maxValue = 1
                numberPicker.displayedValues =
                        Utils.ListOfString.listOfAvailable()

                builder.setTitle("Available")
                builder.setMessage("Choose availability :")
                numberPicker.tag = 9
            }
            10 -> {
                val minDate: Calendar = Calendar.getInstance()
                minDate.add(Calendar.YEAR, -2)
                datePicker.minDate = minDate.timeInMillis
                datePicker.maxDate = Calendar.getInstance().timeInMillis
                builder.setTitle("Date")
                builder.setMessage("Added from :")
                datePicker.tag = 10
            }
            11 -> {
                val minDate: Calendar = Calendar.getInstance()
                minDate.add(Calendar.YEAR, -2)
                datePicker.minDate = minDate.timeInMillis
                datePicker.maxDate = Calendar.getInstance().timeInMillis
                builder.setTitle("Date")
                builder.setMessage("Added to :")
                datePicker.tag = 11
            }
            12 -> {
                val minDate: Calendar = Calendar.getInstance()
                minDate.add(Calendar.YEAR, -2)
                datePicker.minDate = minDate.timeInMillis
                datePicker.maxDate = Calendar.getInstance().timeInMillis
                builder.setTitle("Date")
                builder.setMessage("Sold from :")
                datePicker.tag = 12
            }
            13 -> {
                val minDate: Calendar = Calendar.getInstance()
                minDate.add(Calendar.YEAR, -2)
                datePicker.minDate = minDate.timeInMillis
                datePicker.maxDate = Calendar.getInstance().timeInMillis
                builder.setTitle("Date")
                builder.setMessage("Sold to :")
                datePicker.tag = 13
            }
            14 -> {
                editText.maxLines = 1
                editText.setMaxLength(9)
                editText.setText(s)
                editText.inputType = InputType.TYPE_CLASS_NUMBER
                builder.setTitle("Price")
                builder.setMessage("Min price :")
                numberPicker.tag = 14
                editText.tag = 14
            }
            15 -> {
                editText.maxLines = 1
                editText.setMaxLength(9)
                editText.setText(s)
                editText.inputType = InputType.TYPE_CLASS_NUMBER
                builder.setTitle("Price")
                builder.setMessage("Max price :")
                numberPicker.tag = 15
                editText.tag = 15
            }
            16 -> {
                numberPicker.minValue = 0
                numberPicker.maxValue = 8
                builder.setTitle("Picture")
                builder.setMessage("Between :")
                numberPicker.tag = 16
            }
            17 -> {
                numberPicker.minValue = 0
                numberPicker.maxValue = 8
                builder.setTitle("Picture")
                builder.setMessage("And :")
                numberPicker.tag = 17
            }
            18 -> {
                numberPicker.minValue = 0
                numberPicker.maxValue = 50000
                builder.setTitle("Sqft")
                builder.setMessage("Min sqft :")
                numberPicker.tag = 18
            }
            19 -> {
                numberPicker.minValue = 0
                numberPicker.maxValue = 50000
                builder.setTitle("Sqft")
                builder.setMessage("Max sqft :")
                numberPicker.tag = 19
            }

        }

        if (mOldVal != null)
            numberPicker.value = mOldVal as Int

        builder.setPositiveButton("OK") { dialog, which ->

            when (i) {
                3 -> {
                    string = editText.text.toString()
                    string = String().priceAddSpace(string)
                    RxBus.publish(RxEvent.PickerPriceEvent(string))
                }
                4 -> {
                    string = editText.text.toString()
                    RxBus.publish(RxEvent.PickerDescEvent(string))
                }
                14 -> {
                    string = editText.text.toString()
                    string = String().priceAddSpace(string)
                    RxBus.publish(RxEvent.PickerPriceEvent(string))
                }
                15 -> {
                    string = editText.text.toString()
                    string = String().priceAddSpace(string)
                    RxBus.publish(RxEvent.PickerPriceMaxEvent(string))
                }
                10, 11, 12, 13 -> {
                    dateChangeListener.onDateChanged(datePicker, datePicker.year, datePicker.month, datePicker.dayOfMonth)
                }
            }

            valueChangeListener.onValueChange(numberPicker,
                    mOldVal!!, numberPicker.value)

            Utils.snackBarPreset(activity!!.findViewById(android.R.id.content), "Success")
        }

        builder.setNegativeButton("CANCEL") { dialog, which ->
            Utils.snackBarPreset(activity!!.findViewById(android.R.id.content), "Cancel")
        }

        builder.setNeutralButton("RESET") { dialog, which ->

            when (i) {
                3 -> {
                    string = ""
                    RxBus.publish(RxEvent.PickerPriceEvent(string))
                }
                4 -> {
                    string = ""
                    RxBus.publish(RxEvent.PickerDescEvent(string))
                }
                14 -> {
                    string = ""
                    RxBus.publish(RxEvent.PickerPriceEvent(string))
                }
                15 -> {
                    string = ""
                    RxBus.publish(RxEvent.PickerPriceMaxEvent(string))
                }
                10, 11, 12, 13 -> {
                    datePicker.updateDate(2, 11, 31)
                    dateChangeListener.onDateChanged(datePicker, datePicker.year, datePicker.month, datePicker.dayOfMonth)
                    Utils.snackBarPreset(activity!!.findViewById(android.R.id.content), "Reset")
                }
            }

            valueChangeListener.onValueChange(numberPicker,
                    mOldVal!!, 0)
        }


        when (i) {
            1, 2, 5, 6, 7, 8, 9, 16, 17, 18, 19 -> {
                builder.setView(numberPicker)
            }
            3, 4, 14, 15 -> {
                builder.setView(editText)
            }
            10, 11, 12, 13 -> {
                builder.setView(datePicker)
            }
        }
        return builder.create()
    }

    fun getValueChangeListener(): NumberPicker.OnValueChangeListener {
        return valueChangeListener
    }

    fun setValueChangeListener(valueChangeListener: NumberPicker.OnValueChangeListener) {
        this.valueChangeListener = valueChangeListener
    }

    fun setDateChangeListener(dateChangeListener: DatePicker.OnDateChangedListener) {
        this.dateChangeListener = dateChangeListener
    }
}
