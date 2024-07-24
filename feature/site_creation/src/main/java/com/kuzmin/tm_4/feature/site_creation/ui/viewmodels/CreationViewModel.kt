package com.kuzmin.tm_4.feature.site_creation.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kuzmin.tm_4.feature.site_creation.domain.model.Condition
import com.kuzmin.tm_4.feature.site_creation.domain.model.sealed.CreationState
import com.kuzmin.tm_4.feature.site_creation.domain.validators_api.Validator
import kotlinx.coroutines.CoroutineExceptionHandler

abstract class CreationViewModel : ViewModel(){

    abstract val validator: Validator


    protected abstract var _creationState: MutableLiveData<CreationState>
    /*protected var _creationState = MutableLiveData<CreationState>()
    val creationState: LiveData<CreationState> get() = _creationState*/

    protected val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _creationState.postValue(CreationState.Error(throwable))
    }

    fun setToDefaultState() {
        _creationState.value = CreationState.DefaultState
    }

    fun registerViewInValidator(viewId: Int, parentId: Int, condition: Condition) {
        validator.registerId(viewId, parentId, condition)
    }

    fun validate(viewId: Int, content: String?) {
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
    }
}