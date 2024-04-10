package com.kuzmin.tm_4.feature.sites.ui

import android.content.Context
import android.util.Log
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.kuzmin.tm_4.common.R

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