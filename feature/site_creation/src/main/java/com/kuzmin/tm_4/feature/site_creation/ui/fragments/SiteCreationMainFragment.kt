package com.kuzmin.tm_4.feature.site_creation.ui.fragments

import android.content.Context
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.core.view.children
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.kuzmin.tm_4.common.R.id.site_construction_creation_nav_graph
import com.kuzmin.tm_4.common.R.id.site_creation_nav_graph
import com.kuzmin.tm_4.common.R.id.sites_nav_graph
import com.kuzmin.tm_4.common.R.string.error
import com.kuzmin.tm_4.common.extension.toIntOrZero
import com.kuzmin.tm_4.common.extension.toast
import com.kuzmin.tm_4.common.util.Generator
import com.kuzmin.tm_4.feature.api.domain.model.Tenant
import com.kuzmin.tm_4.feature.api.domain.model.site.Address
import com.kuzmin.tm_4.feature.api.domain.model.site.Site
import com.kuzmin.tm_4.feature.api.domain.model.site.SiteParams
import com.kuzmin.tm_4.feature.api.extension.clearEditTextFields
import com.kuzmin.tm_4.feature.site_creation.R
import com.kuzmin.tm_4.feature.site_creation.databinding.FragmentSiteCreationMainBinding
import com.kuzmin.tm_4.feature.site_creation.domain.model.Condition.*
import com.kuzmin.tm_4.feature.site_creation.domain.model.sealed.CreationState
import com.kuzmin.tm_4.feature.site_creation.domain.model.sealed.CreationState.Error
import com.kuzmin.tm_4.feature.site_creation.domain.model.sealed.CreationState.SuccessSavedToDb
import com.kuzmin.tm_4.feature.site_creation.domain.model.sealed.CreationState.SuccessGetByUuid
import com.kuzmin.tm_4.feature.site_creation.domain.model.sealed.CreationState.SuccessGetDefault
import com.kuzmin.tm_4.feature.site_creation.domain.model.ErrorInfo
import com.kuzmin.tm_4.feature.site_creation.domain.model.SiteCreationStateData
import com.kuzmin.tm_4.feature.site_creation.ui.viewmodels.SiteCreationViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@AndroidEntryPoint
class SiteCreationMainFragment : Fragment() {

    private var siteUuid: String? = null

    private var _binding: FragmentSiteCreationMainBinding? = null
    private val binding: FragmentSiteCreationMainBinding get() = _binding!!

    private val siteCreationViewModel: SiteCreationViewModel by viewModels()

    private val navController by lazy {
        findNavController()
    }

    @Inject
    @ApplicationContext
    lateinit var appContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        /*if (savedInstanceState == null && isFragmentInBackStack(site_creation_nav_graph)) {
            navController.popBackStack(site_creation_nav_graph, false)
        }*/
        super.onCreate(savedInstanceState)
        Log.d("Restore", "On create Site Fragment. SavedInstanceState: $savedInstanceState")
        arguments?.let {
            siteUuid = it.getString(getString(com.kuzmin.tm_4.common.R.string.site_uuid))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSiteCreationMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAddConstructionBtnListener()

        setAddPhotoBtnListener()

        setFabControlBarListeners()

        setFieldValidator(binding.clAllSiteData)

        siteCreationViewModel.stateData.observe(viewLifecycleOwner) {
            binding.etSiteNameCreation.setText(it.siteName)
        }

        siteCreationViewModel.creationState.observe(viewLifecycleOwner) {
            Log.d("Creation", "Site CreationState: $it")
            when (it) {
                is SuccessGetDefault -> {
                    Log.d("Creation", "Success get default")
                }

                is SuccessGetByUuid -> {
                    Log.d("Creation", "Success get by id")
                }

                is SuccessSavedToDb -> {
                    Log.d("Creation", "Success saved and try to launch constr fragment")
                    siteUuid = it.uuid
                    if (it.launchFlag == SAVE_FOR_LAUNCH) {
                        launchConstructionCreationFragment(siteUuid)
                    }
                }

                is CreationState.ValidationField -> {
                    if (it.errorInfo == null) return@observe
                    showError(it.errorInfo)
                }

                is CreationState.ValidationStatus -> {
                    if (it.isValid) {
                        Log.d("Creation", "Permitted")
                        appContext.toast(getString(R.string.site_success))
                    } else {
                        siteUuid = null
                        appContext.toast(getString(R.string.wrong_site_data))
                    }
                }

                is Error -> {
                    Log.d("Creation", "Error")
                    appContext.toast(getString(error) + "${it.throwable}")

                }

                else -> {
                    Log.d("Creation", "Else")

                }
            }
        }
    }

    private fun setAddConstructionBtnListener() {
        Log.d("Creation", "Site fragment SiteUuid = $siteUuid")
        binding.btnAddConstrCreation.setOnClickListener {
            binding.root.clearFocus()
            if (siteCreationViewModel.resultValidation()) {
                siteCreationViewModel.saveSiteToDb(collectSite(), SAVE_FOR_LAUNCH)
            }
        }
    }

    private fun setAddPhotoBtnListener() {
        // TODO
    }

