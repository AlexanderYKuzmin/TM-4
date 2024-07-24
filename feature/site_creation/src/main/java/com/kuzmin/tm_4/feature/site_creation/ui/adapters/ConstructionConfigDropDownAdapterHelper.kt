package com.kuzmin.tm_4.feature.site_creation.ui.adapters

import android.content.Context
import android.widget.ArrayAdapter
import com.kuzmin.tm_4.feature.site_creation.R
import javax.inject.Inject

class ConstructionConfigDropDownAdapterHelper @Inject constructor() {

    fun getConfigures(context: Context) =
        ArrayAdapter(
            context,
            R.layout.item_constr_type_drop_down,
            context.resources.getStringArray(R.array.construction_configs)
        )
}