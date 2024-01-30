package com.kuzmin.tm_4.ui

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuzmin.tm_4.R
import com.kuzmin.tm_4.feature.login.domain.model.AuthUser
import com.kuzmin.tm_4.feature.login.domain.usecases.ReadAuthUserDatastoreUseCase
import com.kuzmin.tm_4.model.AppState
import com.kuzmin.tm_4.model.ScreenMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    //private val searchQuery: StateFlow<String>,
    private val readAuthUserDatastoreUseCase: ReadAuthUserDatastoreUseCase
) : ViewModel() {

    private lateinit var _authUser: AuthUser

    private val _appState = MutableLiveData<AppState>()
    val appState: AppState get() = _appState.value!!

    init {
        viewModelScope.launch {
            _authUser = readAuthUserDatastoreUseCase()
        }
    }

    fun initAppState(context: Context) {
        _appState.value = AppState(
            mode = ScreenMode.HOME,
            title = context.getString(R.string.app_name)
        )
    }

    fun observeState(context: LifecycleOwner, updateState: (appState: AppState) -> Unit) {
        _appState.observe(context) {
            updateState(it)
        }
    }
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
    fun checkAuthUser(context: Context) {
        Log.d("ViewModel", "CheckAuth User")
        if (_authUser.isValid()) {
            Toast.makeText(context, context.getString(R.string.user_authorized), Toast.LENGTH_SHORT).show()
        } else {
            Log.d("ViewModel", "launch fragment")
            _appState.value = appState.copy(mode = ScreenMode.AUTHORIZATION)
        }
    }
}