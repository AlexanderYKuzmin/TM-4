package com.kuzmin.tm_4.ui

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuzmin.tm_4.common.util.CommonConstants.APP_TITLE
import com.kuzmin.tm_4.feature.login.domain.AuthManager
import com.kuzmin.tm_4.feature.login.domain.usecases.ReadAuthUserDatastoreUseCase
import com.kuzmin.tm_4.feature.sites.domain.model.SearchQuerySharedContainer
import com.kuzmin.tm_4.model.AppState
import com.kuzmin.tm_4.model.ScreenMode
import com.kuzmin.tm_4.model.ToolbarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val readAuthUserDatastoreUseCase: ReadAuthUserDatastoreUseCase,
    private val searchQuerySharedContainer: SearchQuerySharedContainer,
    private val authManager: AuthManager
) : ViewModel() {

    private val authExceptionHandler = CoroutineExceptionHandler { _, throwable ->

        Log.d("MainActivity", "Exception handler throwable: $throwable")
    }

    //private val _authorization = MutableLiveData<Boolean>()
    //val authorization: LiveData<Boolean> get() = _authorization

    private val _appState = MutableLiveData(AppState(ScreenMode.HOME, APP_TITLE))
    val appState: AppState get() = _appState.value!!

    private val _toolbarState = MutableLiveData(ToolbarState())
    val toolbarState: ToolbarState get() = _toolbarState.value!!

    init {
        checkAuthorization()
    }

    fun observeAppState(context: LifecycleOwner, updateState: (appState: AppState) -> Unit) {
        _appState.observe(context) {
            updateState(it)
        }
    }

    fun observeToolbarState(
        context: LifecycleOwner,
        updateState: (toolbarState: ToolbarState) -> Unit
    ) {
        _toolbarState.observe(context) {
            updateState(it)
        }
    }

    /*private fun readAuthUser() {
        viewModelScope.launch(Dispatchers.IO + authExceptionHandler) {
            val auth = readAuthUserDatastoreUseCase()
            if (auth.isValid()) _authUser.value = AuthResult.Success(auth)
            Log.d("MainActivity", "Init authuser: $_authUser")
        }
    }*/
    //fun checkAuthUser(context: Context, launchFragment: (username: String) -> Unit) {
        /*_appState.value = appState.value!!.copy(
            mode = ScreenMode.AUTHORIZATION
        )*/  // Update State отдельный метод надо на прверку и апдейт
        /*viewModelScope.launch {
            val job = launch {
                val newDate = Date()
                val time = newDate.time - 100000
                setUserToDataStoreUseCase(AuthUser(
                    username = "admin",
                    password = "any456",
                    token = "token",
                    dateToken = time,
                    remoteId = 12,
                    firstName = "Vasya",
                    lastName = "Gegrby"
                ))
            }
            job.join()
            _authUser = getUserFromDataStoreUseCase()
            if (AuthValidation.isAuthUserValid(authUser)) {
                //application.toast("Юзер валидный")
                RequestInterceptor.token = authUser.token
            } else {
                (context as AppCompatActivity).runOnUiThread { launchFragment(authUser.username) }
            }
        }*/
    fun checkAuthorization() {
        viewModelScope.launch(Dispatchers.IO + authExceptionHandler) {
            Log.d("MainActivity", "Launch check authorization ${this.coroutineContext}")
            handleAuthResult(authManager.isUserAuthorized())
        }
    }

    fun handleAuthResult(isAuthorized: Boolean) {
        Log.d("MainActivity", "HandleAuthResult")
        _toolbarState.postValue(toolbarState.copy(isAuthorized = isAuthorized))

        _appState.postValue(
            appState.copy(
                mode = if (isAuthorized) ScreenMode.HOME
                else ScreenMode.AUTHORIZATION
            )
        )
    }
    fun handleSearchQuery(query: String?) {
        val editedQuery = query?.replace("\u00A0", "") ?: ""
        searchQuerySharedContainer.setData(editedQuery)
    }
}