/*
 * *
 *  * Created by Lionel Joffray on 29/08/19 22:26
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 29/08/19 22:26
 *
 */

package com.openclassrooms.realestatemanager.fragments.list


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.activities.viewmodels.EstateViewModel
import com.openclassrooms.realestatemanager.adapters.estatelist.EstateAdapter
import com.openclassrooms.realestatemanager.injections.Injection
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * A simple [Fragment] subclass.
 */
class ListFragment : Fragment() {

    private lateinit var mRecyclerView: RecyclerView
    lateinit var estateViewModel: EstateViewModel

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
        mRecyclerView = estate_recycler_view
        configureViewModel()
    }

    private fun configureViewModel() {
        val mViewModelFactory = Injection.provideViewModelFactory(requireContext())
        this.estateViewModel = ViewModelProviders.of(this, mViewModelFactory).get(EstateViewModel::class.java)
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        estateViewModel.allEstate.observe(this, Observer { estate ->
            // Data bind the recycler view
            mRecyclerView.adapter = EstateAdapter(estate)
        })
    }


}
