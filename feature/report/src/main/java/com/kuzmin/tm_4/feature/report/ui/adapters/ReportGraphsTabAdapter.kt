package com.kuzmin.tm_4.feature.report.ui.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kuzmin.tm_4.common.util.CommonConstants.GROUP_ALL
import com.kuzmin.tm_4.common.util.CommonConstants.GROUP_ONE
import com.kuzmin.tm_4.common.util.CommonConstants.GROUP_TWO
import com.kuzmin.tm_4.feature.report.ui.ItemGraphFragment
import com.kuzmin.tm_4.feature.report.ui.ReportGraphsPagerFragment

class ReportGraphsTabAdapter(
    fragment: ReportGraphsPagerFragment,
    private val mcUuid: String
) : FragmentStateAdapter(fragment){

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> { ItemGraphFragment.newInstance(mcUuid, GROUP_ONE) }
            1 -> { ItemGraphFragment.newInstance(mcUuid, GROUP_TWO) }
            2 -> { ItemGraphFragment.newInstance(mcUuid, GROUP_ALL) }
            else -> throw RuntimeException("Wrong position number")
        }
    }
}