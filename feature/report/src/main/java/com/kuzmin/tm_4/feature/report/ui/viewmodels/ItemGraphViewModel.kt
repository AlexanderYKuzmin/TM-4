package com.kuzmin.tm_4.feature.report.ui.viewmodels

import com.kuzmin.tm_4.feature.api.domain.model.model_complex_obj.McAndConstruction
import com.kuzmin.tm_4.feature.api.domain.usecases.GetMcAndConstructionUseCase
import com.kuzmin.tm_4.feature.report.domain.model.ChartDataBuilder
import com.kuzmin.tm_4.feature.report.domain.usecases.CreateChartDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ItemGraphViewModel @Inject constructor(
    getMcAndConstructionUseCase: GetMcAndConstructionUseCase,
    private val createChartDataUseCase: CreateChartDataUseCase
) : ReportFeatureViewModel(getMcAndConstructionUseCase) {


    fun getChartData(mvAndC: McAndConstruction, groupNum: Int): ChartDataBuilder {
        return createChartDataUseCase.createChartData(mvAndC, groupNum)
    }
}