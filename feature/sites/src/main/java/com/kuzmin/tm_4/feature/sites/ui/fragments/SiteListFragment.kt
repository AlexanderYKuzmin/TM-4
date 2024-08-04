package com.kuzmin.tm_4.feature.sites.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kuzmin.tm_4.common.R
import com.kuzmin.tm_4.common.extension.toast
import com.kuzmin.tm_4.common.util.CommonConstants
import com.kuzmin.tm_4.feature.api.domain.model.SiteTinyData
import com.kuzmin.tm_4.feature.api.domain.model.sample.SiteSample
import com.kuzmin.tm_4.feature.sites.databinding.FragmentNavSitesBinding
import com.kuzmin.tm_4.feature.sites.domain.model.sealed.SiteResult
import com.kuzmin.tm_4.feature.sites.ui.adapters.SiteListAdapter
import com.kuzmin.tm_4.feature.sites.ui.adapters.SwipeHelper
import com.kuzmin.tm_4.feature.sites.ui.viewmodels.SiteListViewModel

abstract class SiteListFragment : Fragment() {

    var onItemClickListener: OnItemClickListener? = null

    private var _binding: FragmentNavSitesBinding? = null
    protected val binding get() = _binding!!

    protected abstract val viewModel: SiteListViewModel

    protected abstract val appContext: Context

    protected val navController by lazy {
        findNavController()
    }

    protected abstract val siteListAdapter: SiteListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNavSitesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnItemClickListener) {
            onItemClickListener = context
        } else {
            throw RuntimeException("Activity must implement OnDeviceItemClickListener")
        }
    }

    protected fun setAdapterItemClickAction(adapter: SiteListAdapter) {
        adapter.onItemClickListener = { siteUuid, name, cUuid ->
            viewModel.storeSiteData(
                SiteTinyData(siteUuid, name, cUuid)
            )
            navController.navigate(
                R.id.site_nav_graph,
                bundleOf(
                    appContext.getString(R.string.title) to name,
                    appContext.getString(R.string.site_uuid) to siteUuid,
                    appContext.getString(R.string.construction_uuid) to cUuid,
                    appContext.getString(R.string.storage_type) to CommonConstants.STORAGE_REMOTE
                )
            )
            onItemClickListener?.onItemSiteClick(name)
        }
    }

    protected fun renderUi(siteResult: SiteResult) {
        when(siteResult) {
            is SiteResult.Success -> {
                Log.d("get All", "Render Fragment Success. ${siteResult.siteList}")
                with(siteResult.siteList.first()) {
                    storeTinyData(this)
                }
                siteListAdapter.submitList(siteResult.siteList)
            }
            is SiteResult.Error -> {
                appContext.toast( getString(R.string.error_site_loading) + " " + siteResult.throwable.toString())
                Log.d("Get all", "Error get from Db all. ${siteResult.throwable.toString()}")
            }
            is SiteResult.Loading -> showProgress()
            else -> throw RuntimeException("Wrong server response.")
        }
    }

    private fun showProgress() {
        TODO()
    }

    protected abstract fun createBgdButtonList(swipeHelper: SwipeHelper): List<SwipeHelper.ImageBgdButton>

    protected fun registerSwipeHelper(rv: RecyclerView) {
        object : SwipeHelper(appContext, rv) {
            override fun instantiateImageSaveButton(
                viewHolder: RecyclerView.ViewHolder,
                buffer: MutableList<ImageBgdButton>
            ) {
                buffer.addAll(
                    createBgdButtonList(this)
                )
            }
        }
    }

    private fun storeTinyData(siteSample: SiteSample) {
        with(siteSample) {
            viewModel.storeSiteData(
                SiteTinyData(
                    sUuid = uuid,
                    sName = name,
                    cUuid = constructionsSample?.first()?.uuid ?: "",
                    mcUuid =  ""
                )
            )
        }
    }

    interface OnItemClickListener {
        fun onItemSiteClick(name: String)
    }
}