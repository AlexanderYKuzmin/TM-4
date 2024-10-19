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
import com.kuzmin.tm_4.feature.api.domain.model.sealed.SiteActionResult
import com.kuzmin.tm_4.feature.measurements.databinding.FragmentNavMeasurementsBinding
import com.kuzmin.tm_4.feature.measurements.ui.adapters.MeasurementConstructionsAdapter
import com.kuzmin.tm_4.feature.measurements.ui.model.McParent
import com.kuzmin.tm_4.feature.measurements.ui.viewmodels.NavMeasurementsViewModel
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

        navMeasurementsViewModel.siteActionResult.observe(viewLifecycleOwner) {
            when(it) {
                is SiteActionResult.Loading -> TODO()
                is SiteActionResult.SuccessMcFullList -> {
                    with(it) {
                        if (!mcFullList.isNullOrEmpty()) {
                            adapter.submitList(
                                mcFullList!!.map { mc ->
                                    McParent(
                                        uuid = mc.mc.uuid,
                                        date = mc.mc.completedDate,
                                        employee = mc.mc.employeeName,
                                        isServiceable = mc.mc.isServiceable,
                                        levelsInfo = mc.levelsInfo
                                    )
                                }
                            )
                        }
                    }
                }
                is SiteActionResult.Error -> {
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
            navMeasurementsViewModel.saveMcUuidToDatastore(mcUuid)

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