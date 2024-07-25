package com.kuzmin.tm_4.feature.sites.ui.fragments

import android.content.Context
import androidx.fragment.app.Fragment

abstract class SitesFragment : Fragment() {

    var onSitesAdapterClickListener: OnSitesAdapterClickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnSitesAdapterClickListener) {
            onSitesAdapterClickListener = context
        } else {
            throw RuntimeException("Activity must implement OnDeviceItemClickListener")
        }
    }

    interface OnSitesAdapterClickListener {
        fun onItemSiteClick(name: String)
    }
}