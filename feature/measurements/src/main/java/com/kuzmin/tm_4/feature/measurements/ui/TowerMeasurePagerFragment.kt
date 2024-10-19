package com.kuzmin.tm_4.feature.measurements.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.kuzmin.tm_4.feature.api.domain.model.sealed.SiteActionResult
import com.kuzmin.tm_4.feature.api.domain.model.sealed.SiteActionResult.*
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

        Log.d("MC", "Pager. MC Uuid = $mcUuid")
        mcUuid?.let { tmPagerViewModel.getMeasurementConstructionFromDb(it) } // maybe it was exceeded. No need in full obj

        tmPagerViewModel.mcResult.observe(viewLifecycleOwner) {
            when(it) {
                is Loading -> {

                }
                is SuccessMc -> {
                    Log.d("MC", "Getting mc success!")
                    val adapter = TowerMeasureTabAdapter(this).apply {
                        mc = it.mc
                    }
                    binding.vpTowerMeasures.adapter = adapter
                    attachTabLayoutMediator()
                }
                is Error -> {
                    Log.d("MC", "ERROR: happened while mc was getting ${it.throwable}")
                }

                else -> {Log.d("MC", "ERROR: There are no appropriate result class")}
            }
        }
    }

    private fun attachTabLayoutMediator() {
        TabLayoutMediator(binding.tabAzimuth, binding.vpTowerMeasures) { tab, position ->
            /*val tabNames = arrayOf(
                String.format("Группа %d аз.%d%s",groups[0].groupNum, groups[0].azimuth, DEGREE),
                String.format("Группа %d аз.%d%s",groups[1].groupNum, groups[1].azimuth, DEGREE),
            )*/
            val tabNames = resources.getStringArray(R.array.measure_groups_names)
            tab.text = tabNames[position]
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}