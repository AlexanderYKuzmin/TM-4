package com.kuzmin.tm_4.feature.site_creation.domain.validators

import com.kuzmin.tm_4.feature.site_creation.R
import com.kuzmin.tm_4.feature.site_creation.domain.validators_api.Validator
import com.kuzmin.tm_4.feature.site_creation.util.ValidatorData
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test

class SiteValidatorTest {

    private var validator: Validator? = null

    private var errorContainer: ErrorContainerSite? = null

    @Before
    fun setUp() {
        errorContainer = ErrorContainerSite()

        validator = SiteValidator(errorContainer!!)
    }

    @Test
    fun updateErrorMap() {
        val errorInfoList = ValidatorData.getErrorInfoItemsTest()

        val expected = arrayOf(
            errorInfoList[0].viewId,
            errorInfoList[1].viewId,
            errorInfoList[2].viewId,
        )

        errorInfoList.forEach {
            validator?.registerId(it.viewId, it.parentId, it.condition!!)
        }

        val actual = errorContainer?.errorMap?.keys?.toTypedArray()

        assertArrayEquals(actual, expected)
    }

    @Test
    fun validateEmpty() {
        val expected = ValidatorData.getErrorInfoWhenEmptyOrBlanc()

        errorContainer!!.add(ValidatorData.getErrorInfo())

        val actual = validator!!.validate(100, "")

        assertEquals(expected, actual)
    }

    @Test
    fun validateBlanc() {
        val expected = ValidatorData.getErrorInfoWhenEmptyOrBlanc()

        errorContainer!!.add(ValidatorData.getErrorInfo())

        val actual = validator!!.validate(100, "      ")

        assertEquals(expected, actual)
    }

    @Test
    fun validateNull() {
        val expected = ValidatorData.getErrorInfoWhenEmptyOrBlanc()

        errorContainer!!.add(ValidatorData.getErrorInfo())

        val actual = validator!!.validate(100, null)

        assertEquals(expected, actual)
    }

    @Test
    fun validateNotEmpty() {
        val expected = ValidatorData.getErrorInfoWhenNotEmpty()

        errorContainer!!.add(ValidatorData.getErrorInfo())

        val actual = validator!!.validate(100, "TEST")

        assertEquals(expected, actual)
    }

    //Test GEO field
    @Test
    fun validateGeoEmpty() {
        val expected = ValidatorData.getErrorInfoGeoWhenEmpty()

        errorContainer!!.add(ValidatorData.getErrorInfoGeo())

        val actual = validator!!.validate(100, "")

        assertEquals(expected, actual)
    }

    @Test
    fun validateGeoLatitudeWrongFormat() {
        val expected = ValidatorData.getErrorInfoGeoLatitudeWhenWrongFormat()

        errorContainer!!.add(ValidatorData.getErrorInfoGeoLatitude())

        val actual = validator!!.validate(R.id.et_site_latitude_creation, "1234.45677")

        assertEquals(expected, actual)
    }

    @Test
    fun validateGeoLongitudeWrongFormat() {
        val expected = ValidatorData.getErrorInfoGeoLongitudeWhenWrongFormat()

        errorContainer!!.add(ValidatorData.getErrorInfoGeoLongitude())

        val actual = validator!!.validate(R.id.et_site_longitude_creation, "-234")

        assertEquals(expected, actual)
    }

    @Test
    fun validateGeoTooMuchDigitsOverPoint() {
        val expected = ValidatorData.getErrorInfoGeoLatitude()

        errorContainer!!.add(ValidatorData.getErrorInfoGeoLatitude())

        val actual = validator!!.validate(R.id.et_site_latitude_creation, "89.999999")

        assertEquals(expected, actual)
    }

    @Test
    fun validateGeoNoPoint() {
        val expected = ValidatorData.getErrorInfoGeoLatitude()

        errorContainer!!.add(ValidatorData.getErrorInfoGeoLatitude())

        val actual = validator!!.validate(R.id.et_site_latitude_creation, "89")

        assertEquals(expected, actual)
    }

    @Test
    fun validateGeoLatitudeWrongValue() {
        val expected = ValidatorData.getErrorInfoLatitudeWithWrongValueExceed()

        errorContainer!!.add(ValidatorData.getErrorInfoGeoLatitude())

        val actual = validator!!.validate(R.id.et_site_latitude_creation, "90.1")

        assertEquals(expected, actual)
    }

    @Test
    fun validateGeoLongitudeWrongValue() {
        val expected = ValidatorData.getErrorInfoLongitudeWithWrongValueExceed()

        errorContainer!!.add(ValidatorData.getErrorInfoGeoLongitude())

        val actual = validator!!.validate(R.id.et_site_longitude_creation, "180.1")

        assertEquals(expected, actual)
    }

    @Test
    fun checkNoFault() {
        val errorInfos = ValidatorData.getErrorInfoItemsTest()

        errorContainer!!.add(errorInfos[0])
        errorContainer!!.add(errorInfos[1])
        errorContainer!!.add(errorInfos[2])

        val actual = validator!!.checkNoFault()

        assertFalse("Actual is $actual", actual)
    }
}