package com.kuzmin.tm_4.feature.measurements.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.kuzmin.tm_4.feature.measurements.R
import com.kuzmin.tm_4.feature.measurements.databinding.FragmentTowerMeasurePagerBinding
import com.kuzmin.tm_4.feature.measurements.ui.NavMeasurementsFragment.Companion.MC_UUID
import com.kuzmin.tm_4.feature.measurements.ui.adapters.TowerMeasureTabAdapter
import com.kuzmin.tm_4.feature.measurements.ui.viewmodels.TmPagerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TowerMeasurePagerFragment : Fragment() {

    private var mcUuid: String? = null

    private var _binding: FragmentTowerMeasurePagerBinding? = null
    private val binding: FragmentTowerMeasurePagerBinding get() = _binding!!

    private val tmPagerViewModel: TmPagerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTowerMeasurePagerBinding.inflate(inflater, container, false)

        arguments?.apply {
            mcUuid = getString(MC_UUID)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //binding.vpTowerMeasures.adapter = TowerMeasureTabAdapter(this)

        tmPagerViewModel.getSiteFromDb()
    }

    private fun attachTabLayoutMediator() {
        TabLayoutMediator(binding.tabAzimuth, binding.vpTowerMeasures) { tab, position ->
            //val tabNames = resources.getStringArray(R.array.tab_names)
            //tab.text = tabNames[position]
        }.attach()
    }

}