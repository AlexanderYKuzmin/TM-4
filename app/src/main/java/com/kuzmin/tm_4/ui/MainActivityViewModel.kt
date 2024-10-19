package com.kuzmin.tm_4.ui

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuzmin.tm_4.R
import com.kuzmin.tm_4.common.R.id.home_nav_graph
import com.kuzmin.tm_4.common.R.id.site_nav_graph
import com.kuzmin.tm_4.common.util.CommonConstants.AUTHORIZATION_FAILED
import com.kuzmin.tm_4.common.util.CommonConstants.AUTHORIZATION_STARTED
import com.kuzmin.tm_4.common.util.CommonConstants.AUTHORIZED
import com.kuzmin.tm_4.common.util.CommonConstants.FRAGMENT_ON_START
import com.kuzmin.tm_4.common.util.CommonConstants.STORAGE
import com.kuzmin.tm_4.domain.model.ToolbarState
import com.kuzmin.tm_4.domain.model.sealed.AppState
import com.kuzmin.tm_4.domain.usecases.AppStateProcessorUseCase
import com.kuzmin.tm_4.feature.api.domain.model.FragmentAction
import com.kuzmin.tm_4.feature.home.domain.usecases.DeleteAllTempSitesUseCase
import com.kuzmin.tm_4.feature.login.domain.usecases.ReadAuthUserDatastoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val readAuthUserDatastoreUseCase: ReadAuthUserDatastoreUseCase,
    private val deleteAllTempSitesUseCase: DeleteAllTempSitesUseCase,
    private val appStateProcessorUseCase: AppStateProcessorUseCase
) : ViewModel() {

    private val authExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d("MainActivity", "Exception handler throwable: $throwable")
    }

    private var _toolbarState = ToolbarState(false, null)
    val toolbarState: ToolbarState get() = _toolbarState

    private val _appState = MutableLiveData<AppState>()
    private val appState: LiveData<AppState> = _appState

    /*init {
        checkAuthorization()
    }*/

    fun observeAppState(lifecycleOwner: LifecycleOwner, action: (AppState) -> Unit) {
        appState.observe(lifecycleOwner) {
            action.invoke(it)
        }
    }

    fun initHome() {
        handleFragmentAction(FragmentAction.HomeAction)
    }

    fun initAuthorization() {
        handleFragmentAction(FragmentAction.LoginAction(AUTHORIZATION_STARTED))
    }

    fun initFilter(storage: Int) {
        handleFragmentAction(
            FragmentAction.FilterAction(
                FRAGMENT_ON_START,
                bundleOf(STORAGE to storage)
            )
        )
    }

    fun initSiteList(storage: Int) {
        handleFragmentAction(
            FragmentAction.SiteListAction(
                FRAGMENT_ON_START,
                bundleOf(STORAGE to storage)
            )
        )
    }

    fun initNew() {
        _appState.value = AppState.SiteCreationState(null)
    }

    /*private fun checkAuthorization() {
        viewModelScope.launch(Dispatchers.IO + authExceptionHandler) {
            val isAuth = authManager.isUserAuthorized()
            withContext(Dispatchers.Main) {
                handleFragmentAction(
                    FragmentAction.LoginAction(
                        if (!isAuth) AUTHORIZATION_FAILED else AUTHORIZED
                    )
                )
            }
        }
    }*/

    fun deleteAllTempSites() {
        viewModelScope.launch(Dispatchers.IO) {
            deleteAllTempSitesUseCase()
        }
    }

    fun handleFragmentAction(fragmentAction: FragmentAction) {
        val pairState = appStateProcessorUseCase(
            toolbarState,
            fragmentAction
        )
        _toolbarState = pairState.second
        _appState.value = pairState.first ?: AppState.HomeState
    }

    fun handleSiteSelected(name: String?) {
        changeAppState(site_nav_graph, bundle = bundleOf(APP_TITLE to name))
    }

    fun changeAppState(fragmentId: Int, resources: Resources? = null, bundle: Bundle? = null) {
        when (fragmentId) {
            home_nav_graph -> {
                _toolbarState =
                    toolbarState.copy(
                        appTitle = resources?.getString(R.string.app_name),
                        isLogoVisible = true
                    )
                _appState.value = AppState.HomeState
            }

            site_nav_graph -> {
                if (bundle != null) {
                    _toolbarState = toolbarState.copy(appTitle = bundle.getString(APP_TITLE))
                    _appState.value = AppState.SingleSiteState(bundle.getString(APP_TITLE))
                }
            }
        }
    }

    fun handleNew() {
        /*_appStateOld.value =
            appState.copy(
                mode = ScreenMode.SITE_CREATION,
                currentSiteUuid = null
            )*/
    }

    companion object {
        const val APP_TITLE = "app_title"

        const val IS_LOGO_VISIBLE = "is_logo_visible"
    }
}