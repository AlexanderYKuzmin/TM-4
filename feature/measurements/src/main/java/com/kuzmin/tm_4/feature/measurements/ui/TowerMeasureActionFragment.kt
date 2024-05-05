package com.kuzmin.tm_4.feature.measurements.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kuzmin.tm_4.feature.api.model.site.Group
import com.kuzmin.tm_4.feature.api.model.site.Measurement
import com.kuzmin.tm_4.feature.measurements.R
import com.kuzmin.tm_4.feature.measurements.databinding.FragmentTowerMeasureActionBinding
import com.kuzmin.tm_4.feature.measurements.databinding.FragmentTowerMeasurePagerBinding


class TowerMeasureActionFragment : Fragment() {

    private var groupNum: Int? = null
    private var constructionUuid: String? = null
    private var mcUuid: String? = null

    private var _binding: FragmentTowerMeasureActionBinding? = null
    private val binding: FragmentTowerMeasureActionBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            groupNum = it.getInt(GROUP_NUMBER)
            constructionUuid = it.getString(CONSTRUCTION_UUID)
            mcUuid = it.getString(MC_UUID)
        }
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

        binding.tvGroupNum.text = groupNum.toString()
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