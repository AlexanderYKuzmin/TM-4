package com.kuzmin.tm_4.feature.report.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.CombinedData
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.ScatterData
import com.kuzmin.tm_4.common.util.CommonConstants.GROUP_ALL
import com.kuzmin.tm_4.common.util.CommonConstants.GROUP_NUM
import com.kuzmin.tm_4.common.util.CommonConstants.MC_UUID
import com.kuzmin.tm_4.feature.api.domain.model.model_complex.McAndConstruction
import com.kuzmin.tm_4.feature.api.domain.model.sealed.McAndCResult.*
import com.kuzmin.tm_4.feature.report.R
import com.kuzmin.tm_4.feature.report.databinding.FragmentSingleGraphBinding
import com.kuzmin.tm_4.feature.report.domain.model.ChartDataBuilder
import com.kuzmin.tm_4.feature.report.ui.viewmodels.ItemGraphViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@AndroidEntryPoint
class ItemGraphFragment : Fragment() {
    private var mcUuid: String? = null
    private var groupNum: Int = -1

    @Inject
    @ApplicationContext
    lateinit var appContext: Context

    private var _binding: FragmentSingleGraphBinding? = null
    val binding: FragmentSingleGraphBinding get() = _binding!!

    private val itemGraphViewModel: ItemGraphViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mcUuid = it.getString(MC_UUID)
            groupNum = it.getInt(GROUP_NUM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSingleGraphBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (mcUuid == null) throw RuntimeException("MC UUID is null")
        if (groupNum == -1) throw RuntimeException("GROUP NUM is -1")
        itemGraphViewModel.getMcAndConstruction(mcUuid!!)
        itemGraphViewModel.mcResult.observe(viewLifecycleOwner) {
            when(it) {
                is Loading -> {

                }
                is SuccessMcAndC -> {
                    Log.d("report", "SHOW Line chart")
                    setChart(it.mcAndC)

                }
                is Error -> {
                    Log.d("Report", "Error has occured when the MC and C was loading: ${it.throwable}")
                }
                else -> throw RuntimeException("Illegal data loaded from DB")
            }
        }
    }

    private fun setChart(mcAndC: McAndConstruction) {
        Log.d("chart", "ItemFragment setDataChart. START")
        if (
            mcAndC.mcFull.groups.isNullOrEmpty() ||
            mcAndC.mcFull.results.isNullOrEmpty() ||
            mcAndC.mcFull.measurements.isNullOrEmpty()
            )
        {
            return
        }

        val chartDataBuilder = itemGraphViewModel.getChartData(mcAndC, groupNum)

        val combinedChart = binding.combinedChart.apply {
            setScaleEnabled(false)
            description.text = chartDataBuilder.descriptionText
            description.yOffset = ChartDataBuilder.Y_OFFSET
            drawOrder = chartDataBuilder.drawOrder
            legend.apply {
                formSize = 10f
            }
            xAxis.apply {
                with(chartDataBuilder) {
                    axisMaximum = xAxis.axisMaximum
                    axisMinimum = -axisMaximum
                    axisLineWidth = 2f
                    labelCount = xAxis.labelCount
                    position = XAxis.XAxisPosition.BOTTOM
                    removeAllLimitLines()
                    limitLines.addAll(chartXAxisLimitLines)
                }
            }
            axisLeft.apply {
                with(chartDataBuilder) {
                    axisMinimum = leftAxis.axisMinimum
                    axisMaximum = leftAxis.axisMaximum
                    labelCount = leftAxis.labelCount
                    valueFormatter = leftAxis.valueFormatter
                }

            }
            axisRight.apply {
                with(chartDataBuilder) {
                    if (rightAxis != null) {
                        isEnabled = true
                        axisMinimum = 0f
                        axisMaximum = rightAxis!!.axisMaximum
                        labelCount = rightAxis!!.labelCount
                        valueFormatter = rightAxis!!.valueFormatter
                        removeAllLimitLines()
                        limitLines.addAll(chartXAxisLimitLines)
                    } else {
                        isEnabled = false
                    }
                }
            }
        }

        setAxisNamesAndConstraint()

        val lineData = LineData(chartDataBuilder.lineDataSetList)
        Log.d("chart","Linedata. ${lineData.dataSets}")
        val scatterData = ScatterData(chartDataBuilder.scatterDataSetList)
        Log.d("chart","Scatterdata. ${scatterData.dataSets}")

        val combinedData = CombinedData().apply {
            setData(lineData)
            setData(scatterData)
        }

        combinedChart.apply {
            data = combinedData
        }.invalidate()


    }

    private fun setAxisNamesAndConstraint() {
        val xAxisName = TextView(appContext).apply {
            text = appContext.getString(R.string.x_axis_name)
            textSize = 10f
            id = View.generateViewId()
        }
        val yAxisName = TextView(appContext).apply {
            text = appContext.getString(R.string.y_axis_name)
            textSize = 10f
            id = View.generateViewId()
        }
        binding.root.apply {
            addView(xAxisName, 0)
            addView(yAxisName, 0)
        }

        ConstraintSet().apply {
            clone(binding.clChart)
            setDimensionRatio(
                binding.combinedChart.id,
                if (groupNum == GROUP_ALL) "3:4"
                else "${binding.clChart.width}:${binding.clChart.height}"
            )
            connect(xAxisName.id, ConstraintSet.END, binding.clChart.id, ConstraintSet.END)
            connect(xAxisName.id, ConstraintSet.BOTTOM, binding.clChart.id, ConstraintSet.BOTTOM)
            connect(yAxisName.id, ConstraintSet.TOP, binding.clChart.id, ConstraintSet.TOP)
            connect(yAxisName.id, ConstraintSet.START, binding.clChart.id, ConstraintSet.START)
            setMargin(xAxisName.id, ConstraintSet.END, 10)
            setMargin(xAxisName.id, ConstraintSet.BOTTOM, 10)
            setMargin(yAxisName.id, ConstraintSet.START, 10)
            setMargin(yAxisName.id, ConstraintSet.TOP, 10)
        }
            .applyTo(binding.root)
    }

    companion object {

        @JvmStatic
        fun newInstance(mcUuid: String, groupNum: Int) =
            ItemGraphFragment().apply {
                arguments = Bundle().apply {
                    putString(MC_UUID, mcUuid)
                    putInt(GROUP_NUM, groupNum)
                }
            }
    }
}