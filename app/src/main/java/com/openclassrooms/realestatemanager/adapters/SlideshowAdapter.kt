/*
 * *
 *  * Created by Lionel Joffray on 29/08/19 22:26
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 29/08/19 22:22
 *
 */

package com.openclassrooms.realestatemanager.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

import com.openclassrooms.realestatemanager.R


/**
 * Created by Lionel JOFFRAY - on 12/05/2019.
 *
 *
 * History Adapter for the user History list (show user history)
 */
class SlideshowAdapter(var mImageList: List<*>, private val mContext: Context) : RecyclerView.Adapter<SlideshowAdapter.MyViewHolder>() {
    internal var mRed: Int = 0
    internal var mGreen: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val slideView = inflater.inflate(R.layout.slideshow_item, parent, false)
        return MyViewHolder(slideView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //  updateWithFreshInfo(mEstateList.get(position), holder, position);
    }

    /**
     * Update RecyclerView with restaurant list and date.
     */
    //  private void updateWithFreshInfo(final ImageList item, final MyViewHolder holder, int position) {

    //   }
    override fun getItemCount(): Int {
        return mImageList.size
    }


    /**
     * View Holder .
     */
    class MyViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal var mName: TextView? = null
        internal var mDate: TextView? = null
    }
}