package com.kuzmin.tm_4

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.MenuProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kuzmin.tm_4.common.R.*
import com.kuzmin.tm_4.common.extension.dpToIntPx
import com.kuzmin.tm_4.databinding.ActivityMainBinding
import com.kuzmin.tm_4.model.AppState
import com.kuzmin.tm_4.model.ScreenMode
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MenuItem.OnActionExpandListener {
    private lateinit var binding: ActivityMainBinding

    private val navController: NavController by lazy {
        findNavController(R.id.nav_host_fragment_activity_main)
    }

    private lateinit var menuItemLoadRemote: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.sites_nav_graph,
                R.id.measurements_nav_graph,
                R.id.report_nav_graph
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        setupToolbar()
        //val startFragmentBundle = Bundle()

        //val startFragmentBundle = bundleOf(BUILDING_LIST to viewModel.getConstructionList())
        val startFragmentBundle = bundleOf()
        navController.setGraph(navController.graph, startFragmentBundle)
    }

    private fun setMainMenu() {
        addMenuProvider (object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when(menuItem.itemId) {
                    android.R.id.home -> {
                        Log.d("Navigation", "Home pressed in menu")
                        navController.popBackStack()
                        //viewModel.handleScreenMode(HOME)
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
                        Log.d("Navigation", "OnOptionsItemSelected: ${menuItem.itemId}")
                        //viewModel.handleScreenMode(SEARCH_ON_SERVER) //
                        //launchSitesRemoteFragment(viewModel.authUser.token)
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
        })
    }

    override fun onMenuItemActionExpand(item: MenuItem): Boolean {
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
    }

    private fun renderUi(appState: AppState) {
        Log.d("Navigation", "RENDER UI")
        //supportActionBar?.setDisplayShowHomeEnabled(true)


        when (appState.mode) {
            ScreenMode.AUTHORIZATION -> {
                Log.d("Navigation", "mode = ${appState.mode.name}")
                renderToolbarDefault(appState)
            }
            ScreenMode.HOME -> {
                Log.d("navigation", "mode = ${appState.mode.name}")
                renderToolbarDefault(appState)
                binding.navView.visibility = View.VISIBLE
            }
            ScreenMode.SEARCH_ON_SERVER -> {
                Log.d("Navigation", "mode = ${appState.mode.name}")
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                supportActionBar?.setDisplayShowHomeEnabled(true)
                binding.navView.visibility = View.GONE
            }
            else -> {
                supportActionBar?.setDisplayShowHomeEnabled(true)
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
            }

        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        /*binding.toolbar.setOnClickListener {

        }*/

        val logo =
            if (binding.toolbar.childCount > 1) binding.toolbar.getChildAt(1) as ImageView
            else null
        logo?.scaleType = ImageView.ScaleType.CENTER_CROP

        val lp = logo?.layoutParams as? Toolbar.LayoutParams
        lp?.let {
            it.width = dpToIntPx(40)
            it.height = dpToIntPx(40)
            it.marginEnd = dpToIntPx(16)
            logo.layoutParams = it
        }

        //supportActionBar?.setLogo(R.drawable.cell_tower_icon_3_round)

        val title = binding.toolbar.getChildAt(0) as TextView
        val titleTypeFace: Typeface = Typeface.createFromAsset(assets, "fonts/gost_clan_gradient.ttf")
        title.typeface = titleTypeFace
        title.textSize = 24f
        title.setTextColor(ContextCompat.getColor(this, color.color_title))
    }

    private fun renderToolbarDefault(appState: AppState) {
        Log.d("MainActivity", "set default toolbar properties")
        supportActionBar?.setLogo(R.drawable.cell_tower_icon_3_round)
        Log.d("Navigation", "class child 0: ${binding.toolbar.getChildAt(0)}")
        Log.d("Navigation", "class child 1: ${binding.toolbar.getChildAt(1)}")
        Log.d("Navigation", "class child 2: ${binding.toolbar.getChildAt(2)}")

        var titleChildNumber = 0
        if (binding.toolbar.childCount > 2) titleChildNumber = 1

        val title = binding.toolbar.getChildAt(titleChildNumber) as TextView
        title.typeface = Typeface.createFromAsset(assets, "fonts/gost_clan_gradient.ttf")
        //title.text = viewModel.appState.value?.title //TODO
    }

    companion object {
        const val TAG = "MainActivity"
    }
}