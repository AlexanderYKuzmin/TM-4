package com.kuzmin.tm_4.feature.sites.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.kuzmin.tm_4.common.R.color.delete_btn_color
import com.kuzmin.tm_4.common.R.color.save_btn_color
import com.kuzmin.tm_4.common.util.CommonConstants.STORAGE_REMOTE
import com.kuzmin.tm_4.feature.sites.R
import com.kuzmin.tm_4.feature.sites.ui.adapters.SiteListAdapter
import com.kuzmin.tm_4.feature.sites.ui.custom.ImageBgdClickListener
import com.kuzmin.tm_4.feature.sites.ui.adapters.SwipeHelper
import com.kuzmin.tm_4.feature.sites.ui.viewmodels.SiteListRemoteViewModel
import com.kuzmin.tm_4.feature.sites.ui.viewmodels.SiteListViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@AndroidEntryPoint
class SiteListRemoteFragment : SiteListFragment() {

    @Inject
    @ApplicationContext
    override lateinit var appContext: Context

    private val siteListRemoteViewModel: SiteListRemoteViewModel by viewModels()
    override val viewModel: SiteListViewModel
        get() = siteListRemoteViewModel

    override lateinit var siteListAdapter: SiteListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        siteListAdapter = SiteListAdapter(appContext, STORAGE_REMOTE).also {
            setAdapterItemClickAction(it)
        }
        val rv = binding.rvNavSitesLocal.apply {
            adapter = siteListAdapter
        }

        registerSwipeHelper(rv)

        siteListRemoteViewModel.loadSiteList()

        siteListRemoteViewModel.observeSiteResult(viewLifecycleOwner, ::renderUi)
    }


    override fun createBgdButtonList(swipeHelper: SwipeHelper): List<SwipeHelper.ImageBgdButton> {
        with(swipeHelper) {
            return listOf(
                ImageBgdButton(
                    text = getString(R.string.btn_bgd_save),
                    imageResId = R.drawable.save_to_db,
                    textSize = 30f,
                    color = ContextCompat.getColor(appContext, save_btn_color),
                    listener =
                        object : ImageBgdClickListener {
                            override fun onClick(pos: Int) {
                                Log.d("Swipe", "Button SAVE pressed")
                            }
                        },
                    context = appContext
                ),
                ImageBgdButton(
                    text = getString(R.string.btn_bgd_delete),
                    imageResId = R.drawable.delete,
                    textSize = 30f,
                    color = ContextCompat.getColor(appContext, delete_btn_color),
                    listener =
                    object : ImageBgdClickListener {
                        override fun onClick(pos: Int) {
                            Log.d("Swipe", "Button DELETE pressed")
                        }
                    },
                    context = appContext
                )
            )
        }

    }
}