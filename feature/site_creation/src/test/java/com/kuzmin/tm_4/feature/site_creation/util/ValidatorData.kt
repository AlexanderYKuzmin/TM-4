package com.kuzmin.tm_4.feature.site_creation.util

import com.kuzmin.tm_4.feature.site_creation.R
import com.kuzmin.tm_4.feature.site_creation.domain.model.Condition
import com.kuzmin.tm_4.feature.site_creation.domain.model.ErrorInfo

object ValidatorData {

    /*fun getErrorContainerTest(): ErrorContainer {
        return ErrorContainer()
    }*/

    fun getErrorInfoItemsTest(): List<ErrorInfo> {
        return listOf(
            ErrorInfo(
                viewId = 100,
                isCorrect = true,
                condition = Condition.FOR_EMPTY,
                errorHelpTextId = null,
                errorHelpTextShortId = null,
                errorToastId = null,
                parentId = 11
            ),
            ErrorInfo(
                viewId = 101,
                isCorrect = true,
                condition = Condition.GEO_VALUE,
                errorHelpTextId = null,
                errorHelpTextShortId = null,
                errorToastId = null,
                parentId = 12
            ),
            ErrorInfo(
                viewId = 102,
                isCorrect = false,
                condition = Condition.GEO_VALUE,
                errorHelpTextId = null,
                errorHelpTextShortId = null,
                errorToastId = null,
                parentId = 13
            )
        )
    }

    fun getErrorInfo() : ErrorInfo {
        return ErrorInfo(
            viewId = 100,
            isCorrect = true,
            condition = Condition.FOR_EMPTY,
            errorHelpTextId = null,
            errorHelpTextShortId = null,
            errorToastId = null,
            parentId = 11
        )
    }

    fun getErrorInfoWhenEmptyOrBlanc(): ErrorInfo {
        return ErrorInfo(
            viewId = 100,
            isCorrect = false,
            condition = Condition.FOR_EMPTY,
            errorHelpTextId = R.string.must_be_filled,
            errorHelpTextShortId = R.string.must_be_filled_short,
            errorToastId = null,
            parentId = 11
        )
    }

    fun getErrorInfoWhenNotEmpty(): ErrorInfo {
        return ErrorInfo(
            viewId = 100,
            isCorrect = true,
            condition = Condition.FOR_EMPTY,
            errorHelpTextId = null,
            errorHelpTextShortId = null,
            errorToastId = null,
            parentId = 11
        )
    }

    fun getErrorInfoGeo(): ErrorInfo {
        return ErrorInfo(
            viewId = 100,
            isCorrect = true,
            condition = Condition.GEO_VALUE,
            errorHelpTextId = null,
            errorHelpTextShortId = null,
            errorToastId = null,
            parentId = 11
        )
    }

    fun getErrorInfoGeoLatitude(): ErrorInfo {
        return ErrorInfo(
            viewId = R.id.et_site_latitude_creation,
            isCorrect = true,
            condition = Condition.GEO_VALUE,
            errorHelpTextId = null,
            errorHelpTextShortId = null,
            errorToastId = null,
            parentId = 11
        )
    }

    fun getErrorInfoGeoLongitude(): ErrorInfo {
        return ErrorInfo(
            viewId = R.id.et_site_longitude_creation,
            isCorrect = true,
            condition = Condition.GEO_VALUE,
            errorHelpTextId = null,
            errorHelpTextShortId = null,
            errorToastId = null,
            parentId = 11
        )
    }

    fun getErrorInfoGeoWhenEmpty(): ErrorInfo {
        return ErrorInfo(
            viewId = 100,
            isCorrect = false,
            condition = Condition.GEO_VALUE,
            errorHelpTextId = R.string.must_be_filled,
            errorHelpTextShortId = R.string.must_be_filled_short,
            errorToastId = null,
            parentId = 11
        )
    }

    fun getErrorInfoGeoLatitudeWhenWrongFormat(): ErrorInfo {
        return ErrorInfo(
            viewId = R.id.et_site_latitude_creation,
            isCorrect = false,
            condition = Condition.GEO_VALUE,
            errorHelpTextId = R.string.geo_format_lat,
            errorHelpTextShortId = R.string.geo_format_lat,
            errorToastId = null,
            parentId = 11
        )
    }

    fun getErrorInfoGeoLongitudeWhenWrongFormat(): ErrorInfo {
        return ErrorInfo(
            viewId = R.id.et_site_longitude_creation,
            isCorrect = false,
            condition = Condition.GEO_VALUE,
            errorHelpTextId = R.string.geo_format_lon,
            errorHelpTextShortId = R.string.geo_format_lon,
            errorToastId = null,
            parentId = 11
        )
    }

    fun getErrorInfoLatitudeWithWrongValueExceed(): ErrorInfo {
        return ErrorInfo (
            viewId = R.id.et_site_latitude_creation,
            isCorrect = false,
            condition = Condition.GEO_VALUE,
            errorHelpTextId = R.string.geo_wrong_value_latitude,
            errorHelpTextShortId = R.string.geo_wrong_value_latitude,
            errorToastId = null,
            parentId = 11
        )
    }

    fun getErrorInfoLongitudeWithWrongValueExceed(): ErrorInfo {
        return ErrorInfo (
            viewId = R.id.et_site_longitude_creation,
            isCorrect = false,
            condition = Condition.GEO_VALUE,
            errorHelpTextId = R.string.geo_wrong_value_longitude,
            errorHelpTextShortId = R.string.geo_wrong_value_longitude,
            errorToastId = null,
            parentId = 11
        )
    }
}