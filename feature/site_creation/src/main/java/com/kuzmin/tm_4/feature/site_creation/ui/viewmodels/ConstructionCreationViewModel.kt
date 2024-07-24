package com.kuzmin.tm_4.feature.site_creation.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kuzmin.tm_4.common.extension.formatToDateString
import com.kuzmin.tm_4.feature.api.domain.usecases.SaveConstructionAndSectionsToDbUseCase
import com.kuzmin.tm_4.feature.site_creation.domain.model.sealed.CreationConstructionState
import com.kuzmin.tm_4.feature.site_creation.domain.model.sealed.CreationState
import com.kuzmin.tm_4.feature.site_creation.domain.validators.ConstructionFieldDataValidator
import com.kuzmin.tm_4.feature.site_creation.domain.validators.ConstructionStructureValidator
import com.kuzmin.tm_4.feature.site_creation.domain.validators_api.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ConstructionCreationViewModel @Inject constructor(
    private val saveConstructionAndSectionsToDbUseCase: SaveConstructionAndSectionsToDbUseCase,
    @Named("Construction")
    override val validator: Validator
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

    /*fun saveConstructionToDb(constructionAndSections: ConstructionAndSections) {
        if (validateConstructionStructure(constructionAndSections)) {
            viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
                saveConstructionAndSectionsToDbUseCase(constructionAndSections)
                _creationSiteState.postValue(
                    CreationSiteState.SuccessConstructionSaved(constructionAndSections.construction.uuid)
                )
            }
        } else {
            _creationSiteState.value = CreationSiteState.SuccessConstructionSaved(null)
        }
    }*/

    //TODO Change all of this about construction
    /*fun setConstructionFieldDataValidator(
        v: ViewGroup,
        getString: (Int) -> String,
        getColorById: (Int) -> Int
    ) {
        constructionFieldDataValidator.setTextChangedListener(v, getString, getColorById)
    }

    fun validateConstructionFieldData(v: ViewGroup) {
        with(constructionFieldDataValidator) {
            _creationSiteState.value = CreationSiteState.ValidationConstructionStatus(validateAll(v))
        }
    }

    private fun validateConstructionStructure(constructionAndSections: ConstructionAndSections): Boolean {
        return constructionStructureValidator.validateAll(constructionAndSections)
    }*/
}