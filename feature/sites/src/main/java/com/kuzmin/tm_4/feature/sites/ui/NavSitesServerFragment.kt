package com.kuzmin.tm_4.feature.sites.ui

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kuzmin.tm_4.common.R.id.site_nav_graph
import com.kuzmin.tm_4.common.util.CommonConstants.STORAGE_SERVER
import com.kuzmin.tm_4.feature.api.model.SiteDataStore
import com.kuzmin.tm_4.feature.sites.R
import com.kuzmin.tm_4.feature.sites.databinding.FragmentNavSitesBinding
import com.kuzmin.tm_4.feature.sites.domain.model.sealed.SiteResult.Error
import com.kuzmin.tm_4.feature.sites.domain.model.sealed.SiteResult.Loading
import com.kuzmin.tm_4.feature.sites.domain.model.sealed.SiteResult.Success
import com.kuzmin.tm_4.feature.sites.ui.adapters.SitesAdapter
import com.kuzmin.tm_4.feature.sites.ui.adapters.SwipeToSaveHelper
import com.kuzmin.tm_4.feature.sites.ui.helpers.ImageSaveButtonClickListener
import com.kuzmin.tm_4.feature.sites.ui.viewmodels.SitesNavGraphViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@AndroidEntryPoint
class NavSitesServerFragment : SitesFragment() {

    //private var onSitesAdapterClickListener: OnSitesAdapterClickListener? = null

    private var _binding: FragmentNavSitesBinding? = null
    private val binding get() = _binding!!

    /*@Inject
    lateinit var navController: NavController*/

    @Inject
    @ApplicationContext
    lateinit var appContext: Context

    private val navController by lazy {
        findNavController()
    }

    private val sitesNavGraphViewModel: SitesNavGraphViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNavSitesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = SitesAdapter(appContext)
        binding.rvNavSitesLocal.adapter = adapter

        setSwipeToSaveHelper(binding.rvNavSitesLocal)

        setAdapterItemClickAction(adapter)

        sitesNavGraphViewModel.observeQuery(viewLifecycleOwner)
        sitesNavGraphViewModel.siteResult.observe(viewLifecycleOwner) {
            when(it) {
                is Success -> {
                    with(it.sites.first()) {
                        sitesNavGraphViewModel.storeSiteData(
                            SiteDataStore(uuid, name, constructionsSample!!.first().uuid)
                        )
                    }
                    adapter.submitList(it.sites)
                }
                is Error -> {
                    Toast.makeText(
                        appContext,
                    getString(R.string.error_site_loading) + " " + it.throwable.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                Log.d("SitesFragment", "error: ${it.throwable}")}
                is Loading -> showProgress()
                else -> throw RuntimeException("Wrong server response.")
            }
        }
    }

    private fun setSwipeToSaveHelper(rvNavSitesLocal: RecyclerView) {
        val swipeHelper = object : SwipeToSaveHelper(
            this.requireActivity(),
            rvNavSitesLocal,
            300
        ) {
            override fun instantiateImageSaveButton(
                viewHolder: RecyclerView.ViewHolder,
                buffer: MutableList<ImageSaveButton>
            ) {
                buffer.add(
                    ImageSaveButton(
                        "Сохранить",
                        R.drawable.save_to_db,
                        30f,
                        Color.DKGRAY,
                        object : ImageSaveButtonClickListener {
                            override fun onClick(pos: Int) {
                                Log.d("SwipeText", "Save button clicked!!")
                            }
                        },
                        this.context
                    )
                )
            }
        }
    }

    private fun showProgress() {
        Log.d("MainActivity", "Progress ON")
    }

    private fun setAdapterItemClickAction(adapter: SitesAdapter) {
        adapter.onItemClickListener = { siteUuid, name, constrUuid ->
            Log.d("MainActivity", "On item click! ID: $siteUuid, $name")
            sitesNavGraphViewModel.storeSiteData(
                SiteDataStore(siteUuid, name, constrUuid)
            )
            navController.navigate(site_nav_graph,
                bundleOf(
                    TITLE to name,
                    SITE_UUID to siteUuid,
                    CONSTRUCTION_UUID to constrUuid,
                    STORAGE_TYPE to STORAGE_SERVER)
            )
            onSitesAdapterClickListener?.onItemSiteClick("")
        }
    }



    companion object {
        const val TITLE = "title"
        const val SITE_UUID = "site_uuid"
        const val CONSTRUCTION_UUID = "construction_uuid"
        const val STORAGE_TYPE = "storage"
    }
}