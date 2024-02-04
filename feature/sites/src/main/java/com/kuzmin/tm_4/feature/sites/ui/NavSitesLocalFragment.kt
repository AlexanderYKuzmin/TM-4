package com.kuzmin.tm_4.feature.sites.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kuzmin.tm_4.feature.sites.R
import com.kuzmin.tm_4.feature.sites.databinding.FragmentNavSitesBinding
import com.kuzmin.tm_4.feature.sites.domain.model.sealed.SiteResult.Error
import com.kuzmin.tm_4.feature.sites.domain.model.sealed.SiteResult.Loading
import com.kuzmin.tm_4.feature.sites.domain.model.sealed.SiteResult.Success
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@AndroidEntryPoint
class NavSitesLocalFragment : Fragment() {

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

    private val sitesViewModel: SitesViewModel by viewModels()

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

        setOnAdapterItemClickActions(adapter)

        sitesViewModel.observeQuery(viewLifecycleOwner)
        sitesViewModel.siteResult.observe(viewLifecycleOwner) {
            when(it) {
                is Success -> adapter.submitList(it.sites)
                is Error -> {
                    Toast.makeText(
                        appContext,
                    getString(R.string.error_site_loading) + " " + it.throwable.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                Log.d("SitesFragment", "error: ${it.throwable}")}
                is Loading -> showProgress()
            }
        }
    }

    private fun showProgress() {
        Log.d("MainActivity", "Progress ON")
    }

    fun setOnAdapterItemClickActions(adapter: SitesAdapter) {
        adapter.onItemClickListener = {
            //findNavController().navigate(R.id.nav_construction, bundleOf(BUILDING_ID to it))
            Log.d("MainActivity", "On item click! ID: $it")
        }

        /*adapter.onItemLongClickListener = {

        }*/
    }
}