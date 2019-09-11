/*
 * *
 *  * Created by Lionel Joffray on 09/09/19 20:10
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 09/09/19 20:10
 *
 */

package com.openclassrooms.realestatemanager.adapters.search

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.Utils
import com.openclassrooms.realestatemanager.models.EstateAndPictures
import kotlinx.android.synthetic.main.estate_item.view.*


/**
 * Created by Lionel JOFFRAY - on 15/08/2019.
 */
class SearchViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

    var type: TextView = view.estate_type_txt
    var neighborhood: TextView = view.detail_estate_neighborhood
    var price: TextView = view.detail_estate_price
    var picture: ImageView = view.estate_img


    fun updateWithItem(estate: EstateAndPictures, position: Int, listener: (Int) -> Unit) {

        this.type.text = Utils.ListOfString.listOfType()[estate.estate.type]
        this.neighborhood.text = Utils.ListOfString.listOfNeighborhood()[estate.estate.neighborhood]
        this.price.text = "$ " + estate.estate.price
        Glide.with(itemView)
                .load(estate.pictures[0].picturePath)
                .into(picture)
        itemView.setOnClickListener {
            listener(estate.estate.estateId!!.toInt())
        }
    }

    override fun onClick(view: View) {
    }
}