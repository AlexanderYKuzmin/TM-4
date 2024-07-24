package com.kuzmin.tm_4.feature.site_creation.domain.validators_api

import android.widget.EditText

interface OnGeoFieldValidator {

    fun validateGeoField(id: Int, text: String?): Boolean
}