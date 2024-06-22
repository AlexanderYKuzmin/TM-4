package com.kuzmin.tm_4.feature.measurements.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.kuzmin.tm_4.feature.measurements.databinding.FragmentTowerMeasureActionBinding
import com.kuzmin.tm_4.feature.api.domain.model.sealed.McAndCResult
import com.kuzmin.tm_4.feature.measurements.ui.viewmodels.TowerMeasureActionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TowerMeasureActionFragment : Fragment() {

    private var groupNum: Int = 0
    private var constructionUuid: String? = null
    private var mcUuid: String? = null

    private var _binding: FragmentTowerMeasureActionBinding? = null
    private val binding: FragmentTowerMeasureActionBinding get() = _binding!!

    private val towerMeasureActionViewModel: TowerMeasureActionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            groupNum = it.getInt(GROUP_NUMBER)
            constructionUuid = it.getString(CONSTRUCTION_UUID) ?: throw RuntimeException("Construction UUID must not be null.")
            mcUuid = it.getString(MC_UUID)
        }

        if (groupNum == 0) throw RuntimeException("Measure group must not be zero.")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTowerMeasureActionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        towerMeasureActionViewModel.getConstructionFullFromDb(constructionUuid!!)
        towerMeasureActionViewModel.constructionResult.observe(viewLifecycleOwner) {
            when(it) {
                is McAndCResult.Loading -> {

                }
                is McAndCResult.SuccessC -> {
                    binding.tvTower.invalidate()
                    binding.tvTower.populate(it.constructionFull, groupNum, mcUuid)
                }
                is McAndCResult.Error -> {
                    Log.d("Constr", "ERROR has occurred while construction was getting ${it.throwable}")
                }
                else -> throw RuntimeException("Construction Result do not match to instance properly.")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val GROUP_UUID = "group_uuid"
        const val CONSTRUCTION_UUID = "construction_uuid"
        const val MC_UUID = "mc_uuid"
        const val GROUP_NUMBER = "group_num"

        @JvmStatic
        fun newInstance(groupNum: Int, mcUuid: String, cUuid: String) =
            TowerMeasureActionFragment().apply {
                arguments = Bundle().apply {
                    putInt(GROUP_NUMBER, groupNum)
                    putString(MC_UUID, mcUuid)
                    putString(CONSTRUCTION_UUID, cUuid)
                }
            }
    }
}