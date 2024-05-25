package com.kuzmin.tm_4.feature.report.ui.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kuzmin.tm_4.common.util.CommonConstants
import com.kuzmin.tm_4.feature.report.ui.ItemTableFragment
import com.kuzmin.tm_4.feature.report.ui.ReportTablesPagerFragment

class ReportTablesTabAdapter(
    fragment: ReportTablesPagerFragment,
    val mcUuid: String
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> { ItemTableFragment.newInstance(mcUuid, CommonConstants.GROUP_ONE) }
            1 -> { ItemTableFragment.newInstance(mcUuid, CommonConstants.GROUP_TWO) }
            2 -> { ItemTableFragment.newInstance(mcUuid, CommonConstants.GROUP_ALL) }
            else -> throw RuntimeException("Wrong position number")
        }
    }
}