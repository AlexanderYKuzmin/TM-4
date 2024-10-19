package com.kuzmin.tm_4.feature.home.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kuzmin.tm_4.common.R.id.home_nav_graph
import com.kuzmin.tm_4.feature.api.api.FeatureIsActiveListener
import com.kuzmin.tm_4.feature.api.api.HomeButtonRemovable
import com.kuzmin.tm_4.feature.api.api.activity.OnFragmentActionListener
import com.kuzmin.tm_4.feature.api.domain.model.FragmentAction
import com.kuzmin.tm_4.feature.home.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var homeButtonRemovable: HomeButtonRemovable? = null

    private var onFragmentActionListener: OnFragmentActionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is HomeButtonRemovable) {
            homeButtonRemovable = context
        }
        if (context is OnFragmentActionListener) {
            onFragmentActionListener = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("remove arrow", "on create")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onResume() {
        homeButtonRemovable?.remove()
        onFragmentActionListener?.onFragmentAction(FragmentAction.HomeAction)
        super.onResume()
    }
}