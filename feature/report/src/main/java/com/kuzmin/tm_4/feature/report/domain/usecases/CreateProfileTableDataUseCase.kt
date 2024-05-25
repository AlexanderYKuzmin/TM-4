package com.kuzmin.tm_4.feature.report.domain.usecases

import android.util.Log
import com.kuzmin.tm_4.common.extension.toAltitudeString
import com.kuzmin.tm_4.feature.api.domain.model.model_complex_obj.McAndConstruction
import com.kuzmin.tm_4.feature.report.domain.model.ProfileTableData
import com.kuzmin.tm_4.feature.report.domain.model.sealed.ReportTable
import com.kuzmin.tm_4.feature.report.util.ConstructionValues
import javax.inject.Inject

class CreateProfileTableDataUseCase @Inject constructor(

) {
    operator fun invoke(mcAndC: McAndConstruction, groupNum: Int): List<ReportTable> {
        if (mcAndC.mcFull.levelsInfo.isNullOrEmpty()) return emptyList()
        val groupUuid = mcAndC.mcFull.groups?.first { it.groupNum == groupNum }?.uuid
        val filteredResults = mcAndC.mcFull.results
            ?.filter { it.measurementGroupUuid == groupUuid }
            ?.sortedBy { it.level }

        val filteredMeasurements = mcAndC.mcFull.measurements
            ?.filter { it.measurementGroupUuid == groupUuid }
            ?.sortedBy { it.level }

        val profileTableDataList = mutableListOf<ReportTable.ProfileTableData>()

        with(mcAndC.mcFull) {
            for (i in levelsInfo!!.indices) {
                val shiftLimit = ConstructionValues.defineShiftLimits(
                    mcAndC.construction.constructionType,
                    mcAndC.construction.height
                )
                profileTableDataList.add(
                    ReportTable.ProfileTableData(
                        altitude = levelsInfo!![i].altitude.toAltitudeString(),
                        left = filteredMeasurements!![i].leftAngleCl,
                        right = filteredMeasurements[i].rightAngleCl,
                        average = filteredResults!![i].averageClCr,
                        shift = filteredResults[i].shiftMm,
                        isExceeded = ConstructionValues.isProfileExceeded(
                            filteredResults[i].shiftMm,
                            shiftLimit
                        )
                    )
                )
            }
        }
        return profileTableDataList
    }
}