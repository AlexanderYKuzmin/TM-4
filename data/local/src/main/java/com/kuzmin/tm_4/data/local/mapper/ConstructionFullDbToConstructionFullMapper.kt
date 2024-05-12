package com.kuzmin.tm_4.data.local.mapper

import com.kuzmin.tm_4.core.database.delivery.ConstructionAndSectionsDb
import com.kuzmin.tm_4.core.database.delivery.ConstructionFullDb
import com.kuzmin.tm_4.core.database.delivery.McDbFull
import com.kuzmin.tm_4.feature.api.domain.model.model_complex_obj.ConstructionAndSections
import com.kuzmin.tm_4.feature.api.domain.model.model_complex_obj.ConstructionFull
import com.kuzmin.tm_4.feature.api.domain.model.model_complex_obj.McFull
import javax.inject.Inject

class ConstructionFullDbToConstructionFullMapper @Inject constructor() : SiteDbToSiteModelMapper() {
    fun mapConstructionFullDbToConstructionFull(constructionFullDb: ConstructionFullDb): ConstructionFull {
        with(constructionFullDb) {
            return ConstructionFull(
                constructionAndSections = mapConAndSecDbToConAndSec(constructionAndSectionsDb),
                measurementConstructionFullList = mapMcFullDbListToMcFullList(mcFullDbList)
            )
        }
    }

    private fun mapMcFullDbListToMcFullList(mcFullDbList: List<McDbFull>): List<McFull> {
        return mcFullDbList.map {
            mapMcDbFullToMcFull(it)
        }
    }

    private fun mapMcDbFullToMcFull(mcDbFull: McDbFull): McFull {
        with(mcDbFull) {
            return McFull(
                mc = mapMeasurementConstructionDbToMeasurementConstruction(mcDb),
                groups = mapGroupListDbToGroupList(groups),
                measurements = mapMeasurementListDbToMeasurementList(measurements),
                results = mapResultListDbToResultList(results)
            )
        }
    }

    private fun mapConAndSecDbToConAndSec(constructionAndSectionsDb: ConstructionAndSectionsDb): ConstructionAndSections {
        with(constructionAndSectionsDb) {
            return ConstructionAndSections(
                construction = mapConstructionDbToConstruction(constructionDb),
                sections = mapSectionListDbToSectionList(sections)
            )
        }
    }


}