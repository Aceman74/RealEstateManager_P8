/*
 * *
 *  * Created by Lionel Joffray on 04/09/19 19:35
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 04/09/19 19:34
 *
 */

package com.openclassrooms.realestatemanager.adapters.estatelist

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.models.Picture
import kotlinx.android.synthetic.main.slideshow_item.view.*


/**
 * Created by Lionel JOFFRAY - on 15/08/2019.
 */
class EstateDetailViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

    var image: ImageView = view.slideshow_img
    var text: TextView = view.slideshow_txt


    fun updateWithItem(pictures: List<Picture>, position: Int, listener: (Int) -> Unit) {

        text.text = (position + 1).toString() + "/" + pictures.size
        Glide.with(itemView)
                .load(pictures[position].picturePath)
                .into(image)
        itemView.setOnClickListener {
            listener(pictures[position].pictureId!!.toInt())
        }
    }

    override fun onClick(view: View) {
    }
}
