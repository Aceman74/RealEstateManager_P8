/*
 * *
 *  * Created by Lionel Joffray on 29/08/19 22:26
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 29/08/19 22:26
 *
 */

package com.openclassrooms.realestatemanager.adapters.estatelist

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.Utils
import com.openclassrooms.realestatemanager.models.Estate
import kotlinx.android.synthetic.main.estate_item.view.*
import java.lang.ref.WeakReference


/**
 * Created by Lionel JOFFRAY - on 15/08/2019.
 */
class EstateViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

    var type: TextView = view.estate_type_txt
    var neighborhood: TextView = view.detail_estate_neighborhood
    var price: TextView = view.detail_estate_price
    var sold: ImageView = view.estate_item_sold_icon
    var picture: ImageView = view.estate_img


    // FOR DATA
    private var callbackWeakRef: WeakReference<EstateAdapter.Listener>? = null


    fun updateWithItem(estate: Estate) {

        this.type.text = Utils.ListOfString.listOfType()[estate.type]
        this.neighborhood.text = Utils.ListOfString.listOfNeighborhood()[estate.neighborhood]
        this.price.text = "$ " + estate.price


    }

    override fun onClick(view: View) {
        val callback = callbackWeakRef!!.get()
        callback?.onClickDeleteButton(adapterPosition)
    }
}
