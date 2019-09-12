/*
 * *
 *  * Created by Lionel Joffray on 12/09/19 20:50
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 12/09/19 20:50
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
import com.openclassrooms.realestatemanager.injections.Injection
import com.openclassrooms.realestatemanager.models.Estate
import com.openclassrooms.realestatemanager.models.EstateAndPictures
import com.openclassrooms.realestatemanager.models.Picture
import com.openclassrooms.realestatemanager.utils.NumberPickerDialog
import com.openclassrooms.realestatemanager.utils.base.BaseActivity
import com.openclassrooms.realestatemanager.utils.rxbus.RxBus
import com.openclassrooms.realestatemanager.utils.rxbus.RxEvent
import com.openclassrooms.realestatemanager.viewmodels.EstateViewModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_search.*


class SearchActivity(override val activityLayout: Int = R.layout.activity_search) : BaseActivity(), SearchContract.SearchViewInterface, View.OnClickListener, NumberPicker.OnValueChangeListener, DatePicker.OnDateChangedListener {

    private val mPresenter = SearchPresenter()
    var mPickerArray = LongArray(11)
    lateinit var mEstateViewModel: EstateViewModel
    private lateinit var mPickerDisposable: Disposable
    private var mPriceMin: String = ""
    private var mPriceMax: String = ""
    private lateinit var mRecyclerView: RecyclerView
    lateinit var mObservePicture: List<EstateAndPictures>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.attachView(this)
        configureView()
        configureViewModel()
        configureItemListeners()

        mPickerDisposable = RxBus.listen(RxEvent.PickerPriceEvent::class.java).subscribe {
            mPriceMin = it.price
        }
        mPickerDisposable = RxBus.listen(RxEvent.PickerPriceMaxEvent::class.java).subscribe {
            mPriceMax = it.priceMax
        }
        onSearchBtnClick()
    }


    override fun configureView() {
        setSupportActionBar(findViewById(R.id.search_toolbar))
        mPickerArray[4] = -1
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onSearchBtnClick() {
        mRecyclerView = search_recycler_view
        button_search.setOnClickListener {
            val result = executeFilteredSearch(mObservePicture)
            mRecyclerView.adapter = SearchAdapter(result) { lauchDetailActivity(it) }
            mRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        }
    }

    override fun configureViewModel() {
        val mViewModelFactory = Injection.provideViewModelFactory(applicationContext)
        this.mEstateViewModel = ViewModelProviders.of(this, mViewModelFactory).get(EstateViewModel::class.java)
        mEstateViewModel.allEstateWithPitures.observe(this, Observer {
            mObservePicture = it
        })
    }

    override fun executeFilteredSearch(observePicture: List<EstateAndPictures>): List<EstateAndPictures> {
        val filteredList: MutableList<EstateAndPictures> = ArrayList()
        filteredList.addAll(mObservePicture)
        var i = 0
        lateinit var estate: Estate
        lateinit var picture: List<Picture>
        while (i < observePicture.size) {
            estate = observePicture[i].estate
            picture = observePicture[i].pictures
            when {

                mPickerArray[0] > 0 && Utils.dateWithBSToMillis(estate.addDate) < mPickerArray[0] -> {
                    removeEstateFromList(filteredList, observePicture, i)
                }
                mPickerArray[1] > 0 && Utils.dateWithBSToMillis(estate.addDate) > mPickerArray[1] -> {
                    removeEstateFromList(filteredList, observePicture, i)
                }
                mPickerArray[2] > 0 && estate.soldDate == null || mPickerArray[2] > 0 && estate.soldDate != null && Utils.dateWithBSToMillis(estate.soldDate!!) < mPickerArray[2] -> {
                    removeEstateFromList(filteredList, observePicture, i)
                }
                mPickerArray[3] > 0 && estate.soldDate == null || mPickerArray[3] > 0 && estate.soldDate != null && Utils.dateWithBSToMillis(estate.soldDate!!) > mPickerArray[3] -> {
                    removeEstateFromList(filteredList, observePicture, i)
                }
                mPickerArray[4] > -1 && estate.neighborhood != mPickerArray[4].toInt() -> {
                    removeEstateFromList(filteredList, observePicture, i)
                }
                mPickerArray[5] > 0 && String().priceRemoveSpace(estate.price).toLong() < mPickerArray[5] -> {
                    removeEstateFromList(filteredList, observePicture, i)
                }
                mPickerArray[6] > 0 && String().priceRemoveSpace(estate.price).toLong() > mPickerArray[6] -> {
                    removeEstateFromList(filteredList, observePicture, i)
                }
                mPickerArray[7] > 0 && picture.size < mPickerArray[7] -> {
                    removeEstateFromList(filteredList, observePicture, i)
                }
                mPickerArray[8] > 0 && picture.size > mPickerArray[8] -> {
                    removeEstateFromList(filteredList, observePicture, i)
                }
                mPickerArray[9] > 0 && estate.sqft < mPickerArray[9] -> {
                    removeEstateFromList(filteredList, observePicture, i)
                }
                mPickerArray[10] > 0 && estate.sqft > mPickerArray[10] -> {
                    removeEstateFromList(filteredList, observePicture, i)
                }
            }
            i++
            mRecyclerView.adapter?.notifyDataSetChanged()
        }

        return filteredList
    }

    private fun removeEstateFromList(filteredList: MutableList<EstateAndPictures>, observePicture: List<EstateAndPictures>, i: Int) {
        if (filteredList.contains(observePicture[i]))
            filteredList.remove(observePicture[i])
    }

    override fun lauchDetailActivity(it: Int) {
        val intent = Intent(applicationContext, EstateDetailActivity::class.java)
        intent.putExtra("mEstateId", it)
        startActivity(intent)
    }

    override fun showNumberPicker(i: Int, mOldVal: Int?, string: String?) {
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
                    mPickerArray[0] = Utils.dateToMillis(year, monthOfYear + 1, dayOfMonth)
                }
                11 -> {
                    search_data_added_second.text = ("$dayOfMonth/" + (monthOfYear + 1) + "/$year")
                    mPickerArray[1] = Utils.dateToMillis(year, monthOfYear + 1, dayOfMonth)
                }
                12 -> {
                    search_data_sold_first.text = ("$dayOfMonth/" + (monthOfYear + 1) + "/$year")
                    mPickerArray[2] = Utils.dateToMillis(year, monthOfYear + 1, dayOfMonth)
                }
                13 -> {
                    search_data_sold_second.text = ("$dayOfMonth/" + (monthOfYear + 1) + "/$year")
                    mPickerArray[3] = Utils.dateToMillis(year, monthOfYear + 1, dayOfMonth)
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
                showNumberPicker(18, mPickerArray[9].toInt(), null)
            }
            search_sqft_second -> {
                showNumberPicker(19, mPickerArray[10].toInt(), null)
            }
            search_photos_first -> {
                showNumberPicker(16, mPickerArray[7].toInt(), null)
            }
            search_photos_second -> {
                showNumberPicker(17, mPickerArray[8].toInt(), null)
            }
            search_data_added_first -> {
                showNumberPicker(10, mPickerArray[0].toInt(), null)
            }
            search_data_added_second -> {
                showNumberPicker(11, mPickerArray[1].toInt(), null)
            }
            search_data_sold_first -> {
                showNumberPicker(12, mPickerArray[2].toInt(), null)
            }
            search_data_sold_second -> {
                showNumberPicker(13, mPickerArray[3].toInt(), null)
            }
            search_neigh_first -> {
                showNumberPicker(2, mPickerArray[4].toInt(), null)
            }
        }
    }

    override fun configureItemListeners() {
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
}
