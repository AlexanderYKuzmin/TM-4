package com.kuzmin.tm_4.common.util.data_validators

import com.kuzmin.tm_4.common.model.ValidationResult
import com.kuzmin.tm_4.common.util.CommonConstants.PASSWORD
import com.kuzmin.tm_4.common.util.messages.LoginMessage.INVALID_PASSWORD

object PasswordValidator {
    fun validatePassword(password: String): ValidationResult {
        val isValid = password.isNotBlank() && password.length > 7
        return ValidationResult(
            name = PASSWORD,
            isValid = isValid,
            errorMessage = if (isValid) null else INVALID_PASSWORD
        )
    }
}