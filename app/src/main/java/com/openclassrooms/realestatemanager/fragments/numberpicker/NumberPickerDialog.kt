/*
 * *
 *  * Created by Lionel Joffray on 04/09/19 19:35
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 04/09/19 19:34
 *
 */

package com.openclassrooms.realestatemanager.fragments.numberpicker


import android.app.Dialog
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.NumberPicker
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.openclassrooms.realestatemanager.Utils
import com.openclassrooms.realestatemanager.utils.rxbus.RxBus
import com.openclassrooms.realestatemanager.utils.rxbus.RxEvent
import io.reactivex.disposables.Disposable


/**
 * A simple [Fragment] subclass.
 */
class NumberPickerDialog(val i: Int, private var mOldVal: Int?) : DialogFragment() {
    private lateinit var valueChangeListener: NumberPicker.OnValueChangeListener
    var string: String = ""
    private lateinit var pickerDisposable: Disposable

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val numberPicker = NumberPicker(activity)
        val editText = EditText(activity)
        val builder = AlertDialog.Builder(requireContext())

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
                numberPicker.tag = 3
                editText.tag = 3
                editText.inputType = InputType.TYPE_CLASS_NUMBER

            }
            4 -> {
                builder.setTitle("Description")
                builder.setMessage("describe the estate :")
                numberPicker.tag = 4
                editText.tag = 4

            }
            5 -> {
                numberPicker.minValue = 5
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

        }
        when {
            id == 3 -> {
                pickerDisposable = RxBus.listen(RxEvent.PickerPriceEvent::class.java).subscribe {
                    editText.setText(it.price)
                }
            }
            id == 4 -> {
                pickerDisposable = RxBus.listen(RxEvent.PickerDescEvent::class.java).subscribe {
                    editText.setText(it.desc)
                }

            }
        }
        if (mOldVal != null)
            numberPicker.value = mOldVal as Int

        builder.setPositiveButton("OK") { dialog, which ->

            when (i) {
                3 -> {
                    string = editText.text.toString()
                    string = Utils.priceSpace(string)
                    RxBus.publish(RxEvent.PickerPriceEvent(string))
                }
                4 -> {
                    string = editText.text.toString()
                    RxBus.publish(RxEvent.PickerDescEvent(string))
                }
            }
            valueChangeListener.onValueChange(numberPicker,
                    numberPicker.value, numberPicker.value)
            Utils.snackBarPreset(activity!!.findViewById(android.R.id.content), "Success")
        }

        builder.setNegativeButton("CANCEL") { dialog, which ->
            Utils.snackBarPreset(activity!!.findViewById(android.R.id.content), "Cancel")
        }



        when (i) {
            1, 2, 5, 6, 7, 8, 9 -> {
                builder.setView(numberPicker)
            }
            3, 4 -> {
                builder.setView(editText)
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
}
