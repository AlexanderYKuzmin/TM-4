package com.kuzmin.feature.site_filter.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kuzmin.feature.site_filter.databinding.FragmentSiteFilterBinding
import com.kuzmin.feature.site_filter.domain.model.SearchFilterDataResult
import com.kuzmin.feature.site_filter.ui.viewmodels.SearchFilterViewModel
import com.kuzmin.tm_4.common.extension.formatToDateString
import com.kuzmin.tm_4.common.extension.toDate
import com.kuzmin.tm_4.common.util.CommonConstants.START_DATE_MILLIS_DEFAULT
import com.kuzmin.tm_4.feature.api.domain.model.search_filter.SearchFilterData
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date

@AndroidEntryPoint
class SiteFilterFragment : Fragment(), OnClickListener {

    private var onFilterSearchSubmitListener: OnFilterSearchSubmitListener? = null

    private lateinit var _binding: FragmentSiteFilterBinding
    private val binding: FragmentSiteFilterBinding get() = _binding

    private val searchFilterViewModel: SearchFilterViewModel by viewModels()

    private val navController by lazy { findNavController() }

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
                is SearchFilterDataResult.Succes -> {
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
            when(v) {
                btnSearchFilter -> {
                    searchFilterViewModel.saveSearchData(
                        SearchFilterData(
                            dateStart = etStartDate.text.toString().toDate() ?: Date(START_DATE_MILLIS_DEFAULT),
                            dateEnd = etEndDate.text.toString().toDate() ?: Date(),
                            region = etRegionFilter.toString(),
                            city = etCityFilter.toString(),
                            siteName = etNameFilter.toString()
                        )
                    )
                    close(true)
                }
                btnCancelFilter -> {
                    close(false)
                }
            }
        }
    }

    private fun close(isFilterSet: Boolean) {
        onFilterSearchSubmitListener?.onFilterSearchSubmit(isFilterSet)
        navController.popBackStack()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFilterSearchSubmitListener) {
            onFilterSearchSubmitListener = context
        } else {
            throw RuntimeException("Activity must implement OnFilterSearchSubmitListener")
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SiteFilterFragment()
    }

    interface OnFilterSearchSubmitListener {
        fun onFilterSearchSubmit(isFilterSet: Boolean)
    }
}