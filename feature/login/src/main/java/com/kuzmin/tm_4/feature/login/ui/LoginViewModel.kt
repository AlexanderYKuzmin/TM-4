package com.kuzmin.tm_4.feature.login.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuzmin.tm_4.common.util.messages.LoginMessage.SIGN_UP_NEEDED
import com.kuzmin.tm_4.feature.api.domain.model.user.AuthFbInfo
import com.kuzmin.tm_4.feature.api.domain.model.user.AuthUser
import com.kuzmin.tm_4.feature.api.domain.model.user.User
import com.kuzmin.tm_4.feature.login.domain.model.LoginRegResult
import com.kuzmin.tm_4.feature.login.domain.model.LoginRegResult.Error
import com.kuzmin.tm_4.feature.login.domain.usecases.AuthorizeUseCase
import com.kuzmin.tm_4.feature.login.domain.usecases.GetAuthUserLocalUseCase
import com.kuzmin.tm_4.feature.login.domain.usecases.WriteAuthUserDatastoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getAuthUserLocalUseCase: GetAuthUserLocalUseCase,
    private val authorizeUseCase: AuthorizeUseCase,
    private val writeAuthUserDatastoreUseCase: WriteAuthUserDatastoreUseCase
    ) : ViewModel() {

    private lateinit var _authUser: AuthUser
    val authUser: AuthUser get() = _authUser

    private val _loginRegResult = MutableLiveData<LoginRegResult>()
    val loginRegResult: LiveData<LoginRegResult> get() = _loginRegResult

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _loginRegResult.value = Error(throwable)
    }

    init {
       initAuthUserRegistered()
    }

    private suspend fun getAuthUserLocal() = getAuthUserLocalUseCase()

    private fun initAuthUserRegistered() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val authUserDatastore = async { getAuthUserLocal() }.await()

            withContext(Dispatchers.Main) {

                _loginRegResult.value =
                    if (authUserDatastore.isRegistered) LoginRegResult.LocalUserCheck(authUserDatastore)
                    else LoginRegResult.LocalUserCheck(null)
                _authUser = authUserDatastore
            }
        }
    }

    fun signIn(user: User, dataVisibility: Boolean) {
        if (!isAuthUserRegistered(user.email)) {
            _loginRegResult.value = LoginRegResult.Failure(
                AuthFbInfo(
                    throwable = Exception("User is not registered"),
                    message = SIGN_UP_NEEDED
                )
            )
        } else {
            viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
                val authFbInfo = async { authorizeUseCase(authUser) }.await()

                if (authFbInfo.hasError) {
                    withContext(Dispatchers.Main) {
                        _loginRegResult.value = LoginRegResult.Failure(authFbInfo)
                    }
                } else {
                    launch {
                        val authUserDatastore = authFbInfo.authUser!!.copy(
                            dataVisibility = dataVisibility
                        )
                        writeAuthUserDatastoreUseCase(authFbInfo.authUser!!)
                    }
                    withContext(Dispatchers.Main) {
                        _loginRegResult.value = LoginRegResult.Success(authFbInfo)
                    }
                }
            }
        }
    }

    fun signOut() {
        TODO()
    }

    private fun isAuthUserRegistered(email: String): Boolean {
        return authUser.email == email
    }

    fun registerUser(user: User) {
        TODO()
    }

    /*private fun getUserData() {
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
    }*/
}