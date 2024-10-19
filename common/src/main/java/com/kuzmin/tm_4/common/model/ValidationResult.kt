package com.kuzmin.tm_4.common.model

data class ValidationResult (
    val name: String,
    val isValid: Boolean = true,
    val errorMessage: String? = null
)

