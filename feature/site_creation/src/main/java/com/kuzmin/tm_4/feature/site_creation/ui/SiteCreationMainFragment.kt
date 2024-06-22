package com.kuzmin.tm_4.feature.site_creation.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.kuzmin.tm_4.feature.site_creation.R
import com.kuzmin.tm_4.feature.site_creation.databinding.FragmentSiteCreationMainBinding
import com.kuzmin.tm_4.feature.site_creation.ui.viewmodels.CreationViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@AndroidEntryPoint
class SiteCreationMainFragment : Fragment() {

    private var siteUuid: String? = null

    private var _binding: FragmentSiteCreationMainBinding? = null
    private val binding: FragmentSiteCreationMainBinding get() = _binding!!

    private val creationViewModel: CreationViewModel by viewModels()

    @Inject
    @ApplicationContext
    lateinit var appContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            siteUuid = it.getString(appContext.getString(com.kuzmin.tm_4.common.R.string.site_uuid))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSiteCreationMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}