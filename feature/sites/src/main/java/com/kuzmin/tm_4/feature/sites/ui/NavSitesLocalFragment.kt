package com.kuzmin.tm_4.feature.sites.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.kuzmin.tm_4.feature.sites.databinding.FragmentNavSitesBinding
import com.kuzmin.tm_4.feature.sites.domain.model.samples.SiteSample
import javax.inject.Inject


class NavSitesLocalFragment : Fragment() {

    private var _binding: FragmentNavSitesBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var navController: NavController

    val buildingList: List<SiteSample> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*buildingList = if (VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelableArrayList(BUILDING_LIST, ConstructionSimple::class.java) ?:
            listOf()
        } else {
            arguments?.getParcelableArrayList(BUILDING_LIST) ?: listOf()
        }*/
    }

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

        val adapter = SitesAdapter()
        binding.rvNavSitesLocal.adapter = adapter

        setOnItemBuildingClickListener(adapter)

        adapter.submitList(buildingList)
    }

    fun setOnItemBuildingClickListener(adapter: SitesAdapter) {
        /*adapter.onItemSiteClickListener = {
            //findNavController().navigate(R.id.nav_construction, bundleOf(BUILDING_ID to it))
        }*/
    }

    companion object {
        const val BUILDING_LIST = "buildings"
    }
}