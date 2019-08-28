package com.openclassrooms.realestatemanager.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.models.Estate
import kotlinx.android.synthetic.main.estate_item.view.*


/**
 * Created by Lionel JOFFRAY - on 12/05/2019.
 *
 *
 * History Adapter for the user History list (show user history)
 *
 */
class EstateListAdapter(var mEstateList: List<Estate>, private val mContext: Context) : RecyclerView.Adapter<EstateListAdapter.MyViewHolder>() {
    internal var mRed: Int = 0
    internal var mGreen: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val listView = inflater.inflate(R.layout.estate_item, parent, false)
        return MyViewHolder(listView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        updateWithFreshInfo(mEstateList[position], holder, position)
    }

    /**
     * Update RecyclerView with restaurant list and date.
     */
    private fun updateWithFreshInfo(item: Estate, holder: MyViewHolder, position: Int) {

        holder.type


    }

    override fun getItemCount(): Int {
        return mEstateList.size
    }


    /**
     * View Holder .
     *
     */
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var type: TextView = view.estate_type_txt
        var neighborhood: TextView = view.detail_estate_neighborhood
        var price: TextView = view.detail_estate_price
        var sold: ImageView = view.estate_item_sold_icon
        var picture: ImageView = view.estate_img


    }
}