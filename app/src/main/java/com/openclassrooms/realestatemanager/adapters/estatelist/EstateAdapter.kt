/*
 * *
 *  * Created by Lionel Joffray on 29/08/19 22:26
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 29/08/19 22:26
 *
 */

package com.openclassrooms.realestatemanager.adapters.estatelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.models.Estate

/**
 * Created by Lionel JOFFRAY - on 15/08/2019.
 */
class EstateAdapter(var estate: List<Estate>) : RecyclerView.Adapter<EstateViewHolder>() {

    // CALLBACK
    interface Listener {
        fun onClickDeleteButton(position: Int)

    }

    // FOR DATA
    private lateinit var callback: Listener

    // CONSTRUCTOR
    fun EstateAdapter(callback: Listener, estateItem: List<Estate>) {
        this.estate = estateItem
        this.callback = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : EstateViewHolder {
        val v: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.estate_item, parent, false)
        return EstateViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: EstateViewHolder, position: Int) {
        viewHolder.updateWithItem(this.estate[position])
    }

    fun getItem(position: Int): Estate {
        return this.estate[position]
    }

    override fun getItemCount(): Int {
        return this.estate.size
    }

    fun updateData(estateItem: List<Estate>) {
        this.estate = estateItem
        this.notifyDataSetChanged()
    }
}
