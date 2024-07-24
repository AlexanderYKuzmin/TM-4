package com.kuzmin.tm_4.feature.report.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TableRow.LayoutParams
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kuzmin.tm_4.common.R
import com.kuzmin.tm_4.common.extension.toast
import com.kuzmin.tm_4.common.util.CommonConstants.GROUP_NUM
import com.kuzmin.tm_4.common.util.CommonConstants.MC_UUID
import com.kuzmin.tm_4.feature.api.domain.model.sealed.McAndCResult
import com.kuzmin.tm_4.feature.report.R.*
import com.kuzmin.tm_4.feature.report.databinding.FragmentItemTableBinding
import com.kuzmin.tm_4.feature.report.domain.model.sealed.ReportTable
import com.kuzmin.tm_4.feature.report.ui.viewmodels.ItemTablesViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@AndroidEntryPoint
class ItemTableFragment : Fragment(){
    private var mcUuid: String? = null
    private var groupNum: Int = -1

    @Inject
    @ApplicationContext
    lateinit var appContext: Context

    private var _binding: FragmentItemTableBinding? = null
    private val binding: FragmentItemTableBinding get() = _binding!!

    private val itemTableViewModel: ItemTablesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            mcUuid = it.getString(MC_UUID)
            groupNum = it.getInt(GROUP_NUM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemTableBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (mcUuid != null) {
            Log.d("Tables", "ItemTable mcUuid != null")
            itemTableViewModel.getMcAndConstruction(mcUuid!!)
        } else {
            appContext.toast(appContext.getString(R.string.no_measurements))
        }
        itemTableViewModel.mcResult.observe(viewLifecycleOwner) {
            Log.d("Tables", "mcResult was changed McAmdCResult: ${it.javaClass}")
            when(it) {
                is McAndCResult.SuccessMcAndC -> {
                    val tableData = itemTableViewModel.createTableData(it.mcAndC, groupNum)
                    tableData.forEach { Log.d("Tables", "TableData: $it") }
                    when(tableData.firstOrNull()) {
                        is ReportTable.ProfileTableData -> {
                            Log.d("Tables", "Table data is ProfileTableData")
                            setProfileTable(tableData)
                        }
                        is ReportTable.XOYTableData -> {
                            Log.d("Tables", "Table data is XOYTableData")
                            setXOYTableData(tableData)
                        }
                        else -> {
                            Log.d("Table data", "Empty table data.")
                            appContext.toast(appContext.getString(R.string.no_table_data))
                        }
                    }
                }
                is McAndCResult.Error -> {

                }
                else -> throw RuntimeException("Wrong Table class")
            }
        }
    }
    private val rowParams: LayoutParams
        get() = LayoutParams().apply {
                this.topMargin = 25
                this.bottomMargin = 25
        }
    private fun setProfileTable(profileTableDataList: List<ReportTable>) {
        binding.tlXoyResults.visibility = View.GONE
        val table = binding.tlResults.apply { visibility = View.VISIBLE }

        for(i in profileTableDataList.indices) {
            val row = TableRow(appContext)

            row.apply {
                with(profileTableDataList[i] as ReportTable.ProfileTableData) {
                    addView(
                        createTextView(i.toString()),
                        rowParams
                    )
                    addView(
                        createTextView(altitude),
                        rowParams
                    )
                    addView(
                        createTextView(left.toString()),
                        rowParams
                    )
                    addView(
                        createTextView(right.toString()),
                        rowParams
                    )
                    addView(
                        createTextView(average.toString()),
                        rowParams
                    )
                    addView(
                        createTextView(shift.toString()).apply {
                            val profileTableData = this@with
                               setTextColor(
                                   if (profileTableData.isExceeded) AppCompatResources.getColorStateList(appContext, R.color.color_danger)
                                   else AppCompatResources.getColorStateList(appContext, R.color.color_well_done)
                               )
                        },
                        rowParams
                    )
                }
                background =
                    if (i % 2 == 0) AppCompatResources.getDrawable(appContext, R.color.color_gray_light)
                    else AppCompatResources.getDrawable(appContext, R.color.white)
            }
            table.addView(row, TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT))
        }
    }

    private fun setXOYTableData(xoyTableDataList: List<ReportTable>) {
        binding.tlResults.visibility = View.GONE
        val table = binding.tlXoyResults.apply { visibility = View.VISIBLE }

        for(i in xoyTableDataList.indices) {
            val row = TableRow(appContext)

            row.apply {
                with(xoyTableDataList[i] as ReportTable.XOYTableData) {
                    addView(
                        createTextView(i.toString()),
                        rowParams
                    )
                    addView(
                        createTextView(altitude),
                        rowParams
                    )
                    addView(
                        createTextView(shiftX.toString()),
                        rowParams
                    )
                    addView(
                        createTextView(shiftY.toString()),
                        rowParams
                    )
                    addView(
                        createTextView(shiftResult.toString()).apply {
                            val xoyTableData = this@with
                            setTextColor(
                                if (xoyTableData.isExceeded) AppCompatResources.getColorStateList(appContext, R.color.color_danger)
                                else AppCompatResources.getColorStateList(appContext, R.color.color_well_done)
                            )
                        },
                        rowParams
                    )
                }
                background =
                    if (i % 2 == 0) AppCompatResources.getDrawable(appContext, R.color.color_gray_light)
                    else AppCompatResources.getDrawable(appContext, R.color.white)
            }
            table.addView(row, TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun createTextView(text: String): TextView {
        return TextView(appContext).apply {
            this.text = text
            textSize = 16f
            textScaleX = 0.8f
            gravity = Gravity.CENTER
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(mcUuid: String, groupNum: Int) =
            ItemTableFragment().apply {
                arguments = Bundle().apply {
                    putString(MC_UUID, mcUuid)
                    putInt(GROUP_NUM, groupNum)
                }
            }
    }
}