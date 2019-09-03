/*
 * *
 *  * Created by Lionel Joffray on 03/09/19 16:31
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 03/09/19 16:31
 *
 */

package com.openclassrooms.realestatemanager.adapters.estatelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.models.EstateAndPictures

/**
 * Created by Lionel JOFFRAY - on 15/08/2019.
 */
class EstateAdapter(var estate: List<EstateAndPictures>, val listener: (Int) -> Unit) : RecyclerView.Adapter<EstateViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstateViewHolder {

        val v: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.estate_item, parent, false)
        return EstateViewHolder(v)
    }

    override fun onBindViewHolder(holder: EstateViewHolder, position: Int) {
        holder.updateWithItem(this.estate[position], position, listener)
    }

    override fun getItemCount(): Int {
        return this.estate.size
    }
}
