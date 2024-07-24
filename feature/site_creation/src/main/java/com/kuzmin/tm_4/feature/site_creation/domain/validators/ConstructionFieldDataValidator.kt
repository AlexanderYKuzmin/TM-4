package com.kuzmin.tm_4.feature.site_creation.domain.validators

import android.graphics.Color
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.children
import com.google.android.material.textfield.TextInputLayout
import com.kuzmin.tm_4.common.extension.toDate
import com.kuzmin.tm_4.common.util.CommonConstants.MAX_HEIGHT_MM
import com.kuzmin.tm_4.common.util.CommonConstants.MAX_SECTION_NUMBER
import com.kuzmin.tm_4.common.util.CommonConstants.MIN_HEIGHT_MM
import com.kuzmin.tm_4.common.util.CommonConstants.MIN_SECTION_NUMBER
import com.kuzmin.tm_4.feature.api.domain.model.model_complex.ConstructionAndSections
import com.kuzmin.tm_4.feature.api.domain.model.site.Construction
import com.kuzmin.tm_4.feature.api.domain.model.site.Section
import com.kuzmin.tm_4.feature.site_creation.R
import java.util.Date
import javax.inject.Inject

class ConstructionFieldDataValidator @Inject constructor() {

    private lateinit var getString: (Int) -> String
    private lateinit var getColor: (Int) -> Int

    private var isSectionFault = false

    private var isConstructionFault = false

    private val idsCheckList = listOf(
        R.id.et_construction_date_creation,
        R.id.et_construction_height_creation,
        R.id.et_construction_q_sections_creation,
        R.id.et_section_bottom_creation,
        R.id.et_section_top_creation,
        R.id.et_section_height_creation,
    )

    fun validateAll(viewGroup: ViewGroup): Boolean {
        return validateForEmpties(viewGroup) && isConstructionFault && isSectionFault
    }

    private fun validateForEmpties(viewGroup: ViewGroup): Boolean {
        var emptyCheckResult = false
        viewGroup.children.forEach {
            if (it is ViewGroup) validateForEmpties(it)
            else if (it.id in idsCheckList) {
                emptyCheckResult = !(it as EditText).text.isNullOrEmpty()
            }
        }
        return emptyCheckResult
    }

    fun setTextChangedListener(view: ViewGroup, getString: (Int) -> String, getColor: (Int) -> Int) {
        this.getString = getString
        this.getColor = getColor

        when (view.id) {
            R.id.cl_construction_common_data -> {
                setCommonDataChangedListener(view)
            }
            R.id.cl_construction_sections_data -> {
                setSectionDataChangedListener(view)
            }
        }
    }

    private fun setCommonDataChangedListener(view: ViewGroup) {
        view.children.forEach { textInputLayout ->
            if (textInputLayout is TextInputLayout) {

                textInputLayout.editText?.setOnFocusChangeListener { et, hasFocus ->
                    if (hasFocus) return@setOnFocusChangeListener

                    et as EditText
                    when(et.id)  {
                        R.id.et_construction_date_creation -> {
                            val date = et.text.toString().toDate()
                            if (date == null || date > Date()) {
                                textInputLayout.error = getString(R.string.height_field_error)
                            } else textInputLayout.isErrorEnabled = false
                        }
                        R.id.et_construction_height_creation -> {
                            val height = et.text.toString().toIntOrNull() ?: 0
                            if (height < MIN_HEIGHT_MM || height > MAX_HEIGHT_MM) {
                                textInputLayout.error = getString(R.string.height_field_error)
                            } else textInputLayout.isErrorEnabled = false
                        }
                        R.id.et_construction_q_sections_creation -> {
                            val q = et.text.toString().toIntOrNull() ?: 0
                            if (q < MIN_SECTION_NUMBER || q > MAX_SECTION_NUMBER) {
                                textInputLayout.error = getString(R.string.q_sections_field_error)
                            } else textInputLayout.isErrorEnabled = false
                        }
                    }
                }
            }
        }
    }

    private fun setSectionDataChangedListener(viewGroup: ViewGroup) {
        viewGroup.children.forEach { v ->
            if (v is ViewGroup) setSectionDataChangedListener(v)
            else {
                when(v) {
                    is EditText -> {
                        v.setOnFocusChangeListener { _, hasFocus ->
                            if (hasFocus) return@setOnFocusChangeListener
                            val value = v.text.toString().toIntOrNull() ?: 0
                            if (value <= 0) {
                                v.setTextColor(getColor(com.kuzmin.tm_4.common.R.color.color_danger))
                            } else v.setTextColor(Color.BLACK)
                        }
                    }
                }
            }
        }
    }
}