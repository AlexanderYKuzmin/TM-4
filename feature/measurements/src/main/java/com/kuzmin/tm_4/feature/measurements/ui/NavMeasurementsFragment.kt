package com.kuzmin.tm_4.feature.measurements.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kuzmin.tm_4.common.R
import com.kuzmin.tm_4.common.util.CommonConstants
import com.kuzmin.tm_4.feature.api.model.SiteDataStore
import com.kuzmin.tm_4.feature.measurements.databinding.FragmentNavMeasurementsBinding
import com.kuzmin.tm_4.feature.measurements.domain.model.MeasurementConstructionResult.Error
import com.kuzmin.tm_4.feature.measurements.domain.model.MeasurementConstructionResult.Loading
import com.kuzmin.tm_4.feature.measurements.domain.model.MeasurementConstructionResult.Success
import com.kuzmin.tm_4.feature.measurements.ui.adapters.MeasurementConstructionsAdapter
import com.kuzmin.tm_4.feature.measurements.ui.model.McParent
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@AndroidEntryPoint
class NavMeasurementsFragment : Fragment() {

    @Inject
    @ApplicationContext
    lateinit var appContext: Context

    private val navController by lazy {
        findNavController()
    }

    private var _binding: FragmentNavMeasurementsBinding? = null
    private val binding get() = _binding!!

    private val navMeasurementsViewModel: NavMeasurementsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNavMeasurementsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = MeasurementConstructionsAdapter(appContext)
        binding.rvNavMeasuremnts.adapter = adapter

        setAdapterItemClickAction(adapter)

        navMeasurementsViewModel.getAllMeasurementConstructions()

        navMeasurementsViewModel.getAndSaveFullSiteToDbAsTemp()

        navMeasurementsViewModel.measurementConstructionResult.observe(viewLifecycleOwner) {
            when(it) {
                is Loading -> TODO()
                is Success -> {
                    with(it) {
                        measurementConstructionList.forEach {
                            Log.d("MC", "${it.employeeName}")
                        }
                        adapter.submitList(
                            measurementConstructionList.map { mc ->
                                McParent(
                                    uuid = mc.uuid,
                                    date = mc.completedDate,
                                    employee = mc.employeeName,
                                    isServiceable = mc.isServiceable,
                                    levelsInfo = mc.levelsInfo
                                )
                            }
                        )
                    }
                }
                is Error -> {
                    Log.d("MC", "ERROR: ${it.throwable}")
                    //appContext.showShortMessage(getString(string.error_loading_list))
                }
                else -> throw RuntimeException("Wrong Measurement_construction result.")
            }
        }
    }

    private fun setAdapterItemClickAction(adapter: MeasurementConstructionsAdapter) {
        adapter.onItemClickListener = { mcUuid ->
            Log.d("MC", "On item click! ID: $mcUuid")

            navController.navigate(
                R.id.measurement_view_nav_graph,
                bundleOf(
                    MC_UUID to mcUuid
                )
            )
            //onSitesAdapterClickListener?.onItemSiteClick("")
            // TODO notice main activity about it
        }
    }


    companion object {

        const val MC_UUID: String = "mc_uuid"

        @JvmStatic
        fun newInstance() = NavMeasurementsFragment()
    }
}