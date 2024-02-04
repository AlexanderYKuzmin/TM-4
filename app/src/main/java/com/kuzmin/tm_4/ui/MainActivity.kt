package com.kuzmin.tm_4.ui

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
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.kuzmin.tm_4.R
import com.kuzmin.tm_4.common.R.color
import com.kuzmin.tm_4.common.extension.dpToIntPx
import com.kuzmin.tm_4.databinding.ActivityMainBinding
import com.kuzmin.tm_4.feature.login.ui.LoginFragment
import com.kuzmin.tm_4.model.AppState
import com.kuzmin.tm_4.model.ScreenMode
import com.kuzmin.tm_4.model.ToolbarState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), LoginFragment.LoginListener {
    private lateinit var _binding: ActivityMainBinding

    private val viewModel: MainActivityViewModel by viewModels()

    private val navController: NavController by lazy {
        findNavController(R.id.nav_host_fragment_activity_main)
    }
    /*@Inject
    lateinit var navController: NavController*/

    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        window.statusBarColor = ContextCompat.getColor(this, color.color_primary_dark)
        setupToolbar()
        //setMainMenu()


        val navView = _binding.navView
        //navView.isActivated = false
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.sites_nav_graph,
                R.id.measurements_nav_graph,
                R.id.report_nav_graph
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val startFragmentBundle = bundleOf()
        navController.setGraph(navController.graph, startFragmentBundle)

        with(viewModel) {
            observeAppState(this@MainActivity, ::renderUi)
            observeToolbarState(this@MainActivity, ::renderUiToolbar)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        searchView = menu?.findItem(R.id.mm_load_server)?.actionView as SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d(TAG, "OnQueryTextSubmitted")
                viewModel.handleSearchQuery(query)
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d(TAG, "OnQueryTextChanged")
                if(newText.isNullOrEmpty())
                    searchView.setQuery("\u00A0", false)
                return true
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                Log.d("Navigation", "Home pressed in menu")
                //navController.popBackStack()
                //viewModel.handleScreenMode(HOME)
            }
            R.id.mm_authorization -> {
                launchAuthFragment()
            }
            R.id.mm_new -> {
                //supportFragmentManager.popBackStack()
                // viewModel.handleSearchPanelOnShow()
            }
            R.id.mm_load_local -> {
                //supportFragmentManager.popBackStack()
                //viewModel.handleNew()
            }
            R.id.mm_load_server -> {
                Log.d(TAG, "OnOptionsItemSelected: ${item.itemId}")

            }
            R.id.mm_save_local -> {

            }
            R.id.mm_save_server -> {

            }
            R.id.mm_sync -> {

            }
            R.id.mm_quit -> {

            }
        }
        return true
    }

   /* override fun onMenuItemActionExpand(item: MenuItem): Boolean {
        Log.d(TAG, "On menu action Expand. Menu Item: $item")
        when(item) {
            menuItemLoadRemote -> {
                //viewModel.handleScreenMode(SEARCH_ON_SERVER)
                Log.d(TAG, "Menu Item Load Remote")
            }
            //menuItemLoadLocal -> viewModel.handleSearchMode(SEARCH_ON_LOCAL)
            else -> throw RuntimeException("No such item to expand")
        }
        return true
    }

    override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
        Log.d("MainActivity", "Collapse")
        navController.popBackStack()
        //viewModel.handleScreenMode(HOME)
        return true
    }*/

    private fun renderUiToolbar(toolbarState: ToolbarState) {
        val imageView = _binding.toolbar.getChildAt(3) as ImageView
        imageView.setImageDrawable(
            if (toolbarState.isAuthorized) {
                ContextCompat.getDrawable(this, R.drawable.light_bulb_on)
            }
            else ContextCompat.getDrawable(this, R.drawable.light_bulb_off)
        )
    }

    private fun renderUi(appState: AppState) {
        Log.d("MainActivity", "RENDER UI")

        when (appState.mode) {
            ScreenMode.AUTHORIZATION -> {
                Log.d("Navigation", "mode = ${appState.mode.name}")
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                switchBottombarState(false)
                launchAuthFragment()
            }
            ScreenMode.HOME -> {
                Log.d("navigation", "mode = ${appState.mode.name}")
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                switchBottombarState(true)
                //binding.navView.visibility = View.VISIBLE
            }
            ScreenMode.SEARCH_ON_SERVER -> {
                Log.d("Navigation", "mode = ${appState.mode.name}")
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                supportActionBar?.setDisplayShowHomeEnabled(true)
            }
            else -> {
                //supportActionBar?.setDisplayShowHomeEnabled(true)
                //supportActionBar?.setDisplayHomeAsUpEnabled(false)
            }
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun setupToolbar() {
        setSupportActionBar(_binding.toolbar)

        val logo = if (_binding.toolbar.childCount > 1) _binding.toolbar.getChildAt(1) as ImageView else null
        logo?.scaleType = ImageView.ScaleType.CENTER_CROP

        val lp = logo?.layoutParams as? Toolbar.LayoutParams
        lp?.let {
            it.width = dpToIntPx(40)
            it.height = dpToIntPx(40)
            it.marginEnd = dpToIntPx(16)
            logo.layoutParams = it
        }

        //supportActionBar?.setLogo(R.drawable.cell_tower_icon_3_round)

        val title = _binding.toolbar.getChildAt(0) as TextView
        val titleTypeFace: Typeface = Typeface.createFromAsset(assets, "fonts/gost_clan_gradient.ttf")

        with(title) {
            typeface = titleTypeFace
            textSize = 24f
            setTextColor(ContextCompat.getColor(this@MainActivity, color.color_title))
        }

    }

    private fun launchAuthFragment() {
        navController.navigate(R.id.login_nav_graph)
    }

    private fun launchSitesRemoteFragment(token: String) {
        //navController.navigate(R.id.sites_nav_graph, bundleOf(TOKEN to token))
    }

    private fun switchBottombarState(isActive: Boolean) {
        _binding.navView.menu.setGroupEnabled(0, isActive)
    }

    /*private fun checkChildConsistent(clazz: Class<out View>): Int {
        Log.d(TAG, "checkChild: $clazz")
        var titleChildNumber = 0
        while (titleChildNumber <= _binding.toolbar.childCount) {
            Log.d(TAG, "title number: $titleChildNumber, ${_binding.toolbar.getChildAt(titleChildNumber)}")
            if (_binding.toolbar.getChildAt(titleChildNumber).javaClass != clazz) {
                titleChildNumber++
            } else return titleChildNumber
        }
       throw RuntimeException("Wrong child number!")
    }*/

    override fun onAuthorizationCompleted(isClosed: Boolean) {
        switchBottombarState(true)
        viewModel.handleAuthResult(isClosed)
    }

    companion object {
        const val TAG = "MainActivity"
    }
}