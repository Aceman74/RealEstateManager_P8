/*
 * *
 *  * Created by Lionel Joffray on 06/09/19 20:07
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 06/09/19 20:07
 *
 */

package com.openclassrooms.realestatemanager.activities

import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.NumberPicker
import androidx.lifecycle.ViewModelProviders
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.fragments.numberpicker.NumberPickerDialog
import com.openclassrooms.realestatemanager.injections.Injection
import com.openclassrooms.realestatemanager.utils.base.BaseActivity
import com.openclassrooms.realestatemanager.utils.rxbus.RxBus
import com.openclassrooms.realestatemanager.utils.rxbus.RxEvent
import com.openclassrooms.realestatemanager.viewmodels.EstateViewModel
import com.openclassrooms.realpicturemanager.activities.viewmodels.PictureViewModel
import com.openclassrooms.realusermanager.activities.viewmodels.UserViewModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity(override val activityLayout: Int = R.layout.activity_search) : BaseActivity(), View.OnClickListener, NumberPicker.OnValueChangeListener, DatePicker.OnDateChangedListener {

    var mPickerArray = IntArray(11)
    lateinit var estateViewModel: EstateViewModel
    lateinit var userViewModel: UserViewModel
    lateinit var pictureViewModel: PictureViewModel
    private lateinit var pickerDisposable: Disposable
    private var mPriceMin: String = ""
    private var mPriceMax: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(findViewById(R.id.search_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        configureViewModel()
        configureItemListeners()

        pickerDisposable = RxBus.listen(RxEvent.PickerPriceEvent::class.java).subscribe {
            mPriceMin = it.price
        }
        pickerDisposable = RxBus.listen(RxEvent.PickerPriceMaxEvent::class.java).subscribe {
            mPriceMax = it.priceMax
        }
    }

    fun showNumberPicker(i: Int, mOldVal: Int?, string: String? = null) {
        val newFragment = NumberPickerDialog(i, mOldVal, string)
        newFragment.setValueChangeListener(this)
        newFragment.setDateChangeListener(this)
        newFragment.show(supportFragmentManager, "time picker")
    }

    override fun onValueChange(picker: NumberPicker?, oldVal: Int, newVal: Int) {
        if (picker?.tag != null) {
            val i: Int = picker.tag as Int

            when (i) {
                2 -> {
                    search_neigh_first.text = picker.displayedValues[newVal]
                    mPickerArray[4] = newVal
                }
                14 -> {
                    search_price_first.text = mPriceMin
                }
                15 -> {
                    search_price_second.text = mPriceMax
                }
                16 -> {
                    search_photos_first.text = newVal.toString()
                    mPickerArray[7] = newVal
                }
                17 -> {
                    search_photos_second.text = newVal.toString()
                    mPickerArray[8] = newVal
                }
                18 -> {
                    search_sqft_first.text = newVal.toString()
                }
                19 -> {
                    search_sqft_second.text = newVal.toString()
                }
            }
        }

    }

    override fun onDateChanged(datePicker: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {

        if (datePicker?.tag != null) {
            val i: Int = datePicker.tag as Int

            when (i) {
                10 -> {
                    search_data_added_first.text = ("$dayOfMonth/$monthOfYear/$year")
                    mPickerArray[0] = "$dayOfMonth$monthOfYear$year".toInt()
                }
                11 -> {
                    search_data_added_second.text = ("$dayOfMonth/$monthOfYear/$year")
                    mPickerArray[1] = "$dayOfMonth$monthOfYear$year".toInt()
                }
                12 -> {
                    search_data_sold_first.text = ("$dayOfMonth/$monthOfYear/$year")
                    mPickerArray[2] = "$dayOfMonth$monthOfYear$year".toInt()
                }
                13 -> {
                    search_data_sold_second.text = ("$dayOfMonth/$monthOfYear/$year")
                    mPickerArray[3] = "$dayOfMonth$monthOfYear$year".toInt()
                }
            }
        }
    }


    override fun onClick(v: View?) {
        when (v) {
            search_price_first -> {
                showNumberPicker(14, mPickerArray[5], mPriceMin)
            }
            search_price_second -> {
                showNumberPicker(15, mPickerArray[6], mPriceMax)
            }
            search_sqft_first -> {
                showNumberPicker(18, mPickerArray[9])
            }
            search_sqft_second -> {
                showNumberPicker(19, mPickerArray[10])
            }
            search_photos_first -> {
                showNumberPicker(16, mPickerArray[7])
            }
            search_photos_second -> {
                showNumberPicker(17, mPickerArray[8])
            }
            search_data_added_first -> {
                showNumberPicker(10, mPickerArray[0])
            }
            search_data_added_second -> {
                showNumberPicker(11, mPickerArray[1])
            }
            search_data_sold_first -> {
                showNumberPicker(12, mPickerArray[2])
            }
            search_data_sold_second -> {
                showNumberPicker(13, mPickerArray[3])
            }
            search_neigh_first -> {
                showNumberPicker(2, mPickerArray[4])
            }
        }
    }

    private fun configureItemListeners() {
        search_price_first.setOnClickListener(this)
        search_price_second.setOnClickListener(this)
        search_sqft_first.setOnClickListener(this)
        search_sqft_second.setOnClickListener(this)
        search_photos_first.setOnClickListener(this)
        search_photos_second.setOnClickListener(this)
        search_data_added_first.setOnClickListener(this)
        search_data_added_second.setOnClickListener(this)
        search_data_sold_first.setOnClickListener(this)
        search_data_sold_second.setOnClickListener(this)
        search_neigh_first.setOnClickListener(this)
    }

    private fun configureViewModel() {
        val mViewModelFactory = Injection.provideViewModelFactory(this)
        this.estateViewModel = ViewModelProviders.of(this, mViewModelFactory).get(EstateViewModel::class.java)
        this.userViewModel = ViewModelProviders.of(this, mViewModelFactory).get(UserViewModel::class.java)
        this.pictureViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PictureViewModel::class.java)
    }
}
