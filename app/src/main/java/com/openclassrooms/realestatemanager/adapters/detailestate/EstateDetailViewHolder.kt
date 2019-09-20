/*
 * *
 *  * Created by Lionel Joffray on 20/09/19 18:13
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 20/09/19 17:37
 *
 */

package com.openclassrooms.realestatemanager.adapters.detailestate

import android.app.Dialog
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.models.Picture
import kotlinx.android.synthetic.main.slideshow_item.view.*


/**
 * Created by Lionel JOFFRAY - on 15/08/2019.
 *
 * The viewHolder for pictures showing in DetailEstate.
 */
class EstateDetailViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

    var image: ImageView = view.slideshow_img
    var text: TextView = view.slideshow_txt

    /**
     * Update the view with the picture, and handle the click on it with full scree preview.
     */
    fun updateWithItem(pictures: List<Picture>, position: Int, listener: (Int) -> Unit) {

        text.text = (position + 1).toString() + "/" + pictures.size
        Glide.with(itemView)
                .load(pictures[position].picturePath)

                .into(image)
        itemView.setOnClickListener {
            listener(pictures[position].pictureId!!.toInt())

            val dialog = Dialog(itemView.context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.image_full_size)
            val imgView = dialog.findViewById<ImageView>(R.id.dialog_imageview)
            imgView.isClickable = true
            Glide.with(itemView.context)
                    .load(pictures[position].picturePath)
                    .into(imgView)
            dialog.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            dialog.show()
            imgView.setOnClickListener { dialog.dismiss() }
        }
    }

    override fun onClick(view: View) {

    }
}
