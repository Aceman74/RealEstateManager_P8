/*
 * *
 *  * Created by Lionel Joffray on 19/09/19 21:47
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 19/09/19 21:47
 *
 */

package com.openclassrooms.realestatemanager.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.openclassrooms.realestatemanager.fragments.list.ListFragment

import com.openclassrooms.realestatemanager.fragments.map.MapFragment

/**
 * Mainpager for settings Fragments in MainActivity.
 */
class MainPagerAdapter(mgr: FragmentManager, private val mContext: Context) : FragmentPagerAdapter(mgr) {
    /**
     * Page numbers.
     *
     * @return number of pages
     */
    override fun getCount(): Int {
        return 2
    }

    /**
     * Set the fragment to show.
     *
     * @param position actual view
     * @return the fragment
     */
    override fun getItem(position: Int): Fragment {
        return if (position == 0)  //Page number 1
            ListFragment.newInstance()
        else //Page number 2
            MapFragment.newInstance()
    }

    /**
     * Page titles.
     */
    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return "List"
            1 -> return "Map"
            else -> return null
        }
    }
}

