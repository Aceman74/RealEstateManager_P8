/*
 * *
 *  * Created by Lionel Joffray on 19/09/19 21:47
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 19/09/19 19:15
 *
 */

package com.openclassrooms.realestatemanager.fragments.list


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.activities.estatedetail.EstateDetailActivity
import com.openclassrooms.realestatemanager.adapters.estatelist.EstateAdapter
import com.openclassrooms.realestatemanager.injections.Injection
import com.openclassrooms.realestatemanager.models.EstateAndPictures
import com.openclassrooms.realestatemanager.viewmodels.EstateViewModel
import kotlinx.android.synthetic.main.fragment_list.*
import timber.log.Timber


/**
 * List Fragment is the fragment who show the list of all estate.
 * Used in Main Activity.
 */
class ListFragment : Fragment(), ListContract.ListViewInterface {

    private val mPresenter = ListPresenter()
    private lateinit var mRecyclerView: RecyclerView
    lateinit var estateViewModel: EstateViewModel
    lateinit var observePicture: List<EstateAndPictures>
    var mDevise = "$"

    companion object {

        fun newInstance(): ListFragment {
            return ListFragment()
        }
    }

    /**
     * Create view.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    /**
     * When create, init presenter, load devise preferences, set the recyclerview and Viewmodel.
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mPresenter.attachView(this)
        loadSharedPref()
        mRecyclerView = estate_recycler_view
        configureViewModel()
    }

    /**
     * Load devise.
     */
    override fun loadSharedPref() {
        val shared = activity?.getSharedPreferences(getString(R.string.app_name), AppCompatActivity.MODE_PRIVATE)
        mDevise = shared?.getString("actual_devise", "$")!!
    }

    /**
     * Configure the livedata observer for passing the list to RecyclerView.
     */
    override fun configureViewModel() {
        val mViewModelFactory = Injection.provideViewModelFactory(requireContext())
        this.estateViewModel = ViewModelProviders.of(this, mViewModelFactory).get(EstateViewModel::class.java)
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        estateViewModel.allEstateWithPitures.observe(this, Observer { list ->
            observePicture = list
            mRecyclerView.adapter = EstateAdapter(observePicture, mDevise) {
                Timber.tag("RV click").i("$it")
                launchDetailActivity(it)
            }
        })
    }

    /**
     * Launch detail activity on item clicked with Estate ID.
     */
    override fun launchDetailActivity(it: Int) {
        val intent = Intent(context, EstateDetailActivity::class.java)
        intent.putExtra("mEstateId", it)
        startActivity(intent)
    }

}
