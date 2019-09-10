/*
 * *
 *  * Created by Lionel Joffray on 10/09/19 20:32
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 10/09/19 19:38
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
import com.openclassrooms.realpicturemanager.activities.viewmodels.PictureViewModel
import kotlinx.android.synthetic.main.fragment_list.*
import timber.log.Timber


/**
 * A simple [Fragment] subclass.
 */
class ListFragment : Fragment() {

    private lateinit var mRecyclerView: RecyclerView
    lateinit var estateViewModel: EstateViewModel
    lateinit var pictureViewModel: PictureViewModel
    lateinit var observePicture: List<EstateAndPictures>
    var mDevise = "$"

    companion object {

        fun newInstance(): ListFragment {
            return ListFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadSharedPref()
        mRecyclerView = estate_recycler_view
        configureViewModel()
    }

    private fun loadSharedPref() {
        val shared = activity?.getSharedPreferences(getString(R.string.app_name), AppCompatActivity.MODE_PRIVATE)
        mDevise = shared?.getString("actual_devise", "$")!!
    }

    private fun configureViewModel() {
        val mViewModelFactory = Injection.provideViewModelFactory(requireContext())
        this.estateViewModel = ViewModelProviders.of(this, mViewModelFactory).get(EstateViewModel::class.java)
        this.pictureViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PictureViewModel::class.java)
        estateViewModel.allEstateWithPitures.observe(this, Observer {
            observePicture = it
            mRecyclerView.adapter = EstateAdapter(observePicture, mDevise) {
                Timber.tag("RV click").i("$it")
                lauchDetailActivity(it)
            }
        })
        mRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    fun lauchDetailActivity(it: Int) {
        val intent = Intent(context, EstateDetailActivity::class.java)
        intent.putExtra("estateId", it)
        startActivity(intent)
    }

}
