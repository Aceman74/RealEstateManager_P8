/*
 * *
 *  * Created by Lionel Joffray on 09/09/19 20:10
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 09/09/19 17:33
 *
 */

package com.openclassrooms.realestatemanager.adapters.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.models.EstateAndPictures

/**
 * Created by Lionel JOFFRAY - on 15/08/2019.
 */
class SearchAdapter(var estate: List<EstateAndPictures>, val listener: (Int) -> Unit) : RecyclerView.Adapter<SearchViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {

        val v: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.estate_item, parent, false)
        return SearchViewHolder(v)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {

        holder.updateWithItem(this.estate[position], position, listener)
    }

    override fun getItemCount(): Int {
        return this.estate.size
    }
}
