package com.openclassrooms.realestatemanager.fragments.description

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.openclassrooms.realestatemanager.R

class DescriptionFragment : Fragment(), DescriptionContract.DescriptionViewInterface {

    //2
    companion object {

        fun newInstance(): DescriptionFragment {
            return DescriptionFragment()
        }
    }

    //3
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_description, container, false)
    }
}
