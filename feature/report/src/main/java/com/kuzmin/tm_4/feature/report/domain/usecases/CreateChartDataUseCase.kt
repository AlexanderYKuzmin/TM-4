package com.kuzmin.tm_4.feature.report.domain.usecases

import android.util.Log
import com.kuzmin.tm_4.common.util.CommonConstants.GROUP_ALL
import com.kuzmin.tm_4.common.util.CommonConstants.GROUP_ONE
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.model_complex.McAndConstruction
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.McLevelInfo
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.Result
import com.kuzmin.tm_4.feature.report.domain.model.ChartDataBuilder
import com.kuzmin.tm_4.feature.report.domain.model.ChartDataBuilderXOY
import com.kuzmin.tm_4.feature.report.domain.model.ChartDataBuilderXYProfile
import com.kuzmin.tm_4.feature.report.util.ConstructionValues
import javax.inject.Inject
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

class CreateChartDataUseCase @Inject constructor(
    private val chartDataBuilderXYProfile: ChartDataBuilderXYProfile,
    private val chartDataBuilderXOY: ChartDataBuilderXOY
) {


    fun createChartData(mcAndC: McAndConstruction, groupNum: Int): ChartDataBuilder {

        val limit = ConstructionValues.defineShiftLimits(
            mcAndC.construction.constructionType,
            mcAndC.construction.height
        )

        return if (groupNum != GROUP_ALL) {
            val groupUuid = mcAndC.mcFull.groups!!.first { it.groupNum == groupNum }.uuid
            chartDataBuilderXYProfile.apply {
                limitByHeight = limit
                xyValues = prepareXYProfileDataValues(
                    mcAndC.mcFull.results!!.filter { it.measurementGroupUuid == groupUuid }
                        .toList(),
                    mcAndC.mcFull.levelsInfo!!
                )
                xyValuesBorder = prepareXYProfileValuesBorder(
                    mcAndC.construction.height,
                    limit
                )
                xValuesMax = defineXYValueMaxForAxis(xyValues, limit, X)
                yValuesMax = defineYProfileValueMaxForYAxis(mcAndC.construction.height)
                descriptionText = if (groupNum == 1) "XOZ" else "YOZ"

                init()
                createLineDataSetBorder(xyValuesBorder)
            }
        } else {
            chartDataBuilderXOY.apply {
                val gr1Uuid = mcAndC.mcFull.groups!!.first { it.groupNum == GROUP_ONE }.uuid
                limitByHeight = limit
                xyValues = prepareXOYDataVales(
                    mcAndC.mcFull.results!!.filter { it.measurementGroupUuid == gr1Uuid }
                        .toList(),
                    mcAndC.mcFull.results!!.filter { it.measurementGroupUuid != gr1Uuid }
                        .toList()
                )
                xyValuesBorder = prepareXOYValesBorder(limit)
                xValuesMax = defineXYValueMaxForAxis(xyValues, limit, X)
                yValuesMax = defineXYValueMaxForAxis(xyValues, limit, Y)
                descriptionText = "XOY"

                init()
                createScatterDataSetBorder(xyValuesBorder)
            }
        }
    }

    private fun prepareXYProfileDataValues(
        resultList: List<Result>,
        levels: List<McLevelInfo>
    ): List<Pair<Float, Float>> {
        Log.d("chart", "Prepare XY Values. result list size: ${resultList.size}, levels size: ${levels.size}")
        val xyDataValues = mutableListOf<Pair<Float, Float>>()

        if (levels.size != resultList.size) throw RuntimeException("Levels size don't match results size.")
        for (i in levels.indices) {
            xyDataValues.add(
                Pair(resultList[i].shiftMm.toFloat(), levels[i].altitude.toFloat())
            )
        }
        return xyDataValues
    }

    private fun prepareXOYDataVales(
        resultListGr1: List<Result>,
        resultListGr2: List<Result>
    ): List<Pair<Float, Float>> {
        if (resultListGr1.size != resultListGr2.size) throw RuntimeException("Results size of gr 1 is not equal to results size gr 2.")
        val xyDataValues = mutableListOf<Pair<Float, Float>>()

        for (i in resultListGr1.indices) {
            xyDataValues.add(
                Pair(resultListGr1[i].shiftMm.toFloat(), resultListGr2[i].shiftMm.toFloat())
            )
        }
        return xyDataValues
    }

    private fun prepareXYProfileValuesBorder(
        height: Int,
        limitByHeight: Int
    ): List<Pair<Float, Float>> {
        return listOf(
            Pair(limitByHeight.unaryMinus().toFloat(), height.toFloat()),
            Pair(0f, 0f),
            Pair(limitByHeight.toFloat(), height.toFloat()),
        )
    }

    private fun prepareXOYValesBorder(
        radius: Int
    ): List<Pair<Float, Float>> {
        val list = mutableListOf<Pair<Float, Float>>()
        val x0 = -1 * radius
        val x0Offset = 0
        val step = 2

        for (x in x0 + x0Offset..radius step step) {
            val y1 = sqrt(radius.toDouble().pow(2.0) - x.toDouble().pow(2.0))
            val y2 = -1 * sqrt(radius.toDouble().pow(2.0) - x.toDouble().pow(2.0))
            list.add(Pair(x.toFloat(), y1.toFloat()))
            list.add(Pair(x.toFloat(), y2.toFloat()))
        }
        return list
    }

    private fun defineXYValueMaxForAxis(
        xyValues: List<Pair<Float, Float>>,
        limitByHeight: Int,
        axis: Int
    ): Float {
        val maxValuesData = when(axis) {
            X -> abs(
                xyValues.maxBy { abs(it.first) }.first
            )
            Y -> abs(
                xyValues.maxBy { abs(it.second) }.second
            )
            else -> throw RuntimeException("Wrong axis definition")
        }
        return  if (maxValuesData > limitByHeight) maxValuesData else limitByHeight.toFloat()
    }

    private fun defineYProfileValueMaxForYAxis(
        height: Int
    ): Float {
        return height.toFloat()
    }

    companion object {
        private const val X = 0
        private const val Y = 1
    }
}