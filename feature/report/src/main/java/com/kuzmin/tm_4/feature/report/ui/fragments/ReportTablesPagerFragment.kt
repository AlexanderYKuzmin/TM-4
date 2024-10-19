package com.kuzmin.tm_4.feature.report.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.kuzmin.tm_4.common.extension.toast
import com.kuzmin.tm_4.common.util.CommonConstants.MC_UUID
import com.kuzmin.tm_4.feature.report.R
import com.kuzmin.tm_4.feature.report.databinding.FragmentReportTablesPagerBinding
import com.kuzmin.tm_4.feature.report.ui.adapters.ReportTablesTabAdapter
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class ReportTablesPagerFragment : Fragment() {
    private var mcUuid: String? = null

    @Inject
    @ApplicationContext
    lateinit var appContext: Context

    private var _binding: FragmentReportTablesPagerBinding? = null
    val binding: FragmentReportTablesPagerBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mcUuid = it.getString(MC_UUID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportTablesPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (mcUuid != null) {
            val adapter = ReportTablesTabAdapter(this, mcUuid!!)
            binding.vpReportTables.adapter = adapter
            attachTabLayoutMediator()
        } else {
            appContext.toast("Не выбрано измерение для отображения и расчета.")
        }
    }

    private fun attachTabLayoutMediator() {
        TabLayoutMediator(binding.tabTables, binding.vpReportTables) { tab, position ->
            val tabNames = resources.getStringArray(R.array.table_names)
            tab.text = tabNames[position]
        }.attach()
    }

    companion object {
        @JvmStatic
        fun newInstance(mcUuid: String) =
            ReportGraphsPagerFragment().apply {
                arguments = Bundle().apply {
                    putString(MC_UUID, mcUuid)
                }
            }
    }
}