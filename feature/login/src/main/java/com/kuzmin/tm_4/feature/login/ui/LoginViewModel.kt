package com.kuzmin.tm_4.feature.login.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuzmin.tm_4.feature.login.domain.AuthManager
import com.kuzmin.tm_4.feature.login.domain.model.AuthUserResult
import com.kuzmin.tm_4.feature.login.domain.model.AuthUserResult.Error
import com.kuzmin.tm_4.feature.login.domain.model.AuthUserResult.Success
import com.kuzmin.tm_4.feature.login.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    /*private val getAuthUserRemoteUseCase: GetAuthUserRemoteUseCase,
    private val readAuthUserDatastoreUseCase: ReadAuthUserDatastoreUseCase,
    private val writeAuthUserDatastoreUseCase: WriteAuthUserDatastoreUseCase*/
    private val authManager: AuthManager
) : ViewModel() {

    private val _authUserResult = MutableLiveData<AuthUserResult>()
    val authUserResult: LiveData<AuthUserResult> get() = _authUserResult

    private val _userData = MutableLiveData<User>()
    val userdata: LiveData<User> get() = _userData

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _authUserResult.value = Error(throwable, userdata.value ?: User())
    }

    init {
       getUserData()
    }

    private fun getUserData() {
        viewModelScope.launch {
            _userData.value = authManager.getUser()
        }
    }

    fun authenticate(user: User) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val auth = authManager.authorize(user)
            authManager.saveAuthUser(auth)
            withContext(Dispatchers.Main) {
                _authUserResult.value = Success(auth)
            }
        }
    }

    fun cancelAuthentication() {
        viewModelScope.launch {
            authManager.cancelAuthorization()
        }
    }
}