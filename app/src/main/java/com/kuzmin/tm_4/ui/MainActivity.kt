package com.kuzmin.tm_4.ui

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getDrawable
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.navOptions
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kuzmin.feature.site_filter.ui.fragments.SiteFilterFragment
import com.kuzmin.tm_4.R
import com.kuzmin.tm_4.common.R.*
import com.kuzmin.tm_4.common.R.id.*
import com.kuzmin.tm_4.common.R.string.*
import com.kuzmin.tm_4.common.extension.dpToIntPx
import com.kuzmin.tm_4.common.extension.toast
import com.kuzmin.tm_4.common.model.FragmentStatus
import com.kuzmin.tm_4.common.util.CommonConstants.AUTHORIZATION_STARTED
import com.kuzmin.tm_4.common.util.CommonConstants.FRAGMENT_IN_PROGRESS
import com.kuzmin.tm_4.common.util.CommonConstants.FRAGMENT_ON_FINISH
import com.kuzmin.tm_4.common.util.CommonConstants.FRAGMENT_ON_START
import com.kuzmin.tm_4.common.util.CommonConstants.PASS_ON_FILTER
import com.kuzmin.tm_4.common.util.CommonConstants.PASS_ON_SINGLE_SITE
import com.kuzmin.tm_4.common.util.CommonConstants.STORAGE_LOCAL
import com.kuzmin.tm_4.common.util.CommonConstants.STORAGE_REMOTE
import com.kuzmin.tm_4.databinding.ActivityMainBinding
import com.kuzmin.tm_4.feature.api.api.FeatureIsActiveListener
import com.kuzmin.tm_4.feature.api.api.HomeButtonRemovable
import com.kuzmin.tm_4.feature.login.ui.LoginFragment
import com.kuzmin.tm_4.feature.sites.ui.fragments.SiteListFragment
import com.kuzmin.tm_4.domain.model.ScreenMode.*
import com.kuzmin.tm_4.domain.model.ToolbarState
import com.kuzmin.tm_4.domain.model.sealed.AppState
import com.kuzmin.tm_4.domain.model.sealed.AppState.*
import com.kuzmin.tm_4.feature.api.api.activity.OnFragmentActionListener
import com.kuzmin.tm_4.feature.api.domain.model.FragmentAction
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity :
    AppCompatActivity(),
    HomeButtonRemovable,
    OnFragmentActionListener,
    SiteListFragment.OnItemClickListener {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainActivityViewModel by viewModels()

    private val navController: NavController by lazy {
        findNavController(R.id.nav_host_fragment_activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkStatusBar()

        setupToolbar()

        setupNavigation()

        setOnBackPressedCallback()

        viewModel.observeAppState(this@MainActivity, ::renderUi)

        viewModel.initAuthorization()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        with(viewModel) {
            when (item.itemId) {
                R.id.mm_authorization -> { initAuthorization() }

                R.id.mm_new -> { initNew() }

                R.id.mm_load_local -> { initFilter(STORAGE_LOCAL) }

                R.id.mm_load_server -> { initFilter(STORAGE_REMOTE) }

                R.id.mm_sync -> {}

                R.id.mm_quit -> {}
            }
        }
        return true
    }

    private fun renderUi(appState: AppState) {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        renderUiToolbar(viewModel.toolbarState)
        when (appState) {
            is HomeState -> {
                switchBottombarState(false)
            }

            is LoginState -> {
                switchBottombarState(false)
                if (appState.actionKey == AUTHORIZATION_STARTED) {
                    launchAuthFragment()
                } else navController.popBackStack(home_nav_graph, false)
            }

            is FilterState -> {
                switchBottombarState(false)
                when (appState.actionKey) {
                    FRAGMENT_ON_START -> {
                        launchSiteFilterFragment(appState.storage)
                    }
                    FRAGMENT_ON_FINISH -> {
                        viewModel.initSiteList(appState.storage)
                    }
                }
            }

            is SiteListSate -> {
                switchBottombarState(true)

                when (appState.actionKey) {
                    FRAGMENT_ON_START -> {
                        when (appState.storage) {
                            STORAGE_REMOTE -> launchSiteListRemoteFragment()
                            STORAGE_LOCAL -> launchSiteListLocalFragment()
                        }
                    }

                    PASS_ON_FILTER -> {
                        viewModel.initFilter(appState.storage)
                    }

                    PASS_ON_SINGLE_SITE -> {
                        TODO()
                    }
                }
            }

            is SingleSiteState -> {
                switchBottombarState(true)
            }

            is SiteCreationState -> {
                switchBottombarState(false)
                launchSiteCreationFragment(appState.sUuid)
            }

            else -> {

            }
        }
    }

    private fun renderUiToolbar(toolbarState: ToolbarState) {

        if(toolbarState.isLogoVisible) {
            binding.toolbar.getChildAt(1).visibility = View.VISIBLE
        } else {
            binding.toolbar.getChildAt(1).visibility = View.GONE
        }

        val imageView = binding.toolbar.getChildAt(3) as ImageView
        imageView.setImageDrawable(
            if (toolbarState.isLoginCompleted) {
                getDrawable(this, R.drawable.light_bulb_on)
            } else getDrawable(this, R.drawable.light_bulb_off)
        )

        val title = binding.toolbar.getChildAt(0) as TextView
        if (toolbarState.appTitle.isNullOrEmpty()) {
            title.setText(getString(R.string.app_name))
        } else {
            title.setText(toolbarState.appTitle)
        }
    }

    @SuppressLint("RestrictedApi")
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setHomeButtonEnabled(false)

        val logo =
            if (binding.toolbar.childCount > 1) binding.toolbar.getChildAt(1) as ImageView else null
        logo?.scaleType = ImageView.ScaleType.CENTER_CROP

        val lp = logo?.layoutParams as? Toolbar.LayoutParams
        lp?.let {
            it.width = dpToIntPx(40)
            it.height = dpToIntPx(40)
            it.marginEnd = dpToIntPx(16)
            logo.layoutParams = it
        }

        val title = binding.toolbar.getChildAt(0) as TextView
        val titleTypeFace: Typeface =
            Typeface.createFromAsset(assets, "fonts/gost_clan_gradient.ttf")

        with(title) {
            typeface = titleTypeFace
            textSize = 24f
            setTextColor(ContextCompat.getColor(this@MainActivity, color.color_title))
        }

        binding.toolbar.setNavigationOnClickListener {
            toast("Home pressed")
            doBackPressedAction()
            /*if (*//* condition *//*) {
                // Handle the home button click event
            } else {
                // Call the default behavior
                super.onOptionsItemSelected(MenuItemCompat.getActionView(it))
            }*/
        }
    }

    private fun setupNavigation() {
        val navView = binding.navView
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                sites_nav_graph,
                measurements_nav_graph,
                report_nav_graph
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val startFragmentBundle = bundleOf()
        navController.setGraph(navController.graph, startFragmentBundle)

        setBottomNavListeners(navView)
    }

    private fun setBottomNavListeners(navView: BottomNavigationView) {
        navView.setOnItemSelectedListener {
            when (it.itemId) {
                sites_nav_graph -> {
                    navController.popBackStack(sites_nav_graph, false)
                }

                measurements_nav_graph -> {
                    navController.popBackStack(measurements_nav_graph, true)
                    navController.navigate(measurements_nav_graph)
                }

                report_nav_graph -> {
                    navController.navigate(report_nav_graph)
                }

                else -> throw RuntimeException("Wrong MenuItem's Id!")
            }
            true
        }
    }

    private fun setOnBackPressedCallback() {
        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    doBackPressedAction()
                }
            }
        )
    }

    private fun launchHomeFragment() {
        navController.navigate(home_nav_graph)
    }

    private fun launchAuthFragment() {
        navController.navigate(login_nav_graph)
    }

    private fun launchSiteFilterFragment(storage: Int) {
        navController.navigate(
            site_filter_nav_graph,
            bundleOf(
                getString(storage_type) to storage
            )
        )
    }

    private fun launchSiteListRemoteFragment() {
        //navController.navigate(R.id.sites_nav_graph, bundleOf(TOKEN to token))
        navController.navigate(
            sites_nav_graph,
            bundleOf(
                getString(filter) to false
            )
        )
    }

    private fun launchSiteListLocalFragment() {
        navController.navigate(
            sites_local_nav_graph,
            bundleOf(
                getString(filter) to false
            )
        )
    }

    private fun launchSiteCreationFragment(siteUuid: String?) {
        navController.navigate(
            site_creation_nav_graph,
            bundleOf(getString(site_uuid) to siteUuid),
            navOptions {
                launchSingleTop = true
            }
        )
    }

    private fun switchBottombarState(isActive: Boolean) {
        if (isActive) {
            binding.navView.visibility = View.VISIBLE
        } else {
            binding.navView.visibility = View.GONE
        }
    }

    override fun onFragmentAction(fragmentAction: FragmentAction) {
        viewModel.handleFragmentAction(fragmentAction)
    }

    override fun onSiteSelected(siteName: String?) {
        viewModel.handleSiteSelected(siteName)
    }

    override fun remove() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun doBackPressedAction() {
        if (navController.currentDestination?.id == home_nav_graph) {
            viewModel.deleteAllTempSites()
            finish()
        } else {
            navController.navigateUp()
        }
    }

    private fun checkStatusBar() {
        window.statusBarColor = ContextCompat.getColor(this, color.color_primary_dark)
    }

    companion object {
        const val TAG = "MainActivity"
    }
}