    private fun launchConstructionCreationFragment(siteUuid: String?) {
        if (siteUuid.isNullOrEmpty()) appContext.toast(getString(R.string.wrong_site_data))
        else {
            navController.navigate(
                site_construction_creation_nav_graph,
                bundleOf(
                    getString(com.kuzmin.tm_4.common.R.string.site_uuid) to siteUuid
                )
            )
        }
    }

    private fun setFabControlBarListeners() {
        with(binding.llFabGroupSiteCreation) {
            fabCleanCreation.setOnClickListener {
                binding.clAllSiteData.clearEditTextFields()
            }
            fabOkCreation.setOnClickListener {
                if (siteCreationViewModel.resultValidation()) {
                    siteCreationViewModel.saveSiteToDb(collectSite(), SAVE_NOT_LAUNCH)
                }
            }
            fabExitCreation.setOnClickListener {
                close()
            }
        }
    }

    private fun collectSite(): Site {
        with(binding) {
            return Site(
                SiteParams(
                    siteUuid = siteUuid ?: Generator.generateUuid(),
                    name = etSiteNameCreation.text.toString(),
                    description = etSiteDescriptionCreation.text.toString(),
                    latitude = etSiteLatitudeCreation.text.toString().toDouble(),
                    longitude = etSiteLongitudeCreation.text.toString().toDouble(),
                    siteType = etSiteTypeCreation.toIntOrZero(),
                    siteTypeDescription = etSiteTypeDescriptionCreation.text.toString(),
                ),
                Tenant(
                    uuid = "", //It'll be generated in data module
                    name = etSiteTenantNameCreation.text.toString(),
                    null
                ),
                Address(
                    uuid = "", //It'll be generated in data module
                    country = etCountryCreation.text.toString(),
                    region = etRegionCreation.text.toString(),
                    regionCode = etCodeCreation.toIntOrZero(),
                    subRegion = etSubRegionCreation.text.toString(),
                    city = etCityCreation.text.toString(),
                    street = etStreetCreation.text.toString(),
                    building = etBuildingCreation.text.toString(),
                    postalCode = etPostalCreation.text.toString()
                ),
                listOf(),
                listOf(),
                listOf()
            )
        }
    }

    private fun setFieldValidator(viewGroup: ViewGroup) {
        viewGroup.children.forEach { view ->
            if (view is ViewGroup && view.childCount > 1) setFieldValidator(view)
            else if (view is TextInputLayout) {
                (view.editText ?: throw RuntimeException("No EditText in TextInputLayout"))
                    .apply { setValidator(view.id) }
            }
        }
    }

    private fun showError(errorInfo: ErrorInfo) {
        val til = binding.clAllSiteData.findViewById<TextInputLayout>(errorInfo.parentId)
        if (errorInfo.isCorrect) {
            til.isErrorEnabled = false
        } else {
            til.error = when (errorInfo.viewId) {
                R.id.et_country_creation -> getString(R.string.must_be_filled_short)
                else -> getString(R.string.must_be_filled)
            }
        }
    }

    private fun close() {
        navController.popBackStack(sites_nav_graph, false)
    }

    private fun EditText.setValidator(parentId: Int) {
        if (!idCheckList.containsKey(id)) return
        siteCreationViewModel.registerViewInValidator(
            id,
            parentId,
            idCheckList[id] ?: throw RuntimeException("Wrong view ID to validate.")
        )
        setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) return@setOnFocusChangeListener
            setSelection(0)
            siteCreationViewModel.validate(id, text.toString())
        }
    }

    override fun onPause() {
        super.onPause()
        siteCreationViewModel.setToDefaultState()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        siteCreationViewModel.setState(
            SiteCreationStateData(siteName = binding.etSiteNameCreation.text.toString())
        )
    }

    /*fun NavController.isFragmentInBackStack(destinationId: Int) =
        try {
            getBackStackEntry(destinationId)
            true
        } catch (e: Exception) {
            false
        }

    fun Fragment.isFragmentInBackStack(destinationId: Int) =
        try {
            findNavController().getBackStackEntry(destinationId)
            true
        } catch (e: Exception) {
            false
        }*/
    /*if (isFragmentInBackStack(R.id.myFragment)){
        findNavController().popBackStack(R.id.myFragment,false)
    } else {
        val action = MyCurrentFragmentDirections.actionToMyFragment()
        findNavController().navigateSafe(action)
    }*/

    /*override fun onSaveInstanceState(): Parcelable {
        val savedState = SavedState(super.onSaveInstanceState())
        savedState.ssIsOpened = isOpened
        return savedState
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        super.onRestoreInstanceState(state)
        if (state is SavedState) {
            _isOpened = state.ssIsOpened
            visibility = if (isOpened) View.VISIBLE else View.GONE
        }
    }*/

    companion object {
        const val SAVE_FOR_LAUNCH = 1
        const val SAVE_NOT_LAUNCH = 0

        const val SITE_CREATION_STATE_REQUEST = "state_req"

        val idCheckList = mapOf(
            R.id.et_site_name_creation to FOR_EMPTY,
            R.id.et_site_tenant_name_creation to FOR_EMPTY,
            R.id.et_country_creation to FOR_EMPTY,
            R.id.et_region_creation to FOR_EMPTY,
            R.id.et_site_latitude_creation to GEO_VALUE,
            R.id.et_site_longitude_creation to GEO_VALUE
        )
    }
}