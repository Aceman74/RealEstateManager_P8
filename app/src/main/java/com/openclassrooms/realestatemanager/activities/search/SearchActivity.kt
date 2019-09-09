/*
 * *
 *  * Created by Lionel Joffray on 09/09/19 20:10
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 09/09/19 20:10
 *
 */

package com.openclassrooms.realestatemanager.activities.search

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.NumberPicker
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.Utils
import com.openclassrooms.realestatemanager.activities.estatedetail.EstateDetailActivity
import com.openclassrooms.realestatemanager.adapters.search.SearchAdapter
import com.openclassrooms.realestatemanager.extensions.priceRemoveSpace
import com.openclassrooms.realestatemanager.fragments.numberpicker.NumberPickerDialog
import com.openclassrooms.realestatemanager.injections.Injection
import com.openclassrooms.realestatemanager.models.Estate
import com.openclassrooms.realestatemanager.models.EstateAndPictures
import com.openclassrooms.realestatemanager.models.Picture
import com.openclassrooms.realestatemanager.utils.base.BaseActivity
import com.openclassrooms.realestatemanager.utils.rxbus.RxBus
import com.openclassrooms.realestatemanager.utils.rxbus.RxEvent
import com.openclassrooms.realestatemanager.viewmodels.EstateViewModel
import com.openclassrooms.realpicturemanager.activities.viewmodels.PictureViewModel
import com.openclassrooms.realusermanager.activities.viewmodels.UserViewModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity(override val activityLayout: Int = R.layout.activity_search) : BaseActivity(), View.OnClickListener, NumberPicker.OnValueChangeListener, DatePicker.OnDateChangedListener {

    var mPickerArray = LongArray(11)
    lateinit var estateViewModel: EstateViewModel
    lateinit var userViewModel: UserViewModel
    lateinit var pictureViewModel: PictureViewModel
    private lateinit var pickerDisposable: Disposable
    private var mPriceMin: String = ""
    private var mPriceMax: String = ""
    private lateinit var mRecyclerView: RecyclerView
    lateinit var observePicture: List<EstateAndPictures>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(findViewById(R.id.search_toolbar))
        mPickerArray[4] = -1
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
        onSearchBtnClick()
    }

    private fun onSearchBtnClick() {

        mRecyclerView = search_recycler_view
        button_search.setOnClickListener {
            val result = executeFilteredSearch(observePicture)
            mRecyclerView.adapter = SearchAdapter(result) { lauchDetailActivity(it) }
            mRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        }
    }

    private fun executeFilteredSearch(observePicture: List<EstateAndPictures>): List<EstateAndPictures> {
        val filteredList: MutableList<EstateAndPictures> = observePicture as MutableList<EstateAndPictures>
        var i = 0
        lateinit var estate: Estate
        lateinit var picture: List<Picture>
        while (i < observePicture.size) {
            estate = filteredList[i].estate
            picture = filteredList[i].pictures
            if (mPickerArray[5] > 0 && String().priceRemoveSpace(estate.price).toLong() < mPickerArray[5]) filteredList.removeAt(i)
            if (mPickerArray[6] > 0 && String().priceRemoveSpace(estate.price).toLong() > mPickerArray[6]) filteredList.removeAt(i)
            if (mPickerArray[7] > 0 && picture.size < mPickerArray[7]) filteredList.removeAt(i)
            if (mPickerArray[8] > 0 && picture.size > mPickerArray[8]) filteredList.removeAt(i)
            if (mPickerArray[9] > 0 && estate.sqft < mPickerArray[9]) filteredList.removeAt(i)
            if (mPickerArray[10] > 0 && estate.sqft > mPickerArray[10]) filteredList.removeAt(i)
            if (mPickerArray[4] > -1 && estate.neighborhood != mPickerArray[4].toInt()) filteredList.removeAt(i)
            i++
            mRecyclerView.adapter?.notifyDataSetChanged()
        }

        return filteredList
    }

    fun lauchDetailActivity(it: Int) {
        val intent = Intent(applicationContext, EstateDetailActivity::class.java)
        intent.putExtra("estateId", it)
        startActivity(intent)
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
                    mPickerArray[4] = newVal.toLong()
                }
                14 -> {
                    search_price_first.text = mPriceMin
                    mPickerArray[5] = String().priceRemoveSpace(mPriceMin).toLong()
                }
                15 -> {
                    search_price_second.text = mPriceMax
                    mPickerArray[6] = String().priceRemoveSpace(mPriceMax).toLong()
                }
                16 -> {
                    search_photos_first.text = newVal.toString()
                    mPickerArray[7] = newVal.toLong()
                }
                17 -> {
                    search_photos_second.text = newVal.toString()
                    mPickerArray[8] = newVal.toLong()
                }
                18 -> {
                    search_sqft_first.text = newVal.toString()
                    mPickerArray[9] = newVal.toLong()
                }
                19 -> {
                    search_sqft_second.text = newVal.toString()
                    mPickerArray[10] = newVal.toLong()
                }
            }
        }

    }

    override fun onDateChanged(datePicker: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {

        if (datePicker?.tag != null) {
            val i: Int = datePicker.tag as Int

            when (i) {
                10 -> {
                    search_data_added_first.text = ("$dayOfMonth/" + (monthOfYear + 1) + "/$year")
                    mPickerArray[0] = Utils.dateToMillis(year, monthOfYear, dayOfMonth)
                }
                11 -> {
                    search_data_added_second.text = ("$dayOfMonth/" + (monthOfYear + 1) + "/$year")
                    mPickerArray[1] = Utils.dateToMillis(year, monthOfYear, dayOfMonth)
                }
                12 -> {
                    search_data_sold_first.text = ("$dayOfMonth/" + (monthOfYear + 1) + "/$year")
                    mPickerArray[2] = Utils.dateToMillis(year, monthOfYear, dayOfMonth)
                }
                13 -> {
                    search_data_sold_second.text = ("$dayOfMonth/" + (monthOfYear + 1) + "/$year")
                    mPickerArray[3] = Utils.dateToMillis(year, monthOfYear, dayOfMonth)
                }
            }
        }
    }


    override fun onClick(v: View?) {
        when (v) {
            search_price_first -> {
                showNumberPicker(14, mPickerArray[5].toInt(), mPriceMin)
            }
            search_price_second -> {
                showNumberPicker(15, mPickerArray[6].toInt(), mPriceMax)
            }
            search_sqft_first -> {
                showNumberPicker(18, mPickerArray[9].toInt())
            }
            search_sqft_second -> {
                showNumberPicker(19, mPickerArray[10].toInt())
            }
            search_photos_first -> {
                showNumberPicker(16, mPickerArray[7].toInt())
            }
            search_photos_second -> {
                showNumberPicker(17, mPickerArray[8].toInt())
            }
            search_data_added_first -> {
                showNumberPicker(10, mPickerArray[0].toInt())
            }
            search_data_added_second -> {
                showNumberPicker(11, mPickerArray[1].toInt())
            }
            search_data_sold_first -> {
                showNumberPicker(12, mPickerArray[2].toInt())
            }
            search_data_sold_second -> {
                showNumberPicker(13, mPickerArray[3].toInt())
            }
            search_neigh_first -> {
                showNumberPicker(2, mPickerArray[4].toInt())
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
        val mViewModelFactory = Injection.provideViewModelFactory(applicationContext)
        this.estateViewModel = ViewModelProviders.of(this, mViewModelFactory).get(EstateViewModel::class.java)
        estateViewModel.allEstateWithPitures.observe(this, Observer {
            observePicture = it
        })
    }
}
