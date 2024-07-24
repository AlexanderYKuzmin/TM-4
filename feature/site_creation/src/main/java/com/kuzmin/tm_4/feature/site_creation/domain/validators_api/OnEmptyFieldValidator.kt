package com.kuzmin.tm_4.feature.site_creation.domain.validators_api

interface OnEmptyFieldValidator {

    fun validateForEmptyField(id: Int, text: String?): Boolean

    fun validateTotalResult(): Map<Int, Boolean>
}