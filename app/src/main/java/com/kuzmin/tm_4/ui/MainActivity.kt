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
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
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
import com.kuzmin.tm_4.common.util.CommonConstants.STORAGE_LOCAL
import com.kuzmin.tm_4.common.util.CommonConstants.STORAGE_REMOTE
import com.kuzmin.tm_4.databinding.ActivityMainBinding
import com.kuzmin.tm_4.feature.api.api.FeatureIsActiveListener
import com.kuzmin.tm_4.feature.login.ui.LoginFragment
import com.kuzmin.tm_4.feature.sites.ui.fragments.SiteListFragment
import com.kuzmin.tm_4.model.ScreenMode.*
import com.kuzmin.tm_4.model.sealed.AppState
import com.kuzmin.tm_4.model.sealed.AppState.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity :
    AppCompatActivity(),
    FeatureIsActiveListener,
    LoginFragment.OnLoginActionListener,
    SiteListFragment.OnItemClickListener,
    SiteFilterFragment.OnFilterSubmitListener {
    private lateinit var _binding: ActivityMainBinding

    private val viewModel: MainActivityViewModel by viewModels()

    private val navController: NavController by lazy {
        findNavController(R.id.nav_host_fragment_activity_main)
    }

    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        window.statusBarColor = ContextCompat.getColor(this, color.color_primary_dark)
        setupToolbar()

        val navView = _binding.navView
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

        with(viewModel) {
            observeAppState(this@MainActivity, ::renderUi)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        with(viewModel) {
            when (item.itemId) {
                R.id.mm_authorization -> {
                    initAuthorization()
                }

                R.id.mm_new -> {
                    initNew()
                }

                R.id.mm_load_local -> {
                    initSiteList(STORAGE_LOCAL)
                }

                R.id.mm_load_server -> {
                    initSiteList(STORAGE_REMOTE)
                }

                R.id.mm_sync -> {

                }

                R.id.mm_quit -> {

                }
            }
        }
        return true
    }

    private fun renderUi(appState: AppState) {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        when (appState) {
            is ToolbarState -> {
                renderUiToolbar(appState)
            }

            is LoginState -> {
                launchAuthFragment()
            }

            is SiteListSate -> {
                with(appState) {
                    if (isFilterSet) {
                        when (storage) {
                            STORAGE_REMOTE -> launchSiteListRemoteFragment(isFilterSet)
                            STORAGE_LOCAL -> launchSiteListLocalFragment(isFilterSet)
                        }
                    } else launchSiteFilterFragment(storage)
                }
            }

            is SingleSiteState -> {

            }

            is SiteCreationState -> {
                launchSiteCreationFragment(appState.sUuid)
            }

            else -> {

            }
        }
    }

    private fun renderUiToolbar(toolbarState: ToolbarState) {
        val imageView = _binding.toolbar.getChildAt(3) as ImageView
        imageView.setImageDrawable(
            if (toolbarState.isLoginCompleted) {
                getDrawable(this, R.drawable.light_bulb_on)
            } else getDrawable(this, R.drawable.light_bulb_off)
        )

        val title = _binding.toolbar.getChildAt(0) as TextView
        if (toolbarState.appTitle.isNullOrEmpty()) {
            title.setText(getString(R.string.app_name))
        } else {
            title.setText(toolbarState.appTitle)
        }
    }

    @SuppressLint("RestrictedApi")
    private fun setupToolbar() {
        setSupportActionBar(_binding.toolbar)
        supportActionBar?.setDefaultDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        val logo =
            if (_binding.toolbar.childCount > 1) _binding.toolbar.getChildAt(1) as ImageView else null
        logo?.scaleType = ImageView.ScaleType.CENTER_CROP

        val lp = logo?.layoutParams as? Toolbar.LayoutParams
        lp?.let {
            it.width = dpToIntPx(40)
            it.height = dpToIntPx(40)
            it.marginEnd = dpToIntPx(16)
            logo.layoutParams = it
        }

        val title = _binding.toolbar.getChildAt(0) as TextView
        val titleTypeFace: Typeface =
            Typeface.createFromAsset(assets, "fonts/gost_clan_gradient.ttf")

        with(title) {
            typeface = titleTypeFace
            textSize = 24f
            setTextColor(ContextCompat.getColor(this@MainActivity, color.color_title))
        }
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

    private fun launchSiteListRemoteFragment(isFilterSet: Boolean) {
        //navController.navigate(R.id.sites_nav_graph, bundleOf(TOKEN to token))
        navController.navigate(
            sites_nav_graph,
            bundleOf(
                getString(filter) to isFilterSet
            )
        )
    }

    private fun launchSiteListLocalFragment(isFilterSet: Boolean) {
        navController.navigate(
            sites_local_nav_graph,
            bundleOf(
                getString(filter) to isFilterSet
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
        // _binding.navView.menu.setGroupEnabled(0, isActive)

        // _binding.navView.menu.setGroupVisible(0, false)
        if (isActive) {
            _binding.navView.visibility = View.VISIBLE
        } else {
            _binding.navView.visibility = View.GONE
        }
    }

    override fun onAuthorizationCompleted(isAuthOk: Boolean) {
        switchBottombarState(true)
        /*viewModel.handleAuthResult(
            if (isOk) AuthState.AUTHORIZED else AuthState.CANCELED
        )*/
        viewModel.handleLogin(isAuthOk)
    }

    @SuppressLint("RestrictedApi")
    override fun onFilterSearchSubmit(isFilterSet: Boolean, storage: Int) {
        navController.popBackStack(home_nav_graph, true)
        navController.currentBackStack.value.forEach {//TODO delete all of this
            Log.d("Filter", "Entry: ${it.id}")
        }
        viewModel.handleFilter(isFilterSet, storage)
    }

    override fun onSiteSelected(siteName: String?) {
        viewModel.handleSiteSelected(siteName)
    }

    override fun onFeatureIsActive(fragmentId: Int) {
        Log.d("Test navview", "Fragment ID: $fragmentId")
        Log.d("Test navview", "R id : ${com.kuzmin.tm_4.common.R.id.site_nav_graph}")
        when(fragmentId) {
            com.kuzmin.tm_4.common.R.id.site_nav_graph -> {
                Log.d("Test navview", "Selected SITE NAV GRAPH: $fragmentId")
                _binding.navView.menu
                    .findItem(com.kuzmin.tm_4.common.R.id.sites_nav_graph)
                    .setChecked(true)
            }
        }
    }

    /*override fun onResume() {
        super.onResume()
        Log.d("remove arrow", "on resume activity")
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }*/

    companion object {
        const val TAG = "MainActivity"
    }
}