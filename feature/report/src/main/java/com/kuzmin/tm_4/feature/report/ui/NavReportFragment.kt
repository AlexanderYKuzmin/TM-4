package com.kuzmin.tm_4.feature.report.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kuzmin.tm_4.common.R
import com.kuzmin.tm_4.common.extension.toast
import com.kuzmin.tm_4.common.util.CommonConstants.MC_UUID
import com.kuzmin.tm_4.feature.api.R.*

import com.kuzmin.tm_4.feature.report.databinding.FragmentNavReportBinding
import com.kuzmin.tm_4.feature.report.ui.viewmodels.NavReportViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@AndroidEntryPoint
class NavReportFragment : Fragment() {
    private var mcUuid: String? = null

    @Inject
    @ApplicationContext
    lateinit var appContext: Context

    private val navController by lazy {
        findNavController()
    }

    private var _binding: FragmentNavReportBinding? = null
    val binding: FragmentNavReportBinding get() = _binding!!

    private val navReportViewModel: NavReportViewModel by viewModels()

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
            _binding = FragmentNavReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("report", "NavReportFragment. onCreated. mcUuid: $mcUuid")
        if (mcUuid.isNullOrBlank()) {
            navReportViewModel.getMcUuidFromDatastore()
        }

        navReportViewModel.mcUuidLiveData.observe(viewLifecycleOwner) {
            if (it == null) appContext.toast("Не выбрана конструкция для отображения.")
            else mcUuid = it
        }

        setClickListeners()
    }

    private fun setClickListeners() {
        val downAnim = AnimationUtils.loadAnimation(appContext, anim.down)
        with(binding) {
            clGraphs.setOnClickListener {
                it.startAnimation(downAnim)
                if (mcUuid != null) navController.navigate(
                   R.id.report_graphs,
                   bundleOf(MC_UUID to mcUuid)
                )
                else appContext.toast("Не выбрано измерение для отображения")
            }
            clTables.setOnClickListener {
                it.startAnimation(downAnim)
                if (mcUuid != null) navController.navigate(
                    R.id.report_tables,
                    bundleOf(MC_UUID to mcUuid)
                )
                else appContext.toast("Не выбрано измерение для отображения")
            }
            clReport.setOnClickListener {
                Log.d("Report", "Here's gonna be a report!")
            }
        }
    }
}