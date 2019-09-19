/*
 * *
 *  * Created by Lionel Joffray on 19/09/19 21:47
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 19/09/19 21:47
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
 *
 * Adapter who shows the Estate on FragmentList in MainActivity and Search.
 */
class EstateAdapter(var estate: List<EstateAndPictures>, var devise: String, val listener: (Int) -> Unit) : RecyclerView.Adapter<EstateViewHolder>() {

    /**
     * Create the ViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstateViewHolder {
        val v: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.estate_item, parent, false)
        return EstateViewHolder(v)
    }

    /**
     * Bind the estate to the view, add animation.
     */
    override fun onBindViewHolder(holder: EstateViewHolder, position: Int) {
        holder.updateWithItem(this.estate[position], position, listener, devise)
        Utils.setFadeAnimation(holder.itemView, holder.itemView.context)
    }

    /**
     * ItemCount.
     */
    override fun getItemCount(): Int {
        return this.estate.size
    }
}
