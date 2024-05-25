package com.kuzmin.tm_4.feature.report.ui.viewmodels

import android.util.Log
import com.kuzmin.tm_4.common.util.CommonConstants.GROUP_ALL
import com.kuzmin.tm_4.feature.api.domain.model.model_complex_obj.McAndConstruction
import com.kuzmin.tm_4.feature.api.domain.usecases.GetMcAndConstructionUseCase
import com.kuzmin.tm_4.feature.report.domain.model.sealed.ReportTable
import com.kuzmin.tm_4.feature.report.domain.usecases.CreateProfileTableDataUseCase
import com.kuzmin.tm_4.feature.report.domain.usecases.CreateXOYTableDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ItemTablesViewModel @Inject constructor(
    getMcAndConstructionUseCase: GetMcAndConstructionUseCase,
    private val createProfileTableDataUseCase: CreateProfileTableDataUseCase,
    private val createXOYTableDataUseCase: CreateXOYTableDataUseCase
) : ReportFeatureViewModel(getMcAndConstructionUseCase) {

    fun createTableData(mcAndC: McAndConstruction, groupNum: Int): List<ReportTable> {
        try {
            return if (groupNum != GROUP_ALL) {
                createProfileTableDataUseCase(mcAndC, groupNum)
            } else {
                createXOYTableDataUseCase(mcAndC)
            }
        } catch (e: Exception) {
            Log.d("ItemTableVM", e.toString())
        }
        return emptyList()
    }
}