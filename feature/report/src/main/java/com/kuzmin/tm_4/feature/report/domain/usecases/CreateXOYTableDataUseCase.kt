package com.kuzmin.tm_4.feature.report.domain.usecases

import android.util.Log
import com.kuzmin.tm_4.common.extension.toAltitudeString
import com.kuzmin.tm_4.common.util.CommonConstants.GROUP_ONE
import com.kuzmin.tm_4.feature.api.domain.model.model_complex_obj.McAndConstruction
import com.kuzmin.tm_4.feature.report.domain.model.XOYDataTable
import com.kuzmin.tm_4.feature.report.domain.model.sealed.ReportTable
import com.kuzmin.tm_4.feature.report.util.ConstructionValues
import javax.inject.Inject

class CreateXOYTableDataUseCase @Inject constructor(

) {
    operator fun invoke(mcAndC: McAndConstruction): List<ReportTable> {
        if (mcAndC.mcFull.levelsInfo.isNullOrEmpty()) return emptyList()
        val groupUuid = mcAndC.mcFull.groups?.first { it.groupNum == GROUP_ONE }?.uuid

        with(mcAndC) {
            if (mcFull.results.isNullOrEmpty()) return emptyList()
            val resultsGroupOne = mcFull.results!!
                .filter { it.measurementGroupUuid == groupUuid }.sortedBy { it.level }
            val resultsGroupTWO = mcFull.results!!
                .filter { it.measurementGroupUuid != groupUuid }.sortedBy { it.level }


            val xoyTableDataList = mutableListOf<ReportTable>()

            for (i in mcFull.levelsInfo!!.indices) {
                val finalShift = ConstructionValues.defineFinalShift(
                    construction.config,
                    resultsGroupOne[i].shiftMm,
                    resultsGroupTWO[i].shiftMm
                )
                val shiftLimit = ConstructionValues.defineShiftLimits(
                    construction.constructionType,
                    construction.height
                )
                xoyTableDataList.add(
                    ReportTable.XOYTableData(
                        altitude = mcFull.levelsInfo!![i].altitude.toAltitudeString(),
                        shiftX = resultsGroupOne[i].shiftMm,
                        shiftY = resultsGroupTWO[i].shiftMm,
                        shiftResult = finalShift,
                        isExceeded = ConstructionValues.isFinalExceeded(finalShift, shiftLimit)
                    )
                )
            }
            return xoyTableDataList
        }
    }
}