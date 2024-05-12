package com.kuzmin.tm_4.feature.report.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.kuzmin.tm_4.common.util.CommonConstants.GROUP_NUM
import com.kuzmin.tm_4.common.util.CommonConstants.MC_UUID
import com.kuzmin.tm_4.feature.api.domain.model.model_complex_obj.McAndConstruction
import com.kuzmin.tm_4.feature.api.domain.model.sealed.McAndCResult
import com.kuzmin.tm_4.feature.api.domain.model.sealed.McAndCResult.*
import com.kuzmin.tm_4.feature.report.databinding.FragmentSingleGraphBinding
import com.kuzmin.tm_4.feature.report.ui.viewmodels.ItemGraphViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemGraphFragment : Fragment() {
    private var mcUuid: String? = null
    private var groupNum: Int = -1

    private var _binding: FragmentSingleGraphBinding? = null
    val binding: FragmentSingleGraphBinding get() = _binding!!

    private val itemGraphViewModel: ItemGraphViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mcUuid = it.getString(MC_UUID)
            groupNum = it.getInt(GROUP_NUM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSingleGraphBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (mcUuid == null) throw RuntimeException("MC UUID is null")
        itemGraphViewModel.getMcAndConstruction(mcUuid!!)
        itemGraphViewModel.mcResult.observe(viewLifecycleOwner) {
            when(it) {
                is Loading -> {

                }
                is SuccessMcAndC -> {
                    //setLineChart(it.mcAndC)
                    Log.d("report", "SHOW Line chart")
                }
                is Error -> {
                    Log.d("Report", "Error has occured when the MC and C was loading")
                }
                else -> throw RuntimeException("Illegal data loaded from DB")
            }
        }
    }

    private fun setLineChart(mcAndConstruction: McAndConstruction) {

    }

    companion object {

        @JvmStatic
        fun newInstance(mcUuid: String, groupNum: Int) =
            ItemGraphFragment().apply {
                arguments = Bundle().apply {
                    putString(MC_UUID, mcUuid)
                    putInt(GROUP_NUM, groupNum)
                }
            }
    }
}