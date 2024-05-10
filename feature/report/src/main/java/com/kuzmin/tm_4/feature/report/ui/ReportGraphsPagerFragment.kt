package com.kuzmin.tm_4.feature.report.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kuzmin.tm_4.feature.report.R
import com.kuzmin.tm_4.feature.report.ui.NavReportFragment.Companion.MC_UUID

class ReportGraphsPagerFragment : Fragment() {
    private var mcUuid: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mcUuid = it.getString(MC_UUID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_report_graphs_pager, container, false)
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