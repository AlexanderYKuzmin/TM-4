package com.kuzmin.tm_4.ui

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuzmin.tm_4.common.util.CommonConstants.STORAGE_LOCAL
import com.kuzmin.tm_4.common.util.CommonConstants.STORAGE_REMOTE
import com.kuzmin.tm_4.feature.login.domain.AuthManager
import com.kuzmin.tm_4.feature.login.domain.usecases.ReadAuthUserDatastoreUseCase
import com.kuzmin.tm_4.feature.sites.domain.model.SearchQuerySharedContainer
import com.kuzmin.tm_4.model.ScreenMode
import com.kuzmin.tm_4.model.sealed.AppState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val readAuthUserDatastoreUseCase: ReadAuthUserDatastoreUseCase,
    private val authManager: AuthManager
) : ViewModel() {

    private val authExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d("MainActivity", "Exception handler throwable: $throwable")
    }

    private var _toolbarState: AppState.ToolbarState? = null
    private val toolbarState: AppState.ToolbarState get() = _toolbarState!!

    private val _appState = MutableLiveData<AppState>()
    private val appState: LiveData<AppState> = _appState

    init {
        checkAuthorization()
    }

    fun observeAppState(lifecycleOwner: LifecycleOwner, action: (AppState) -> Unit) {
        appState.observe(lifecycleOwner) {
            action.invoke(it)
        }
    }

    fun initAuthorization() {
        _appState.value = AppState.LoginState
    }

    fun initSiteList(storage: Int) {
        _appState.value = AppState.SiteListSate(
            storage,
            false
        )
    }

    fun initNew() {
        _appState.value = AppState.SiteCreationState(null)
    }

    fun initSearchFilter() {
        _appState.value
    }

    private fun checkAuthorization() {
        viewModelScope.launch(Dispatchers.IO + authExceptionHandler) {
            Log.d("MainActivity", "Launch check authorization ${this.coroutineContext}")
            val isAuth = authManager.isUserAuthorized()
            withContext(Dispatchers.Main) {
                if (isFirstStart()) _appState.value = AppState.LoginState
                else handleLogin(isAuth)
            }
        }
    }

    fun handleLogin(isAuthOk: Boolean) {
        _toolbarState = if (_toolbarState == null) {
            AppState.ToolbarState(false, null)
        }
        else {
            toolbarState.copy(isLoginCompleted = isAuthOk)
        }

        _appState.value = toolbarState
    }

    fun handleFilter(isFilterSet: Boolean, storage: Int) {
        _appState.value = AppState.SiteListSate(storage, isFilterSet)
    }

    fun handleSiteSelected(name: String?) {
        _appState.value = toolbarState.copy(appTitle = name)
    }

    fun handleNew() {
        /*_appStateOld.value =
            appState.copy(
                mode = ScreenMode.SITE_CREATION,
                currentSiteUuid = null
            )*/
    }

    private fun isFirstStart(): Boolean {
        return _toolbarState == null
    }
}