package com.kuzmin.tm_4.data.local.mapper.fromModelToDb

import com.kuzmin.tm_4.core.database.model.delivery.ConstructionAndSectionsDb
import com.kuzmin.tm_4.data.local.util.ConstructionTestData
import com.kuzmin.tm_4.data.local.util.SectionTestData
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.model_complex.ConstructionAndSections
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class ConstructionAndSectionsToConstructionAndSectionsDbMapperTest {

    private lateinit var constructionAndSections: ConstructionAndSections

    private val constructionAndSectionsToConstructionAndSectionsDbMapper = ConstructionAndSectionsToConstructionAndSectionsDbMapper()

    @Before
    fun setUp() {
        constructionAndSections = ConstructionAndSections(
            ConstructionTestData.createTestConstruction(0, 0),
            SectionTestData.createTestSectionList(0, listOf(0), 3, "actual")
        )
    }

    @Test
    fun mapConstructionAndSectionsToConstructionAndSectionsDb() {
        val expected = ConstructionAndSectionsDb(
            ConstructionTestData.createTestConstructionDb(0, 0),
            SectionTestData.createTestSectionDbList(0, listOf(0), 3, "actual")
        )

        val actual = constructionAndSectionsToConstructionAndSectionsDbMapper
            .mapConstructionAndSectionsToConstructionAndSectionsDb(constructionAndSections)

        assertEquals(expected, actual)
    }
}