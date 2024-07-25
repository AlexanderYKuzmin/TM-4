package com.kuzmin.tm_4.feature.site_creation.ui.viewmodels

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.kuzmin.tm_4.feature.api.domain.model.site.Site
import com.kuzmin.tm_4.feature.api.domain.usecases.GetSiteByIdFullUseCase
import com.kuzmin.tm_4.feature.api.domain.usecases.SaveSiteToDbUseCase
import com.kuzmin.tm_4.feature.site_creation.domain.model.SiteCreationStateData
import com.kuzmin.tm_4.feature.site_creation.domain.model.sealed.CreationState
import com.kuzmin.tm_4.feature.site_creation.domain.validators_api.Validator
import com.kuzmin.tm_4.feature.site_creation.ui.fragments.SiteCreationMainFragment.Companion.SITE_CREATION_STATE_REQUEST
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class SiteCreationViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val saveSiteToDbUseCase: SaveSiteToDbUseCase,

    private val getSiteByIdFullUseCase: GetSiteByIdFullUseCase,

    @Named("Site")
    override val validator: Validator
) : CreationViewModel() {

    protected override var _creationState = MutableLiveData<CreationState>()
    val creationState: LiveData<CreationState> get() = _creationState

    val stateData: LiveData<SiteCreationStateData> =
        savedStateHandle.getLiveData<SiteCreationStateData>(SITE_CREATION_STATE_REQUEST)

    fun setState(state: Parcelable) {
        Log.d("Restore", "save state data")
        savedStateHandle[SITE_CREATION_STATE_REQUEST] = state
    }

    fun saveSiteToDb(site: Site, launchFlag: Int) {
        Log.d("Creation", "Save site to DB")
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            saveSiteToDbUseCase(site)
            _creationState.postValue(
                CreationState.SuccessSavedToDb(site.siteParams.siteUuid, launchFlag)
            )
        }
    }



    /*override fun updateValidator(viewId: Int, parentId: Int) {
        validator.updateErrorMap(
            viewId,
            parentId,
            SiteCreationMainFragment.idCheckList[viewId] ?: throw RuntimeException("Wrong View ID to validate.")
        )
    }*/

    /*fun validate(viewId: Int, content: String?) {
        _creationState.value = CreationState.ValidationField(
            validator.validate(
                viewId,
                content ?: throw RuntimeException("Edit string for validation is null.")
            )
        )
    }

    fun resultValidation(): Boolean {
        val isCorrect = validator.checkNoFault()
        _creationState.value = CreationState.ValidationStatus(isCorrect)
        return isCorrect
    }*/


}