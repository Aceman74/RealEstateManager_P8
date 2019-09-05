/*
 * *
 *  * Created by Lionel Joffray on 05/09/19 19:00
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 05/09/19 19:00
 *
 */

package com.openclassrooms.realestatemanager.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.Utils
import com.openclassrooms.realestatemanager.injections.Injection
import com.openclassrooms.realestatemanager.utils.base.BaseActivity
import com.openclassrooms.realestatemanager.viewmodels.EstateViewModel
import com.openclassrooms.realpicturemanager.activities.viewmodels.PictureViewModel
import com.openclassrooms.realusermanager.activities.viewmodels.UserViewModel
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity(override val activityLayout: Int = R.layout.activity_search) : BaseActivity(), View.OnClickListener, TextWatcher, View.OnFocusChangeListener {
    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        when (v) {
            search_price_first, search_price_second, search_sqft_first, search_sqft_second -> {
                if (!hasFocus && search_price_first.text.toString() != "")
                    search_price_first.setText(Utils.priceSpace(search_price_first.text.toString()))
            }
            search_data_added_first, search_data_added_second, search_data_sold_first, search_data_sold_second -> {

            }
        }
    }


    lateinit var estateViewModel: EstateViewModel
    lateinit var userViewModel: UserViewModel
    lateinit var pictureViewModel: PictureViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(findViewById(R.id.main_toolbar))
        configureViewModel()
        configureItemListeners()
    }

    override fun onClick(v: View?) {
        when (v) {
            search_price_first -> {
            }
            search_price_second -> {
            }
            search_sqft_first -> {
            }
            search_sqft_second -> {
            }
            search_photos_first -> {
            }
            search_photos_second -> {
            }
            search_data_added_first -> {
            }
            search_data_added_second -> {
            }
            search_data_sold_first -> {
            }
            search_data_sold_second -> {
            }
            search_neigh_first -> {
            }
        }
    }

    private fun configureItemListeners() {
        search_price_first.onFocusChangeListener = this
        search_price_second.addTextChangedListener(this)
        search_sqft_first.addTextChangedListener(this)
        search_sqft_second.addTextChangedListener(this)
        search_photos_first.addTextChangedListener(this)
        search_photos_second.addTextChangedListener(this)
        search_data_added_first.addTextChangedListener(this)
        search_data_added_second.addTextChangedListener(this)
        search_data_sold_first.addTextChangedListener(this)
        search_data_sold_second.addTextChangedListener(this)
        search_neigh_first.addTextChangedListener(this)
    }

    override fun afterTextChanged(text: Editable) {
        when (text.toString()) {
            search_price_first.text.toString() -> {
            }
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

    private fun configureViewModel() {
        val mViewModelFactory = Injection.provideViewModelFactory(this)
        this.estateViewModel = ViewModelProviders.of(this, mViewModelFactory).get(EstateViewModel::class.java)
        this.userViewModel = ViewModelProviders.of(this, mViewModelFactory).get(UserViewModel::class.java)
        this.pictureViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PictureViewModel::class.java)
    }
}
