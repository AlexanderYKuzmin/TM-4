package com.kuzmin.tm_4.common.util.data_validators

import com.kuzmin.tm_4.common.model.ValidationResult
import com.kuzmin.tm_4.common.util.CommonConstants.EMAIL
import com.kuzmin.tm_4.common.util.messages.LoginMessage.INVALID_EMAIL

object EmailValidator {
    fun validateEmail(email: String): ValidationResult {
        val isValid = email.isNotBlank() && email.length > 7
        return ValidationResult(
            name = EMAIL,
            isValid = isValid,
            errorMessage = if (isValid) null else INVALID_EMAIL
        )
    }
}