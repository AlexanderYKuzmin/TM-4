package com.kuzmin.tm_4.feature.site_creation.domain.validators

import com.kuzmin.tm_4.feature.site_creation.R
import com.kuzmin.tm_4.feature.site_creation.domain.validators_api.Validator
import com.kuzmin.tm_4.feature.site_creation.util.ConstructionValidatorData
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class ConstructionValidatorTest {

    private var validator: Validator? = null

    private var errorContainer: ErrorContainerConstruction? = null

    @Before
    fun setUp() {
        val errInfos = ConstructionValidatorData.getErrorInfoStaticItemsTest()

        errorContainer = ErrorContainerConstruction()

        errInfos.forEach {
            errorContainer!!.add(it)
        }

        validator = ConstructionValidator(errorContainer!!)
    }

   /* @Test
    fun updateErrorMap() {
        val expected = ConstructionValidatorData.getErrorInfoStaticItemsTest()

        expected.forEach {
            errorContainer!!.add(it)
        }

        val actual = errorContainer!!.errorMap.values.toList()

        assertEquals(expected, actual)
    }*/

    @Test
    fun validateWhenDateWrong() {

        val expected = ConstructionValidatorData.getErrorInfoWhenDateWrong()

        println(errorContainer!!.errorMap)

        val actual1 = validator!!.validate(R.id.et_construction_date_creation, "1965.11.11")
        val actual2 = validator!!.validate(R.id.et_construction_date_creation, "1965/27/12")
        val actual3 = validator!!.validate(R.id.et_construction_date_creation, "11/11/2024")

        assertEquals(expected, actual1)
        assertEquals(expected, actual2)
        assertEquals(expected, actual3)
    }

    @Test
    fun validateWhenDateTrue() {
        val expected = ConstructionValidatorData.getErrorInfoWhenDateOk()

        val actual = validator!!.validate(R.id.et_construction_date_creation, "19.11.1999")

        assertEquals(expected, actual)
    }

    @Test
    fun validateWhenHeight() {
        val expected = ConstructionValidatorData.getErrorInfoWhenHeightWrong()

        val actualLower = validator!!.validate(R.id.et_construction_height_creation, "120")
        val actualHigher = validator!!.validate(R.id.et_construction_height_creation, "2000000")

        val actualCorrect = validator!!.validate(R.id.et_construction_height_creation, "90000")

        assertEquals(expected, actualHigher)
        assertEquals(expected,  actualLower)

        assertNotEquals(expected, actualCorrect)
    }

    @Test
    fun validateWhenQuantity() {
        val expected = ConstructionValidatorData.getErrorInfoWhenSectionQWrong()

        val actualTooMuch = validator!!.validate(R.id.et_construction_q_sections_creation, "100")
        val actualZero = validator!!.validate(R.id.et_construction_q_sections_creation, "0")

        val actualCorrect = validator!!.validate(R.id.et_construction_q_sections_creation, "20")

        assertEquals(expected, actualTooMuch)
        assertEquals(expected,  actualZero)

        assertNotEquals(expected, actualCorrect)
    }

    @Test
    fun validateWhenSectionHeight() {
        val expected = ConstructionValidatorData.getErrorInfoWhenSectionHeightWrong()

        val actualLower = validator!!.validate(R.id.et_section_height_creation + 100 * 3, "120")
        val actualHigher = validator!!.validate(R.id.et_section_height_creation + 100 * 3, "50000")

        val actualCorrect = validator!!.validate(R.id.et_section_height_creation + 100 * 3, "10000")

        assertEquals(expected, actualHigher)
        assertEquals(expected,  actualLower)

        assertNotEquals(expected, actualCorrect)
    }

    @Test
    fun validateWhenSectionBottomTop() {
        val expectedBottom = ConstructionValidatorData.getErrorInfoWhenSectionBottomWrong()

        val expectedTop = ConstructionValidatorData.getErrorInfoWhenSectionTopWrong()

        val actualNarrowTop = validator!!.validate(R.id.et_section_top_creation + 100 * 3, "1")
        val actualExceedTop = validator!!.validate(R.id.et_section_top_creation + 100 * 3, "21000")
        val actualCorrectTop = validator!!.validate(R.id.et_section_top_creation + 100 * 3, "20000")

        val actualNarrowBottom = validator!!.validate(R.id.et_section_bottom_creation + 100 * 3, "1")
        val actualExceedBottom = validator!!.validate(R.id.et_section_bottom_creation + 100 * 3, "21000")
        val actualCorrectBottom = validator!!.validate(R.id.et_section_bottom_creation + 100 * 3, "20000")

        assertEquals(expectedBottom, actualExceedBottom)
        assertEquals(expectedBottom, actualNarrowBottom)

        assertEquals(expectedTop, actualExceedTop)
        assertEquals(expectedTop, actualNarrowTop)

        assertNotEquals(expectedBottom, actualCorrectBottom)
        assertNotEquals(expectedTop, actualCorrectTop)
    }

    @Test
    fun checkNoFault() {
    }

    /*Register in validator: 2131362327, 100
    Register in validator: 2131362326, 100
    Register in validator: 2131362328, 100*/
}