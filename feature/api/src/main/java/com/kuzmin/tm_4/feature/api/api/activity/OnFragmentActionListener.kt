package com.kuzmin.tm_4.feature.api.api.activity

import android.os.Bundle
import com.kuzmin.tm_4.feature.api.domain.model.FragmentAction

interface OnFragmentActionListener {
    fun onFragmentAction(fragmentAction: FragmentAction)
}