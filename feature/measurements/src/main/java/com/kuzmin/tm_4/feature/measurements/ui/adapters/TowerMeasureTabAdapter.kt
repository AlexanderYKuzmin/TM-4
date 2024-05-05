package com.kuzmin.tm_4.feature.measurements.ui.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kuzmin.tm_4.feature.api.model.site.MeasurementConstruction
import com.kuzmin.tm_4.feature.measurements.ui.TowerMeasureActionFragment
import com.kuzmin.tm_4.feature.measurements.ui.TowerMeasurePagerFragment

class TowerMeasureTabAdapter(
    fragment: TowerMeasurePagerFragment
) : FragmentStateAdapter(fragment){

    var mc: MeasurementConstruction? = null

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        if (mc == null) throw RuntimeException("Measurement construction is null")
        return when(position) {
            0 -> TowerMeasureActionFragment.newInstance(
                1,
                mc!!.uuid,
                mc!!.constructionUuid
            )
            1 -> TowerMeasureActionFragment.newInstance(
                2,
                mc!!.uuid,
                mc!!.constructionUuid
            )
            else -> throw RuntimeException("Wrong position count in tm_tab_adapter.")
        }
    }
}