/*
 * *
 *  * Created by Lionel Joffray on 19/09/19 21:47
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 19/09/19 19:07
 *
 */

package com.openclassrooms.realestatemanager.adapters.detailestate

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
 * Adapter who shows the pictures on the EstateDetail view. Horizontally.
 */
class EstateDetailAdapter(var estate: List<EstateAndPictures>, val listener: (Int) -> Unit) : RecyclerView.Adapter<EstateDetailViewHolder>() {

    /**
     * Create the ViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstateDetailViewHolder {
        val v: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.slideshow_item, parent, false)
        return EstateDetailViewHolder(v)
    }

    /**
     * Bind the estate to the view, add animation.
     */
    override fun onBindViewHolder(holder: EstateDetailViewHolder, position: Int) {
        holder.updateWithItem(this.estate[0].pictures, position, listener)
        Utils.setFadeAnimation(holder.itemView, holder.itemView.context)
    }

    /**
     * ItemCount.
     */
    override fun getItemCount(): Int {
        return this.estate[0].pictures.size
    }
}
