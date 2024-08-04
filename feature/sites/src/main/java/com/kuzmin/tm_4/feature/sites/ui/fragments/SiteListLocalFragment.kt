package com.kuzmin.tm_4.feature.sites.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.kuzmin.tm_4.common.util.CommonConstants
import com.kuzmin.tm_4.feature.sites.R
import com.kuzmin.tm_4.feature.sites.ui.adapters.SiteListAdapter
import com.kuzmin.tm_4.feature.sites.ui.adapters.SwipeHelper
import com.kuzmin.tm_4.feature.sites.ui.custom.ImageBgdClickListener
import com.kuzmin.tm_4.feature.sites.ui.viewmodels.SiteListLocalViewModel
import com.kuzmin.tm_4.feature.sites.ui.viewmodels.SiteListViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@AndroidEntryPoint
class SiteListLocalFragment : SiteListFragment() {

    @Inject
    @ApplicationContext
    override lateinit var appContext: Context

    private val siteListLocalViewModel: SiteListLocalViewModel by viewModels()
    override val viewModel: SiteListViewModel
        get() = siteListLocalViewModel

    override lateinit var siteListAdapter: SiteListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        siteListAdapter = SiteListAdapter(appContext, CommonConstants.STORAGE_LOCAL).also {
            setAdapterItemClickAction(it)
        }
        val rv = binding.rvNavSitesLocal.apply {
            this.adapter = siteListAdapter
        }

        registerSwipeHelper(rv)

        siteListLocalViewModel.loadSiteList()

        siteListLocalViewModel.observeSiteResult(viewLifecycleOwner, ::renderUi)
    }

    override fun createBgdButtonList(swipeHelper: SwipeHelper): List<SwipeHelper.ImageBgdButton> {
        with(swipeHelper) {
            return listOf(
                ImageBgdButton(
                    text = getString(R.string.btn_bgd_save),
                    imageResId = R.drawable.save_to_db,
                    textSize = 30f,
                    color = ContextCompat.getColor(appContext,
                        com.kuzmin.tm_4.common.R.color.save_btn_color
                    ),
                    listener =
                    object : ImageBgdClickListener {
                        override fun onClick(pos: Int) {
                            Log.d("Swipe", "Button LOCAL SAVE pressed")
                        }
                    },
                    context = appContext
                ),
                ImageBgdButton(
                    text = getString(R.string.btn_bgd_delete),
                    imageResId = R.drawable.delete,
                    textSize = 30f,
                    color = ContextCompat.getColor(appContext,
                        com.kuzmin.tm_4.common.R.color.delete_btn_color
                    ),
                    listener =
                    object : ImageBgdClickListener {
                        override fun onClick(pos: Int) {
                            Log.d("Swipe", "Button LOCAL DELETE pressed")
                        }
                    },
                    context = appContext
                )
            )
        }
    }
}