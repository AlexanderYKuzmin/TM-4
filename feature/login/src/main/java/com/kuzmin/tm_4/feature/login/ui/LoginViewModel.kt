package com.kuzmin.tm_4.feature.login.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuzmin.tm_4.feature.login.domain.model.AuthUser
import com.kuzmin.tm_4.feature.login.domain.model.AuthUserState
import com.kuzmin.tm_4.feature.login.domain.model.AuthUserState.Error
import com.kuzmin.tm_4.feature.login.domain.model.AuthUserState.Success
import com.kuzmin.tm_4.feature.login.domain.usecases.GetAuthUserRemoteUseCase
import com.kuzmin.tm_4.feature.login.domain.usecases.ReadAuthUserDatastoreUseCase
import com.kuzmin.tm_4.feature.login.domain.usecases.WriteAuthUserDatastoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getAuthUserRemoteUseCase: GetAuthUserRemoteUseCase,
    private val readAuthUserDatastoreUseCase: ReadAuthUserDatastoreUseCase,
    private val writeAuthUserDatastoreUseCase: WriteAuthUserDatastoreUseCase
) : ViewModel() {

    private val _authUserState = MutableLiveData<AuthUserState>()
    val authUserState: LiveData<AuthUserState> get() = _authUserState

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _authUserState.value = Error(throwable)
    }

    init {
        viewModelScope.launch {
            val auth = readAuthUserDatastoreUseCase()
            _authUserState.value = AuthUserState.Default(auth)
        }
    }

    fun getAuthUserRemote(username: String, password: String) {
        viewModelScope.launch(exceptionHandler) {
            val auth = getAuthUserRemoteUseCase(username, password)
            if (auth.isValid()) {
                launch { writeAuthUserDatastoreUseCase(auth) }
                _authUserState.value = Success(auth)
            } else _authUserState.value = Error(IllegalAccessException())
        }
    }


}