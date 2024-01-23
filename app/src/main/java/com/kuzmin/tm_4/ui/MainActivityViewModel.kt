package com.kuzmin.tm_4.ui

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuzmin.tm_4.R
import com.kuzmin.tm_4.feature.login.domain.model.AuthUser
import com.kuzmin.tm_4.feature.login.domain.model.AuthUserState
import com.kuzmin.tm_4.feature.login.domain.usecases.CheckUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    //private val searchQuery: StateFlow<String>,
    private val checkUserUseCase: CheckUserUseCase
) : ViewModel() {

    private lateinit var _authUser: AuthUser

    init {
        viewModelScope.launch {
            _authUser = checkUserUseCase()
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
    fun checkAuthUser(context: Context, launchFragment: (username: String) -> Unit) {
        Log.d("ViewModel", "CheckAuth User")
        if (_authUser.isValid()) {
            Toast.makeText(context, context.getString(R.string.user_authorized),
                Toast.LENGTH_SHORT).show()
        } else {
            Log.d("ViewModel", "launch fragment")
            launchFragment(_authUser.username)
        }
    }
}