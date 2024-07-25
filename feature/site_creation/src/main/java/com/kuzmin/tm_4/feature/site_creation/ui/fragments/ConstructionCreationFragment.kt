package com.kuzmin.tm_4.feature.site_creation.ui.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.kuzmin.tm_4.common.R.color.color_danger
import com.kuzmin.tm_4.common.R.color.color_on_container_dark
import com.kuzmin.tm_4.common.R.color.color_on_surface
import com.kuzmin.tm_4.common.R.string.construction_saved_local
import com.kuzmin.tm_4.common.R.string.error
import com.kuzmin.tm_4.common.extension.formatToDateString
import com.kuzmin.tm_4.common.extension.toIntOrZero
import com.kuzmin.tm_4.common.extension.toast
import com.kuzmin.tm_4.common.util.CommonConstants
import com.kuzmin.tm_4.common.util.Generator
import com.kuzmin.tm_4.feature.api.domain.model.model_complex.ConstructionAndSections
import com.kuzmin.tm_4.feature.api.domain.model.site.Construction
import com.kuzmin.tm_4.feature.api.domain.model.site.Section
import com.kuzmin.tm_4.feature.api.extension.clearEditTextFields
import com.kuzmin.tm_4.feature.site_creation.R
import com.kuzmin.tm_4.feature.site_creation.databinding.FragmentConstructionCreationBinding
import com.kuzmin.tm_4.feature.site_creation.domain.model.Condition
import com.kuzmin.tm_4.feature.site_creation.domain.model.Condition.DATE
import com.kuzmin.tm_4.feature.site_creation.domain.model.Condition.HEIGHT
import com.kuzmin.tm_4.feature.site_creation.domain.model.Condition.SECTIONS_Q
import com.kuzmin.tm_4.feature.site_creation.domain.model.ErrorInfo
import com.kuzmin.tm_4.feature.site_creation.domain.model.sealed.CreationConstructionState
import com.kuzmin.tm_4.feature.site_creation.domain.model.sealed.CreationConstructionState.ConstructionValidationField
import com.kuzmin.tm_4.feature.site_creation.domain.model.sealed.CreationConstructionState.ConstructionValidationStatus
import com.kuzmin.tm_4.feature.site_creation.domain.model.sealed.CreationConstructionState.Error
import com.kuzmin.tm_4.feature.site_creation.domain.model.sealed.CreationConstructionState.SuccessConstructionBySiteUuid
import com.kuzmin.tm_4.feature.site_creation.domain.model.sealed.CreationConstructionState.SuccessConstructionDefault
import com.kuzmin.tm_4.feature.site_creation.domain.model.sealed.CreationConstructionState.Loading
import com.kuzmin.tm_4.feature.site_creation.domain.model.sealed.CreationConstructionState.SuccessConstructionSaved
import com.kuzmin.tm_4.feature.site_creation.domain.model.sealed.CreationState
import com.kuzmin.tm_4.feature.site_creation.extensions.getOriginalId
import com.kuzmin.tm_4.feature.site_creation.extensions.isSectionId
import com.kuzmin.tm_4.feature.site_creation.extensions.transformId
import com.kuzmin.tm_4.feature.site_creation.ui.adapters.ConstructionConfigDropDownAdapterHelper
import com.kuzmin.tm_4.feature.site_creation.ui.adapters.ConstructionTypeDropDownAdapterHelper
import com.kuzmin.tm_4.feature.site_creation.ui.viewmodels.ConstructionCreationViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Date
import javax.inject.Inject

@AndroidEntryPoint
class ConstructionCreationFragment : Fragment() {
    private var siteUuid: String? = null
    private var constructioUuid: String? = null

    private var _binding: FragmentConstructionCreationBinding? = null
    private val binding: FragmentConstructionCreationBinding get() = _binding!!

    private val navController by lazy {
        findNavController()
    }

    private val constructionCreationViewModel: ConstructionCreationViewModel by viewModels()

    private val sectionViews = mutableListOf<ViewGroup>()

    @Inject
    @ApplicationContext
    lateinit var appContext: Context

    @Inject
    lateinit var adapterHelperTypes: ConstructionTypeDropDownAdapterHelper

    @Inject
    lateinit var adapterHelperConfigures: ConstructionConfigDropDownAdapterHelper

