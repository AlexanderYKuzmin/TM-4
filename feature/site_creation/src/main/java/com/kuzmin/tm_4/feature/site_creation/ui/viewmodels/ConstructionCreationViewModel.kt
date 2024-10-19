package com.kuzmin.tm_4.feature.site_creation.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.model_complex.ConstructionAndSections
import com.kuzmin.tm_4.feature.api.domain.usecases.SaveConstructionAndSectionsToDbUseCase
import com.kuzmin.tm_4.feature.site_creation.domain.model.sealed.CreationState
import com.kuzmin.tm_4.feature.site_creation.domain.validators_api.StructureValidator
import com.kuzmin.tm_4.feature.site_creation.domain.validators_api.Validator
import com.kuzmin.tm_4.feature.site_creation.ui.fragments.SiteCreationMainFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ConstructionCreationViewModel @Inject constructor(
    private val saveConstructionAndSectionsToDbUseCase: SaveConstructionAndSectionsToDbUseCase,
    @Named("Construction")
    override val validator: Validator,
    private val structureValidator: StructureValidator
) : CreationViewModel() {

    protected override var _creationState = MutableLiveData<CreationState>()
    val creationState: LiveData<CreationState> get() = _creationState

    fun populateConstructionCreation(siteUuid: String?) {
        populateDefaultConstruction()
        /*if (siteUuid.isNullOrEmpty()) populateDefaultConstruction()
        else populateConstructionByUuid(siteUuid)*/
    }

    private fun populateDefaultConstruction() {
        _creationState.value = CreationState.SuccessGetDefault
    }

    private fun populateConstructionByUuid(siteUuid: String) {
        //TODO()
    }

    fun saveConstructionToDb(constructionAndSections: ConstructionAndSections) {
        if (structureValidator.validateStructure(constructionAndSections)) {
            viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
                saveConstructionAndSectionsToDbUseCase(constructionAndSections)
                _creationState.postValue(
                    CreationState.SuccessSavedToDb(constructionAndSections.construction.uuid, SiteCreationMainFragment.SAVE_NOT_LAUNCH)
                )
            }
        } else {
            _creationState.value = CreationState.ValidationStatus(false)
        }
    }
}