package com.kuzmin.tm_4.feature.report.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kuzmin.tm_4.common.util.CommonConstants
import com.kuzmin.tm_4.common.util.CommonConstants.GROUP_NUM
import com.kuzmin.tm_4.common.util.CommonConstants.MC_UUID
import com.kuzmin.tm_4.feature.report.databinding.FragmentItemTableBinding
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ItemTableFragment : Fragment(){
    private var mcUuid: String? = null
    private var groupNum: Int = -1

    @Inject
    @ApplicationContext
    lateinit var appContext: Context

    private var _binding: FragmentItemTableBinding? = null
    private val binding: FragmentItemTableBinding get() = _binding!!

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