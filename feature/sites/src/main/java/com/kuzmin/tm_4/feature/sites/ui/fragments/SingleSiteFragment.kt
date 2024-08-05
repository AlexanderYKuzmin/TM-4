package com.kuzmin.tm_4.feature.sites.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kuzmin.tm_4.common.R
import com.kuzmin.tm_4.common.extension.formatToDateString
import com.kuzmin.tm_4.feature.api.api.FeatureIsActiveListener
import com.kuzmin.tm_4.feature.api.domain.model.site.Construction
import com.kuzmin.tm_4.feature.api.domain.model.site.MeasurementConstruction
import com.kuzmin.tm_4.feature.api.domain.model.site.Site
import com.kuzmin.tm_4.feature.sites.databinding.FragmentSiteBinding
import com.kuzmin.tm_4.feature.sites.domain.model.sealed.SiteResult
import com.kuzmin.tm_4.feature.sites.ui.adapters.PagerPhotoAdapter
import com.kuzmin.tm_4.feature.sites.ui.viewmodels.SiteViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@AndroidEntryPoint
class SingleSiteFragment : Fragment() {

    private var featureIsActiveListener: FeatureIsActiveListener? = null

    private var removeActionbarBackArrow: (() -> Unit)? = null

    private var _binding: FragmentSiteBinding? = null
    private val binding get() = _binding!!

    @Inject
    @ApplicationContext
    lateinit var appContext: Context

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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FeatureIsActiveListener) {
            featureIsActiveListener = context
        } else {
            throw RuntimeException("Activity must implement FeatureIsActiveListener")
        }
        if (context is AppCompatActivity) removeActionbarBackArrow = {
            context.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            title = getString(appContext.getString(R.string.title))
            siteUuid = getString(appContext.getString(R.string.site_uuid))
            constructionUuid = getString(appContext.getString(R.string.construction_uuid))
            storage = getInt(appContext.getString(R.string.storage_type))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSiteBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val adapter = PagerPhotoAdapter(this)
        binding.vpSitePhotos.adapter = adapter

        siteViewModel.getSiteByIdNoSections(siteUuid, constructionUuid, storage)
        siteViewModel.siteResult.observe(viewLifecycleOwner) {
            when(it) {
                is SiteResult.SuccessSingle -> {
                    site = it.site
                    adapter.photos = it.site.photos
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
        with(binding) {
            with(site!!) {
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

    override fun onResume() {
        removeActionbarBackArrow?.invoke()
        featureIsActiveListener?.onFeatureIsActive(R.id.site_nav_graph)
        super.onResume()
    }

    companion object {
        private const val ACTUAL = "actual"
    }
}