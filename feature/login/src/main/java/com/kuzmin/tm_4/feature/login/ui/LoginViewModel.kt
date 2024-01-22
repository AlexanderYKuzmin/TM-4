package com.kuzmin.tm_4.feature.login.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.kuzmin.tm_4.feature.login.domain.usecases.GetAuthUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getAuthUserUseCase: GetAuthUserUseCase
) : ViewModel() {
    fun checkWork() {
        Log.d("ViewModel", "It's working...")
    }
}