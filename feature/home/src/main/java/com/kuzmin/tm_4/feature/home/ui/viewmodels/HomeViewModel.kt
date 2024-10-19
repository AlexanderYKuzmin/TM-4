package com.kuzmin.tm_4.feature.home.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuzmin.tm_4.feature.api.domain.model.sealed.SiteActionResult
import com.kuzmin.tm_4.feature.api.ui.FeatureViewModel
import com.kuzmin.tm_4.feature.home.domain.usecases.DeleteAllTempSitesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val deleteAllTempSitesUseCase: DeleteAllTempSitesUseCase
) : FeatureViewModel() {


    fun deleteAllTempSites() {
        viewModelScope.launch(Dispatchers.IO) {
            deleteAllTempSitesUseCase()

            _siteActionResult.postValue(SiteActionResult.DeleteTempsSuccess)
        }
    }
}