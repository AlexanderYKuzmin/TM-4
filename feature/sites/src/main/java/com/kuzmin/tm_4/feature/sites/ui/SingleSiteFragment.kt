package com.kuzmin.tm_4.feature.sites.ui

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
import com.kuzmin.tm_4.common.extension.formatToDateString
import com.kuzmin.tm_4.feature.sites.R
import com.kuzmin.tm_4.feature.sites.databinding.FragmentSiteBinding
import com.kuzmin.tm_4.feature.sites.domain.model.sealed.SiteResult
import com.kuzmin.tm_4.feature.api.model.site.Construction
import com.kuzmin.tm_4.feature.api.model.site.MeasurementConstruction
import com.kuzmin.tm_4.feature.api.model.site.Site
import com.kuzmin.tm_4.feature.sites.ui.NavSitesServerFragment.Companion.CONSTRUCTION_UUID
import com.kuzmin.tm_4.feature.sites.ui.NavSitesServerFragment.Companion.SITE_UUID
import com.kuzmin.tm_4.feature.sites.ui.NavSitesServerFragment.Companion.STORAGE_TYPE
import com.kuzmin.tm_4.feature.sites.ui.NavSitesServerFragment.Companion.TITLE
import com.kuzmin.tm_4.feature.sites.ui.adapters.PhotoAdapter
import com.kuzmin.tm_4.feature.sites.ui.viewmodels.SiteViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@AndroidEntryPoint
class SingleSiteFragment : Fragment() {
    private var _binding: FragmentSiteBinding? = null
    private val binding get() = _binding!!

    @Inject
    @ApplicationContext
    lateinit var appContext: Context

    private val navController by lazy {
        findNavController()
    }

    private var title: String? = null
    private var siteUuid: String? = null
    private var constructionUuid: String? = null
    private var storage: Int? = null

    private var currentActualConstrIndex: Int? = null
    private var site: Site? = null
    private var currentConstruction: Construction? = null
    private var currentConstructionMeasurement: MeasurementConstruction? = null
    private var currentConstructionUuid: String? = null

    private val siteViewModel: SiteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSiteBinding.inflate(inflater, container, false)

        arguments?.apply {
            title = getString(TITLE)
            siteUuid = getString(SITE_UUID)
            constructionUuid = getString(CONSTRUCTION_UUID)
            storage = getInt(STORAGE_TYPE)
        }

        Log.d("MainActivity", "arguments: siteId: $siteUuid, storage: $storage")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val adapter = PhotoAdapter()
        binding.rvSitePhotos.adapter = adapter


        //setOnAdapterItemClickActions(adapter)
        siteViewModel.getSiteByIdNoSections(siteUuid, constructionUuid, storage)
        siteViewModel.siteResult.observe(viewLifecycleOwner) {
            when(it) {
                is SiteResult.SuccessSingle -> {
                    Log.d("Site", "Successful Site loaded. site.construction: ${it.site.constructions.first().toString()}")
                    site = it.site
                    adapter.submitList(it.site.photos)
                    showSiteData()
                }
                is SiteResult.Error -> {
                    Toast.makeText(
                        appContext,
                        getString(R.string.error_site_loading) + " " + it.throwable.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d("SitesFragment", "error: ${it.throwable}")}
                is SiteResult.Loading -> showProgress()
                else -> throw RuntimeException("Wrong getSiteById response.")
            }
        }
    }

    private fun showProgress() {

    }

    private fun showSiteData() {
        Log.d("Site", "---seeking logs_1---")
        with(binding) {
            with(site!!) {
                Log.d("Site", "---seeking logs_2---")
                tvAddressSite.text = address.toString()
                tvPositionSite.text = siteParams.coordinates
                tvSiteType.text =
                    String.format("%d-%s", siteParams.siteType, siteParams.siteTypeDescription)
                tvSiteOwner.text = tenant.name
            }
        }
        site?.let{

            currentConstruction = it.constructions.firstOrNull { construction ->
                construction.status == ACTUAL
            }

            currentConstructionMeasurement = if (currentConstruction != null) {
                it.measurementsConstructions?.firstOrNull { mc ->
                    mc.constructionUuid == currentConstruction!!.uuid
                }
            } else null
        }
        fillConstructionForm()
        fillMeasurementConstructionForm()
    }

    private fun fillConstructionForm() {
        with(binding) {
            if (currentConstruction == null) return
            with(currentConstruction!!) {
                tvConstrVersion.text = version.toString()
                tvSiteConstrDesc.text = description
                tvSiteConstrType.text = constructionType
                tvSiteConstrConfig.text = config
                tvSiteConstrHeight.text = height.toString()
                tvSiteConstrSections.text = numOfSections.toString()
            }
        }
    }

    private fun fillMeasurementConstructionForm() {
        if (currentConstructionMeasurement == null) return
        with(binding) {
            with(currentConstructionMeasurement!!) {
                tvSiteConstrEmployee.text = employeeName
                tvSiteConstrMeasureDate.text = completedDate?.formatToDateString() ?: ""
                tvSiteConstrConclusion.text = isCompleted.toString() // TODO satisfactory
            }
        }
    }
    companion object {
        private const val ACTUAL = "actual"
    }
}