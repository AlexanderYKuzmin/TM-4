package com.kuzmin.tm_4.feature.measurements.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuzmin.tm_4.feature.api.domain.model.sealed.SiteActionResult
import com.kuzmin.tm_4.feature.api.domain.usecases.GetConstructionFullFromDbUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalStateException
import javax.inject.Inject

@HiltViewModel
class TowerMeasureActionViewModel @Inject constructor(
    private val getConstructionFullFromDbUseCase: GetConstructionFullFromDbUseCase
) : ViewModel() {

    private val _constructionResult = MutableLiveData<SiteActionResult>()
    val constructionResult: LiveData<SiteActionResult> get() = _constructionResult

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _constructionResult.postValue(SiteActionResult.Error(throwable))
    }

    fun getConstructionFullFromDb(cUuid: String) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val constr = getConstructionFullFromDbUseCase(cUuid)

            if (constr != null) {
                _constructionResult.postValue(
                    SiteActionResult.SuccessC(constr)
                )
            } else {
                throw IllegalStateException("Error fetching construction from DB")
            }
        }
    }
}