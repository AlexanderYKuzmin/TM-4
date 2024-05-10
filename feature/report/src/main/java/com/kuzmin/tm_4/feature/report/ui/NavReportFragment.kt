package com.kuzmin.tm_4.feature.report.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kuzmin.tm_4.common.extension.toast
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

        if (mcUuid == null) {
            navReportViewModel.getMcUuidFromDatastore()
        }

        navReportViewModel.mcUuidLiveData.observe(viewLifecycleOwner) {
            if (it == null) appContext.toast("Не выбрана конструкция для отображения.")
            else mcUuid = it
        }

        setClickListeners()
    }

    private fun setClickListeners() {
        with(binding) {
            clGraphs.setOnClickListener {
               // if (mcUuid != null) navController.navigate()
            }
            clTables.setOnClickListener {
                // TODO:
            }
            clReport.setOnClickListener {
                Log.d("Report", "Here's gonna be a report!")
            }
        }
    }

    companion object {
        const val MC_UUID = "mc_uuid"

        /*@JvmStatic
        fun newInstance(param1: String, param2: String) =
            NavReportFragment().apply {
                arguments = Bundle().apply {
                    *//*putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)*//*
                }
            }*/
    }
}