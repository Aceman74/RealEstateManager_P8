package com.openclassrooms.realestatemanager.activities.addestate

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.NumberPicker
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.Utils
import com.openclassrooms.realestatemanager.activities.estate.EstateDetailActivity
import com.openclassrooms.realestatemanager.activities.login.AddEstateContract
import com.openclassrooms.realestatemanager.activities.main.MainActivity
import com.openclassrooms.realestatemanager.fragments.numberpicker.NumberPickerDialog
import com.openclassrooms.realestatemanager.utils.base.BaseActivity
import com.openclassrooms.realestatemanager.utils.rxbus.RxBus
import com.openclassrooms.realestatemanager.utils.rxbus.RxEvent
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_add_estate.*
import kotlinx.android.synthetic.main.fragment_description.*
import timber.log.Timber


class AddEstateActivity(override val activityLayout: Int = R.layout.activity_add_estate) : BaseActivity(), AddEstateContract.AddEstateViewInterface, NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, NumberPicker.OnValueChangeListener, NumberPickerDialog.OnDialogFinish {
    var mStringResult: String = ""
    var mIntResult: Int = 0
    var mPickerArray = IntArray(9)
    private lateinit var pickerDisposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(findViewById(R.id.add_estate_toolbar))
        configureDrawerLayout(add_estate_drawer_layout, add_estate_toolbar)
        configureItemListeners()
        configureEditView()
        date_added_txt_view.text = Utils.todayDate
        agent_txt_view.text = currentUser?.displayName.toString()

        pickerDisposable = RxBus.listen(RxEvent.PickerEvent::class.java).subscribe {
            mStringResult = it.string
            mIntResult = it.nbr
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            desc_estate_type_img -> {
                showNumberPicker(1, mPickerArray[0])
                Timber.i("Click Type")
            }
            desc_neighbourhood_img -> {
                showNumberPicker(2, mPickerArray[1])
                Timber.i("Click Neighbourhood")
            }
            desc_price_img -> {
                showNumberPicker(3, mPickerArray[0])
                Timber.i("Click Price")
            }
            desc_description_img -> {
                showNumberPicker(4, mPickerArray[0])
                Timber.i("Click Description")
            }
            desc_sqft_img -> {
                showNumberPicker(5, mPickerArray[4])
                Timber.i("Click Sqft")
            }
            desc_rooms_img -> {
                showNumberPicker(6, mPickerArray[5])
                Timber.i("Click Rooms")
            }
            desc_bathrooms_img -> {
                showNumberPicker(7, mPickerArray[6])
                Timber.i("Click Bathrooms")
            }
            desc_bedrooms_img -> {
                showNumberPicker(8, mPickerArray[7])
                Timber.i("Click Bedrooms")
            }
            desc_avaiable_img -> {
                showNumberPicker(9, mPickerArray[8])
                Timber.i("Click Available")
            }
        }
    }

    override fun onString(string: String, nbr: Int) {
        mStringResult = string
        mIntResult = nbr
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!pickerDisposable.isDisposed) pickerDisposable.dispose()
    }

    override fun onValueChange(picker: NumberPicker?, oldVal: Int, newVal: Int) {
        val i: Int = picker?.tag as Int

        when (i) {
            1 -> {
                add_estate_type_txt.text = picker.displayedValues[newVal]
                mPickerArray[0] = newVal
            }
            2 -> {
                add_estate_neighborhood.text = picker.displayedValues[newVal]
                mPickerArray[1] = newVal
            }
            3 -> {
                add_estate_price.text = mStringResult
            }
            4 -> {
                fragment_desc_desc_txt.text = mStringResult
            }
            5 -> {
                surface_txt_view.text = newVal.toString()
                mPickerArray[4] = newVal
            }
            6 -> {
                rooms_txt_view.text = newVal.toString()
                mPickerArray[5] = newVal
            }
            7 -> {
                bathrooms_txt_view.text = newVal.toString()
                mPickerArray[6] = newVal
            }
            8 -> {
                bedrooms_txt_view.text = newVal.toString()
                mPickerArray[7] = newVal
            }

            9 -> {
                avaiable_txt_view.text = picker.displayedValues[newVal]
                mPickerArray[8] = newVal
                when (newVal) {
                    0 -> {
                        add_estate_state_txt.text = "For Sale"
                        add_estate_state_txt.setTextColor(resources.getColor(R.color.quantum_lightgreen))
                        detail_estate_date_sold.text = ""
                    }
                    1 -> {
                        add_estate_state_txt.text = "Sold"
                        add_estate_state_txt.setTextColor(resources.getColor(R.color.quantum_googred))
                        detail_estate_date_sold.text = Utils.todayDate
                    }


                }
            }

        }
    }

    fun showNumberPicker(i: Int, mOldVal: Int?) {
        val newFragment = NumberPickerDialog(i, mOldVal)
        newFragment.setValueChangeListener(this)
        newFragment.show(supportFragmentManager, "time picker")
    }

    private fun configureEditView() {
        desc_estate_type_img.visibility = ImageView.VISIBLE
        desc_neighbourhood_img.visibility = ImageView.VISIBLE
        desc_price_img.visibility = ImageView.VISIBLE
        desc_description_img.visibility = ImageView.VISIBLE
        desc_sqft_img.visibility = ImageView.VISIBLE
        desc_rooms_img.visibility = ImageView.VISIBLE
        desc_bathrooms_img.visibility = ImageView.VISIBLE
        desc_bedrooms_img.visibility = ImageView.VISIBLE
        desc_avaiable_img.visibility = ImageView.VISIBLE

        desc_estate_type_img.setOnClickListener(this)
        desc_neighbourhood_img.setOnClickListener(this)
        desc_price_img.setOnClickListener(this)
        desc_description_img.setOnClickListener(this)
        desc_sqft_img.setOnClickListener(this)
        desc_rooms_img.setOnClickListener(this)
        desc_bathrooms_img.setOnClickListener(this)
        desc_bedrooms_img.setOnClickListener(this)
        desc_avaiable_img.setOnClickListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        //  Navigation Drawer item settings

        when (item.itemId) {
            R.id.drawer_first -> {
                val intent = Intent(baseContext, AddEstateActivity::class.java)
                startActivity(intent)
                Timber.i("Click Create")
            }
            R.id.drawer_second -> {
                val intent = Intent(baseContext, MainActivity::class.java)
                startActivity(intent)
                Timber.i("Click Main")
            }
            R.id.drawer_third -> {
                val intent = Intent(baseContext, EstateDetailActivity::class.java)
                startActivity(intent)
                Timber.i("Click Detail")
            }
            else -> {
            }
        }
        return true
    }

    /**
     * Override on back pressed when drawer layout is open.
     */
    override fun onBackPressed() {
        if (add_estate_drawer_layout.isDrawerOpen(GravityCompat.START)) {
            add_estate_drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.add_estate_toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    fun configureItemListeners() {
        add_estate_nav_view.setNavigationItemSelectedListener(this)
    }
}