    /*@Inject
    lateinit var textFieldValidator: TextFieldValidator*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            siteUuid = it.getString(
                getString(com.kuzmin.tm_4.common.R.string.site_uuid)
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConstructionCreationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("Creation", " Site Uuid: $siteUuid")

        setFabControlBarListeners()

        constructionCreationViewModel.populateConstructionCreation(siteUuid)

        setStaticFieldValidator(binding.llConstructionAll)

        with(binding) {
            with(atvConstructionTypeCreation) {
                setAdapter(adapterHelperTypes.getTypes(appContext))
                setDropDownBackgroundResource(com.kuzmin.tm_4.common.R.color.pop_up_background)
                setText(resources.getStringArray(R.array.construction_types)[0], false)
            }

            with(atvConstructionConfigCreation) {
                setAdapter(adapterHelperConfigures.getConfigures(appContext))
                setDropDownBackgroundResource(com.kuzmin.tm_4.common.R.color.pop_up_background)
                setText(resources.getStringArray(R.array.construction_configs)[0], false)
            }

            /*siteCreationViewModel.setConstructionFieldDataValidator(
                binding.clConstructionCommonData,
                ::getString,
                ::getColorById
            )*/
        }

        constructionCreationViewModel.creationState.observe(viewLifecycleOwner) {
            Log.d("Creation", "Construction CreationState: $it")
            when (it) {
                is CreationState.Loading -> {
                    //TODO
                }

                is CreationState.SuccessGetDefault -> {
                    Log.d("Creation", "Construction CreationState Success get: $it")
                    setDefaultConstructionData(Date().formatToDateString())
                }

                is CreationState.SuccessGetByUuid -> {
                    //TODO for updates realixation
                }

                is CreationState.Error -> {
                    appContext.toast(getString(error) + it.throwable)
                }

                is CreationState.ValidationField -> {
                    Log.d("Creation", "Validation field")
                    if (it.errorInfo != null) {
                        showError(it.errorInfo)
                    }
                }

                is CreationState.ValidationStatus -> {
                    if (it.isValid) {
                        appContext.toast(getString(R.string.construction_success))
                    } else {
                        appContext.toast(getString(R.string.construction_fault))
                    }
                }

                is CreationState.SuccessSavedToDb -> {
                    if (it.uuid != null) {
                        appContext.toast(getString(construction_saved_local))
                    } else {
                        appContext.toast(getString(R.string.construction_fault))
                    }
                }

                else -> throw RuntimeException("Wrong population result in ConstructionCreationFragment")
            }
        }
    }

    private fun setStaticFieldValidator(viewGroup: ViewGroup) {
        viewGroup.children.forEach { view ->
            if (view is ViewGroup && view.childCount > 1) setStaticFieldValidator(view)
            else if (view is TextInputLayout) {
                (view.editText ?: throw RuntimeException("No EditText in TextInputLayout"))
                    .apply { setValidator(view.id) }
            }
        }
    }

    private fun createDefaultSections(quantity: Int) {
        clearSectionLayout()

        val layoutInflater = LayoutInflater.from(appContext)

        for (i in 0 until quantity) {
            val sectionView =
                layoutInflater.inflate(
                    R.layout.item_section,
                    binding.clConstructionSectionsData,
                    false
                ).apply {
                    id = 100 + i
                }
            sectionView.findViewById<TextView>(R.id.tv_number_section_creation)
                .apply { setText(resources.getString(R.string.section_number, i + 1)) }

            if (sectionView is ViewGroup) {
                setDynamicFieldValidator(sectionView)

                binding.clConstructionSectionsData.addView(sectionView)
                sectionViews.add(sectionView)
            }
        }
    }

    private fun setDynamicFieldValidator(sectionView: ViewGroup) {
        sectionView.children.forEach {
            if (it is EditText) {
                it.setValidator(sectionView.id)
            }
        }
    }

    private fun clearSectionLayout() {
        with(binding.clConstructionSectionsData) {
            if (childCount > 1) removeViews(1, childCount - 1)
        }
    }

    private fun setFabControlBarListeners() {
        with(binding.llFabGroupConstructionCreation) {
            fabCleanCreation.setOnClickListener {
                binding.clConstructionSectionsData.clearEditTextFields()
            }
            fabOkCreation.setOnClickListener {
                if (constructionCreationViewModel.resultValidation()) {
                    constructionCreationViewModel.saveConstructionToDb(
                        collectConstructionAndSections()
                    )
                }
            }
            fabExitCreation.setOnClickListener {
                close()
            }
        }
        binding.fabDoubleCreation.setOnClickListener {
            binding.root.clearFocus()
            if (sectionViews.size > 1) {
                duplicateSection()
            } else {
                appContext.toast(getString(R.string.too_little_sections_to_duplicate))
            }
        }
    }

    private fun collectConstructionAndSections(): ConstructionAndSections {
        val construction = collectConstruction().also { constructioUuid = it.uuid }
        val sections = collectSections(construction.uuid)
        return ConstructionAndSections(construction, sections)
    }

    private fun collectConstruction(): Construction {
        with(binding) {
            return Construction(
                uuid = constructioUuid ?: Generator.generateUuid(),
                version = 1,
                description = etConstructionDescriptionCreation.text.toString(),
                status = CommonConstants.STATUS_ACTUAL,
                numOfSections = etConstructionQSectionsCreation.toIntOrZero(),
                height = etConstructionHeightCreation.toIntOrZero(),
                constructionType = atvConstructionTypeCreation.text.toString(),
                config = atvConstructionConfigCreation.text.toString(),
                measureLevels = null,
                siteUuid = siteUuid
                    ?: throw RuntimeException("Site uuid in Construction Fragment is null")
            )
        }
    }

    private fun collectSections(constructionUuid: String): List<Section> {
        val sections = mutableListOf<Section>()
        for (i in 0 until sectionViews.size) {
            with(sectionViews[i]) {
                val wBottom =
                    findViewById<EditText>(R.id.et_section_bottom_creation.transformId(id))
                        .toIntOrZero()
                val wTop =
                    findViewById<EditText>(R.id.et_section_top_creation.transformId(id))
                        .toIntOrZero()
                val height =
                    findViewById<EditText>(R.id.et_section_height_creation.transformId(id))
                        .toIntOrZero()

                sections.add(
                    Section(
                        uuid =
                        if (siteUuid.isNullOrEmpty()) throw RuntimeException("Site uuid in Construction Fragment is null")
                        else Generator.generateUuid(),
                        number = i,
                        wBottom = wBottom,
                        wTop = wTop,
                        height = height,
                        level = null,
                        status = CommonConstants.STATUS_ACTUAL,
                        constructionUuid = constructionUuid
                    )
                )
            }
        }
        return sections
    }

    private fun duplicateSection() {
        var lastNotEmptyIndex = -1
        for (i in 0 until sectionViews.size) {
            if (sectionViews[i].hasEmpties() && lastNotEmptyIndex > -1) {
                sectionViews[i].requestFocus()
                copyDataFromPreviousSection(i - 1, i)
                break
            } else {
                lastNotEmptyIndex = i
            }
        }
    }

    private fun copyDataFromPreviousSection(prev: Int, current: Int) {
        sectionViews[current].children.forEach {
            if (it is EditText) {
                it.setText(
                    sectionViews[prev].findViewById<EditText>(
                        it.id
                            .getOriginalId(sectionViews[current].id)
                            .transformId(sectionViews[prev].id)
                    ).text
                )
            }
        }
    }


    private fun setDefaultConstructionData(date: String) {
        binding.etConstructionDateCreation.setText(date)
    }

    private fun EditText.setValidator(parentId: Int) {
        if (!idCheckList.containsKey(id)) return

        val defaultId = id
        if (parentId.isSectionId()) id.transformId(parentId)
        constructionCreationViewModel.registerViewInValidator(
            id,
            parentId,
            idCheckList[defaultId] ?: throw RuntimeException("Wrong view ID to validate.")
        )
        setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) return@setOnFocusChangeListener
            setSelection(0)                                  //set cursor to start of the word
            constructionCreationViewModel.validate(id, text.toString())
        }

        if (id == R.id.et_construction_q_sections_creation) {
            doAfterTextChanged {
                createDefaultSections(
                    if (it.toString().isEmpty()) {
                        0
                    } else {
                        it.toString().toInt()
                    }
                )
            }
        }
    }

    private fun ViewGroup.hasEmpties(): Boolean {
        children.forEach {
            if (it is EditText) {
                if (it.text.isEmpty()) return true
            }
        }
        return false
    }

    /*private fun getStringByRes(stringRes: Int): String {
        return getString(stringRes)
    }*/

    private fun showError(errorInfo: ErrorInfo) {
        if (errorInfo.parentId.isSectionId()) {
            showSectionViewError(errorInfo)
        } else {
            showCommonConstructionDataError(errorInfo)
        }
    }

    private fun showSectionViewError(errorInfo: ErrorInfo) {
        val etSectionParameter =
            binding.clConstructionSectionsData.findViewById<EditText>(errorInfo.viewId)
        if (errorInfo.isCorrect) {
            etSectionParameter.apply {
                setTextColor(getColorById(color_on_surface))
            }
        } else {
            etSectionParameter.apply {
                if (text.isNullOrEmpty()) {
                    setText("0")
                }
                setTextColor(getColorById(color_danger))
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun showCommonConstructionDataError(errorInfo: ErrorInfo) {
        val textInputLayout =
            binding.clConstructionCommonData.findViewById<TextInputLayout>(errorInfo.parentId)
                ?: throw RuntimeException("Found ID is not TextInputLayout's")

        if (errorInfo.isCorrect) {
            textInputLayout.isErrorEnabled = false
        } else {
            textInputLayout.error = getString(
                if (textInputLayout.width > binding.clConstructionCommonData.width / 2) {
                    errorInfo.errorHelpTextId
                } else {
                    errorInfo.errorHelpTextShortId
                }
                    ?: throw RuntimeException("Error help text needed.")
            )
        }

    }

    private fun getColorById(colorId: Int): Int {
        return ContextCompat.getColor(appContext, colorId)
    }

    private fun close() {
        navController.popBackStack()
    }

    companion object {
        val idCheckList = mapOf(
            R.id.et_construction_date_creation to DATE,
            //R.id.et_construction_description_creation to FOR_EMPTY,
            R.id.et_construction_height_creation to HEIGHT,
            R.id.et_construction_q_sections_creation to SECTIONS_Q,
            R.id.et_section_height_creation to Condition.SECTION_HEIGHT,
            R.id.et_section_top_creation to Condition.SECTION_WIDTH_OR_TOP,
            R.id.et_section_bottom_creation to Condition.SECTION_WIDTH_OR_TOP
        )
    }
}