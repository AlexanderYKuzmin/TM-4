package com.kuzmin.feature.site_filter.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.kuzmin.feature.site_filter.databinding.FragmentSiteFilterBinding
import com.kuzmin.feature.site_filter.domain.model.SearchFilterDataResult
import com.kuzmin.feature.site_filter.ui.viewmodels.SearchFilterViewModel
import com.kuzmin.tm_4.common.R.string.storage_type
import com.kuzmin.tm_4.common.extension.formatToDateString
import com.kuzmin.tm_4.common.extension.toDate
import com.kuzmin.tm_4.common.util.CommonConstants
import com.kuzmin.tm_4.common.util.CommonConstants.FRAGMENT_ON_FINISH
import com.kuzmin.tm_4.common.util.CommonConstants.IS_FILTER_SET
import com.kuzmin.tm_4.common.util.CommonConstants.START_DATE_MILLIS_DEFAULT
import com.kuzmin.tm_4.common.util.CommonConstants.STORAGE
import com.kuzmin.tm_4.feature.api.api.activity.OnFragmentActionListener
import com.kuzmin.tm_4.feature.api.domain.model.FragmentAction
import com.kuzmin.tm_4.feature.api.domain.model.search_filter.SearchFilterData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Date

@AndroidEntryPoint
class SiteFilterFragment : Fragment(), OnClickListener {

    private var storage: Int = -1

    private var onFragmentActionListener: OnFragmentActionListener? = null

    private lateinit var _binding: FragmentSiteFilterBinding
    private val binding: FragmentSiteFilterBinding get() = _binding

    private val searchFilterViewModel: SearchFilterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storage = arguments?.getInt(getString(storage_type)) ?: throw RuntimeException("Storage type can not be null")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSiteFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSearchFilter.setOnClickListener(this)
        binding.btnCancelFilter.setOnClickListener(this)

        searchFilterViewModel.searchFilterDataResult.observe(viewLifecycleOwner) {
            when(it) {
                is SearchFilterDataResult.Success -> {

                    Log.d("Search_Filter", "SearchFilter Data: ${it.searchFilterData}")
                    Log.d("Search_Filter", "SearchFilter startDate string: ${it.searchFilterData.dateStart.formatToDateString()}")
                    Log.d("Search_Filter", "SearchFilter endDate string: ${it.searchFilterData.dateEnd.formatToDateString()}")

                    with(binding) {
                        with(it.searchFilterData) {
                            etStartDate.setText(dateStart.formatToDateString())
                            etEndDate.setText(dateEnd.formatToDateString())
                            etRegionFilter.setText(region)
                            etCityFilter.setText(city)
                            etNameFilter.setText(siteName)
                        }
                    }
                }
                is SearchFilterDataResult.Error -> {

                    // TODO
                }
            }
        }
    }

    override fun onClick(v: View) {
        with(binding) {
            lifecycleScope.launch {
                when(v) {
                    btnSearchFilter -> {
                        searchFilterViewModel.saveSearchData(
                            SearchFilterData(
                                dateStart = etStartDate.text.toString().toDate() ?: Date(START_DATE_MILLIS_DEFAULT),
                                dateEnd = etEndDate.text.toString().toDate() ?: Date(),
                                region = etRegionFilter.text.toString(),
                                city = etCityFilter.text.toString(),
                                siteName = etNameFilter.text.toString()
                            )
                        )
                        close(true)
                    }
                    btnCancelFilter -> {
                        close(false)
                    }

                    else -> {throw RuntimeException("Wrong Click view!")}
                }
            }
        }
    }

    private fun close(isFilterSet: Boolean) {
        //onFilterSubmitListener?.onFilterSearchSubmit(isFilterSet, storage)
        onFragmentActionListener?.onFragmentAction(
            FragmentAction.FilterAction(
                action = FRAGMENT_ON_FINISH,
                data = Bundle().apply {
                    putBoolean(IS_FILTER_SET, isFilterSet)
                    putInt(STORAGE, storage)
                }
            )
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentActionListener) {
            onFragmentActionListener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentActionListener")
        }
    }
}