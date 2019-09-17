/*
 * *
 *  * Created by Lionel Joffray on 17/09/19 23:02
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 17/09/19 23:01
 *
 */

package com.openclassrooms.realestatemanager.adapters.estatelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.models.EstateAndPictures
import com.openclassrooms.realestatemanager.utils.Utils

/**
 * Created by Lionel JOFFRAY - on 15/08/2019.
 */
class EstateAdapter(var estate: List<EstateAndPictures>, var devise: String, val listener: (Int) -> Unit) : RecyclerView.Adapter<EstateViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstateViewHolder {

        val v: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.estate_item, parent, false)
        return EstateViewHolder(v)
    }

    override fun onBindViewHolder(holder: EstateViewHolder, position: Int) {
        holder.updateWithItem(this.estate[position], position, listener, devise)
        Utils.setFadeAnimation(holder.itemView, holder.itemView.context)
    }

    override fun getItemCount(): Int {
        return this.estate.size
    }
}
